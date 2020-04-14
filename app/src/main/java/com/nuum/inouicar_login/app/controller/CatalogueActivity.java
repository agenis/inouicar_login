package com.nuum.inouicar_login.app.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nuum.inouicar_login.R;
import com.nuum.inouicar_login.app.model.Car;
import com.nuum.inouicar_login.app.model.CarAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class CatalogueActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CarAdapter adapter;
    List<Car> carList = new ArrayList<>();
    static final String CAR_URL_0 = "http://spajkpw.cluster029.hosting.ovh.net/api/cars/?";
    String CAR_URL                = "http://spajkpw.cluster029.hosting.ovh.net/api/cars/?";
    String query_autoOpen       = "";
    String query_pricemax       = "";
    SeekBar PriceSlider;
    TextView priceTextView, nocarTextView;
    Spinner spinner;
    ProgressBar loading;
    CheckBox autoOpenCheckbox;
    int MaxPrice = 40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);

        adapter = new CarAdapter(CatalogueActivity.this, carList);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);// creer l'adapter une seule fois.

        // configurer le selecteur de couleur (il est inutile pour le moment)
        spinner = findViewById(R.id.colorSpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.color_picker_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        // configurer le slider de prix et textes
        nocarTextView = findViewById(R.id.nocarTextView);
        loading = findViewById(R.id.loading);
        PriceSlider = findViewById(R.id.priceSliderView);
        priceTextView = findViewById(R.id.priceTextView);
        PriceSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // on affiche la valeur du slider dès qu'il bouge.
                priceTextView.setText("Max price per day: " + progress + "€");
                MaxPrice = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                nocarTextView.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // on met a jour la liste uniquement quand on relache le slider
                loading.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                query_pricemax = "&has_price_less_than=" + MaxPrice;
                carList.clear();
                loadCars();
                if (carList.size() == 0){
                    nocarTextView.setVisibility(View.VISIBLE);
                }
            }
        });

        // configurer la checkbox autoRemote
        autoOpenCheckbox = findViewById(R.id.autoOpenCheckbox);
        autoOpenCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (autoOpenCheckbox.isChecked()) {
                    query_autoOpen = "&has_remote_locking=" + 1;
                } else {
                    query_autoOpen = "";
                }
                carList.clear();
                loadCars();
            }
        });

        loadCars();

    }

    private void loadCars() {
        recyclerView.removeAllViews();
        Log.d("REQUEST_POST ", "request = "+ CAR_URL + query_pricemax + query_autoOpen);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, CAR_URL + query_pricemax + query_autoOpen, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    Type listType = new TypeToken<List<Car>>() {}.getType();
                    List<Car> tempCarList = new Gson().fromJson(response, listType);
                    carList.addAll(tempCarList);
                    adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CatalogueActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }


}















/*
// ancienne methode de parsing JSON

                try {

                    JSONArray cars = new JSONArray(response);
                    for (int i=0; i<cars.length(); i++) {
                        JSONObject carObject = cars.getJSONObject(i);
                        //brand, model, color, is_available, price_per_day, price_per_km, label, photo
                        String brand = carObject.getString("brand");
                        String model = carObject.getString("model");
                        String color = carObject.getString("color");
                        String is_available = carObject.getString("is_available");
                        float price_per_day = (float) carObject.getDouble("price_per_day");
                        float price_per_km = (float) carObject.getDouble("price_per_km");
                        String label = carObject.getString("label");
                        String photo = carObject.getString("photo");
                        Car car = new Car(brand, model, color, is_available, price_per_day, price_per_km, label, photo);
                        carList.add(car);
                        adapter.notifyDataSetChanged();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
 */

/*
// ancienne methode de creation locale des voitures
        /*
        // creation de voitures juste pour tester: (les pictures ont ete codees avec https://www.base64-image.de/
        String picture1 = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEgAAAAqCAIAAACfs4pBAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAB3CSURBVGhDpZdnVFvHtse5KbappkpCdEkUoUpHSIAKQghRBKqAEKKI3qspNs2mN/c47iXYSVzABoO7TTW2RZGobslNXnKd+OYm9671vr99kOOX9/md9WPWPnMOOvOfvWfPHpN//vHHQ90i8Ei3+GRp+cni0uNtwJheXple1iOs/MnyyswKoEfQrxqZ/YjByPqMHlgDY9awMbe6MQ8YNsHYBoz/w+xf+L/vIMYstPAjhnUjyA8a+fC/iA2dCPr1aeS767Mra/B1k9/++OOxTvdEp5taXJwGlgAdtLNLS3MrK3PL24AB6D+iB2b1+nmDYU4PILeI/YFVaOdXV5+tbSysb0CLGKvr86trT9cQ5rfZttc/3v7ZifT8lT/7t4FfWF1bWFsHPj5Cnq6uwo9vA5822usm//r3H7NLy3NLS/Mri8BT/RKwoF9+qtcvGAwIiLH6DAa6ajCysLq6sAYYnq0h/QvIx9aera9/BG7hY8/XN3WbL19sbAGItjXktf8Pz1a3+fitvzyCz8HcAU8Na4hhWDP54z//ntfrnhoWnxp0zwyLC6sIz9aWn62tPF/TP1/XI+02L9YNH9gw6IDN1cWttW02F7e2jOi2NnVIu/V8Y0O3uYWw8SdgwyNj++HNv7Bp/PftFxA2X/wJ2LrNjRcbGy/WN3Qbmy82NuHHjfxVGzK/IEm/+tQAGBBhzw0vtll8YVgCdKvLRhbXVhbXDB9ZAtZXlzbWljfXlrZWgeWXGyuvNlag/cCm/tWm4c0rw5vX/8urN/pXb1Zff2d49RYM/Uu4fa1/+Xrl5dbyy60lZFI2YGqWNrYQQN6fgIyP6DbWFzc3jP2g7Tm4C8JSbwDmV/TG9TJvXDXLK0gALq+Y/Ps//14yLC6tbrO2uLy2tLy+vLy+BMbK+vLK+srKuh7QbxoQNgD9yoZhGTrB3twybL1cff3K8Pql4dWW4fXr1ddv1t68XXvz3Srw+u2fgP0RpGdl6xXIeGaA0FqdW1ycXdTN6pamX+imXyzOLC4B05DJnr94/Oz51AvdzCJ06qZ1APLUOHqjAKOGuW3jg/3nLSJsxbC4rNetGHQrq4vLq4ikZUTnEoDcGpYMayuGdT2wbFjWLel0y4szT+cXIN/MP5188OTG+OSlb6+duvjVFydPHT7+xeDhI72DQ939A529ffs7O4H2zs62AwdaOw60dXV29vf1Hz5y7OTp899eG7l7/9HcwujE3Vt374/ff3h3evbezOydmZk70zN3p+fuTM9NTs1MTk1PPJkCbj9+AkxOTd2ZmoZOsCcePxl/9Bhs5PbRY7Dvzc7BXDx+9uzRwjOT33//19zT6dl5YGp67sn0/JMn848fzz56OP2BJ3NTT2anHk1PPZx6PDI2funy1y+W9TdujS3pDaPjo19d/kqlSo0VCfh8LjeKDfD4HGijojlsXkQYixEWzmBEhAWGBoaGBbPCmdwYPj8uVqyQSdUadX5BcV3dvu6eI2fOdg8cHL01MX7vwfiDB2P37wOI8eDBzXv3trlz8+6dW9Ai3L15F2Hkzp1RaCcnb0xOXp+YuDY+cXXs9tVbt6/fnhy7/8Dk/T/fg3F78taf3By/PToxeWt8Ymx84tb45NjY5K2xydtjk3cuDl959faH/3r3Hpqpufmffvnlh19/ufPwbkxMFIVKCgoOCAkLYrEZ4WwGMzxYGM+PEfP8GP5EOoUS4k8NDSAH+9HCAoPZ4SHcCG68IFIo4IsTFJmqwqri4tqytsH+5s6uk2fPXR25cWNsZGT8xuj4jZGx0ZHxmyPjIzcnbtycGBm9DYxC541bI9vcgPba6A3g6sj16yMjV0eB0W9Hbl4dGTV59+7n69eGR0eu3By5fGv08ujIpbGbw2O3Lt+6eWX0xlcjV8+PjFy+c//+hYuX/v7DT/94/9vPv/72fHHl+//6xy+//fGP3/519cZ1sTge5+nu7O7m6OoazgefsNhRjHBuaEQUE/R4UimU4EBSoL9PoD8pJJjMCKWxWOw4UXg0hyWM4SbGpWvTKmoKKhvKq5qb+o8d7x3sP3mq7+zZzrNnui6cH7p8+cjlywe/Gj4IxtWrX1y7durqtyeuXz32zZXBK5f6Ll7sGr40cOnCobOn+86d6R0ePnrlm1NXvr505dtLJj//8PL8UPm1042jF/d9e6rp+oX2iW/6bn7VdfFY/enBygsn2ibvjI+NjT98+GRjc2NldXX95cv5hWfv/vn7+9//8/P79/ce3k9Nlbt5uKCdsHaOjuyYSH4Ch58Q4e6Ns3dxdXBysXdyRbu5Y3F4LN7TxcsHT6X7gNtihTxhZHBkJFMQHZOUoMlNKanIqWkor6iv6Tl+ZF9bXUtTZvf+zL4DOUPdOYPdmT0dWX2dOQM92YM9WrAHujMHe9XdnRn727P6urMGe7P7u3KGenIO9+UcGsg/2F/S05Fr8t+/f/96tkf/oGPpQcfMjZbnd4c25s7Mjfef6MjcWyG5/2CsrrH+/NnT3V0d6gyFMkWSk5sxeLDv5k2I7dvDV4YHB/uzszVu7i72GLQVyoEpiBBIY2NkQgzB1QqN2Y3G2GCw1hisraMzotDFwwXvRaLS+TFcQTyXHBIUxOWwoqOSUpIyMiX5henVe0pK6qv3Dw40Ndd2tmR3tqS11Mmb6lT1tSkNNdL2vcr2JmVdpbS6UlZdLq2vlJ85knv5RObVU3lfn9IeH8hqqZe1NMra98pbGhTgsbXrp/KbK/iKeHpOKutYb+H1i62nh8o69qSN3BgemxiPjmFz2GEMhl9AgK+/P4lI9PD39w1j+NFoXv5+ZA6bla5SeuDdEGH2KEs0yoXo4+zthcHjLexRwG60IwizwTjZop3sMM5YF5wP0YfDZYXzI2hhDDqTGcpl8xNipcp4pSpJm5dRWVNeWlPZ2tPZuLe6tlJWkJPQ2dXceaB8T2V0Z7OoY6+ovUnc3JDY1pDQ1ZTY3RrX2SLqaU/sbhc3NyWWFiXVVclamiQ1ZfEmUELkp0ekS1jShMD4WIpCGpSbzasokUw9ufPzr7+UlRfFxnIYDDqZhPPwcCTgXWlU33BWKJ/PplC8KWTPKF64UpHsS/ZBYdA2KAdLe5QVxtEWizW3t7ewQ25BmBUKA9ihsSisi4srzAs5IjyEEhxA9POjBAcHR0bwREKxLFGiTE5Vp+QVaMurygpKi9t7Oqtry1MUYiaLweNGVuQJ9tdFHdgTtbeC07ZttNVw99dxm8oj91VxGsoj6ys5DVXcfVVRbXt4+6p5Jmv6Z6rkAGWSv1JCT5EHJCVSk5ICIWH8+I+f3r3/dXbh2aXhr1pb9ynk4sjI0DBGYGqKnEIh+fh4EQg4by8PQTQHeoKDAx0d0bZo1G4HB1sMCuvuYot2sEM72KNR9hiMLQrtgMY4OTu5urkRPPFkqq9fAM0vMDAYNgIelxsjECYmyBRSdaY6O1dbXFZas6dub2tL7+DAFye/rG9qTFerhQLQIzvUnHyiXXKmSz7QIDh5IPnkAcn5LvHRvbEHKjiNpRFlWkalNrw2N7y5nLuniG2ytrqoUkSkyZipspBURZAsmZabk1BRWtTc3HBr7NbIrbHvf3r36u8/LrzQnT53vqOjzcsTb2trbWOzG4/3YIQGRPPZkDySkhJBqqe3J5HkC+MODPKHXYvBZLC5bF40P0YkSkxKkimVGVnZRWUVe5r2dfT2nzhz7uvrI7cm796+9/DOoycPpqYezc48np2ffa57oTcYNta33rx+++MPb3/4u06/MnH/7uzUvcsnu0/1Fh7cJ+6pibrQk3KlP+VUu/hyb1p/TVRDYXBjSehAo+B4a9yFrpThvnSTl1sb1eV5NeU5NaWZ1SXpZQXyPdXZ4cxAKpUQxWEmJcf19HVO3JncfPP2u5/fTT2db25rkcmlQSFB3kRvEplIJHqGhQWLRILsHE1hcWF2TnaWNie3sKC0sry8trajb+DsV99+PXr7yo1xaK+Nwcb66Paj6btTcw/mnj16qpt6tvhkQfdg9unj+YXphRczz3TzuuXny4aVtbW1ra2Xb9++/v77rTff3X883bCveX7h+eam/tK5oQN7pO1l7Ibc4M5K5sHa8KE6VnMBJV/mVZdDrtEQi5Tk8nSayauXm3uqi+qqixqqixqrixuriypKs0JD6MwwikgQyo6gMUJ9ebxQTZZq8PDB8bv3FldXYUZnnr8Yu3e/b+ggeMnd3RnjaE+hEtWa9OLSIkWKUq3R5BXkF5YUl1ZUNexrP3l+eOT2vcvXx06cGz52+uKxM5eOnr54+MTZgaNfDhz5ou/w0c7+odYDXc3tHZ29A71DhwcPHzt64sszFy5e/vYq1DoT9x7cvvugu39IrlROz868gtJ0RXf2eEtrRUxHNXuwiddWxqzRBmilBG0CPlOEk/OdQRsirL6meE91cUN1cX1VEVBeog7w9wry9xBF06O5FBbDWxgdFBfLio3hsRgBYrGotKLsi9On7j56qNMb0jUaNNrByRmjyVILRYJjXxyKiGSGMUPZ7PCoKK4gRiCKTxRLZPmFxYeOn7xw+WpX/+GK2saCsmptfqkmKy89IzNNrU5RqRRKpVyhkClTU9IzUtM16Zqs7Nz8gpLS8uqa+r379rXur6lvlMhlMGuXhofHJicfPrx/5auTuRk8bVpQpiIoKyWoUB1ckBJYrqIXyz3rVCREWENtyd76iubGyn315Y11ZaVFaj+6J8nXKTTQg0rC4nH2dIoHh0nlRwbyIvz8aXh3HNrbxz0oiJ6hUQ1fHhbFCh2xjnfuPxi5fXvi3t0INjMgkMYIC6LRKDQ6BVo6nRoQ6M8Kj1CpNS3tHS37uzK1BWKpXBQXL4jm86K4HG4kF5I+nyeMFcYlJCSIxbFxCSIgPj4hSZwsl8lTUpRpqQniRB4/ak9j47fXbjx/rls1GG5cvVCmFacnh6kljLJM3p5CQUdd3LFW2cVumXGN5ddWFYLfQFVzQ4VEEuuAcqDTcJwIX26kb1gwIYDq7k9yC6Z7hId6hYZ40Pyx3r5oJ2cbDMYGiocnTx7FxAi+uX5j6NjxwcOHIF9nZKRGRIZFCaJ40VyJNClNlSKVJsMQY4TRbA4LZr2isiYjW8uPFkRGsJhMBoMRArBYYbBjRscIEhITQQoI4/KQKyoqSiAQCGNjY4Qx8HTw4MFXr9++fP32yczC0vLy2S96+1vL+lpL+/aVDLSUDLWXHGkvPr6/EBFWWZpbVZ4HKQTJIhV5/nSys4sTjYKP5vmpUgWqFLY8OVAkIAt5lLAQj9Bg17AwVxYLFxjo5u3lSPb1KCvJVciTG5qaKqoq8vK1kEh8iZ5eXjhVekp1TaUmM72qtmpve2vvwf6hob7jxw91de/PL9Cq0tNhOiIjWUxWCANSKCMEFEayI6IFfKlMuqe+saq6ViKRstnsiIgIaKN4UUKhMCExYXDo4MSd+8dOnGpsam5ta7t05sjpg3tPD+w9NdR0CoxDYOz9cqDpg7DKUq2RmvI8Xx9P2DmDAnzZLJIoNiwpka1Oj0tMCBHGkLkcQjgTx2LgQwLdAuiuQYH4ADrezdUBh3OG0sTbG+/m5uSItnOwt3bCokJZIfwoZmJirCJFurelcV9r/cBA5+Bg1+UrF775Zrivr1uhlHK44eDDSDYTxm+MRnCMWJyYocnq6u5JSkoCd4HTuFwun8+PjY2VSqW1dfU9/Qf7Bg4XFZeo1eojhwaODrT1tlcNHqg63F13rLfuRG/dl/11Jlub6xUl2qqyD9rAY1QSMTVFGhxM40TSi3IStBmxEnE4hx2QnMyPFbEiWKTgAHdmCMjz5ESS3F3RprvMLC3MyUQczhXrgnVwdnTAYuywGHt3N6wn3h1PcA8KphcVZp88OXh5+NS5c0evX7t06GBvY1OtOiNFJIrmR3Oi+CCJKxDwYmKiQVicKBYWl1AYy+GwQ0KC/aFUIcPG4uPt7UWhkNPS0ju6eg8eOpqpydRoMq5cudTWXFdVmltTXlhXUdRYU9zSUNpSX2KyubFalJdRWphZVpRVUZJTU57P4zA1GalcDjOS5be3OmfgQFXnvpLqkjRFEpvHDYzmM+TS2Gh+SBjDi8em2dnZ7Nxp6uvtGeRPpvgQ3LAowBlj74y2c3G0d3VCu7o4OjujfIh4iDseN0ytltfWlB840Nza2tjetreoKJdCJrq5ujg5YdFoFMrB3sHeFloM1CrbODjY2dvbwlcAlIOdJwEXGhwUA0tOIEhMSFDIJXU15T0d+wpzVUV5ahBSUoAIKS3QmGysr+blpEHMF+amwzN4kJ4mlSTHJ4ljqURCcU5qeUE6TEB3a+WBxsK6Mo02QyyMZrDD/Rgh3nHCMCsrcycndADdFxFGJHjjXAluTnhXLM7F0cPV0d0FAy3F19PXB08meeFxLl5eHn7+1KBgfzLJBySBl11dsDBiqGYcUHZYLNrFGYvzcCP6ePnRqSEhQbDwIKlAlCYkxKUo5VAzR/E48L8B/lBCQ6iz5ZLEuuqS8uKssuJskFRenA2AjXgMJBXmItryc9LystPSFAlRHIZCJiZ547IzFFnp0kxVcmZ6Ul6WvKYkY2+Ndk+Fpjw/JTtNlJmWtNvK0scLx2OHUkme3gQ3OsWH6OlOJMBxzNXLwwXOnwQPBJDniLFHOdhgHR1g+Tk7Y6CKBOeAHjs7axqNbL3bMiFedPbs6evXrz94cH92Zmbh6dPFxcWlpQ9sbKzPzMzECHhwlPNAjrVOUKkyQoMOHRw8engQFhEsKIg444ICG4StFeeri/NBGwDaVGny+Aimv1wS74lzTlOKRYIIpTRWKhaI43iAKDo8ScSRJ0alSYWpsjhLSwuFNFkijvHEOYGwQD8y2RsPkLxwgA/eDRHm7gSuw6Dt7O1277Yys7E2t7OxsrI0NzPdCZia7nR1dXbEoKqrKhcWnv76669zc3Pv3783GAzv3r3T6/U///yzTqeD9sULXZxIoJAlkny9YOmJYgUV5cXgh4x0ZU1FPoipLEWEQb6oKsuD5LFWUpBRXABBCGSUFWVmZ8gT46JKCrU+MPdebpyIYCaDGhfDjuYyBRwmm+EfHkpnhdBhHw4PpdnYWCllyU31leFhQSCMTCQYhQE0H89AGuns6ROtrXurK8tk0mQvT5z1bgsbK3OQZ2G+y6gKcHNzSRInNNTXvf3uzY8//rAJ19bm8+fP5+fnx8ZunT9/rrurMz8/TxgbA0EYL+IHBfqTSD5trc1KpYTJDElXKWoqC+qqCqF+gt0YysOaigIkFEsLNSWFGeC0ojxYgiptllKlSNKkKyhEL2srKwg2J0cHf5oXneIVGkRnhgSEBNBCA6lBdN8Aqjdkv8T42P6e/bAJ+3rjwDO+nh4QigDFEwdLS5KcePbcGThx37kzce7s6SxNeiSLERLo50cn+0FdQiX5wKkUjYK8BwcfKpXi60sESHCgJXrDicHLiwAayCRv5E0iAY62/n6+n3/2ORqDEgi4caJoqVTc0tJQV1VUW1EIiREkVVfkIx4DYQXatILcNFhm4LSyogxYbGkKsSAqguiJs7KEad1lumMnOzwY4pNO8fTxdHF1dvD3p9BpJD8K0cUZHRnBiBPy05QSKsnL1Qnl64UIgyAk4lyRVUcjwctYLMrd3QVCzt3NOSwkcH/LXm8vvIWFOewTZqamu60sIiJY4DcXFyc3N2cMBmVvbwf5EMIXDkcEvAeo8vL0IBA8QoL9YEnb2tgTCHgIyGg+B066TY21g30dzQ3V9dWliMJKcF0RZEVDfk7qdrpU5WUrc7OUkEJgXTGC/ZwxKPjs559/vmPHDnvb3WiULQaN4IixxeEwBIKTuzva3RUTFuLvhXctyM2EZINzw4JaKtETQhFcB/JIPgRPnJuNlQXKAf4RZWtj5UcnCWOi7GytTXft/ORvn3z6ySfmZmaQ30NDgl1hnlCgCFajvTPWycPDDfYucJonAe9L9AkMoBflayNYoXCKh+KGRCTQKcTI8OCqiqIDbY393W0Dvfv7ulvbW+ob6spN1tcMORo56MnNUgDgPW2mUpIYDXNkB0th5w7TnTvMzXZamO+0tNhlZWkKrTlsybtMzU3NUajdBBw2yJ/kRyXW11ZASsS7O0G6B1Wwp0EL+cPXG++JcyV5E9xhDtAoDNre2RnrSfBwwmJg+8LjPCC5Q8UIY4XzP5zNYQ8A//j70SCzx8fFarMzqipLi4vyYoV8GplIhR0T7+bh4YzHuVIp3nJJQnFBVkVpXmVZfnVFYW1VcU1VcWVZQUlhNuIx0KPNBOQ5GhkYoDNTLU1PTRAJIwP9YPdxgQCDYgLjYGNvY2Vrbbnb0twclO1EPGmz2yLIjxzFYfV2tfnRfPGQ2V0cQR5sTli0rRPGzsnRnoBzdbCzRqPtIMvDPgz7HuzaOHdXkAelCQGC1odA8vWkUYh0KgLseD7eOAAmN4BO8qeTcB7OWIwDyt7Gzma3rfVuW9vdsHOwwoKK8rNKi7RlxTmVZXlV5QWgEArXcqA4F/FYlloKaLc9BoC8zPRk6MlUSzSqZJVSDCkxxJ/sQ3CBI6UzxgFtZ21vbWVtYWphZmpmuotO9ZYlx2mz0mg0b5yHC2QaJ6w9UjE6WNvZW9vYWgGWVmbm4GqLXZaWpjbWFjAdMEEwTSAYZWftiLbHOtq7OKE93JyInh5+VF9GkB8nghHNi4jmRUZHsWHXtjCD2QTgz9zS0hyHc0lVJOVmq3IyU7VZKYV56sI8TVF+ZnFBdmlRTjnsY2urK9kZUqOjwMjRADIA7Iy0pAxkawYD5CVIxdGc8GBI6LA1gUMw9jbWVrD4TakUL2lSbJyQS6V6w75pCwNHRmG6a/uCcUCSMDODHGHE9LNPP/vsk88+/+zTnTt3wgvQY2ZuZmFpZmVlYWOzG3ZtyB3OSDmG8nDFEjxcEP9DAWpnZ7l9wVexaDsyEc+NDFVI4zXpspxMZV6OKl+rhjZbk5KtSQXDxKBfSlXEqVMSoIzQZkAoIvK2PSYBA9rMdEl6amJGmlidmpimiJMnx8REMQNpRC+ci5sTGouyo5HBY/GQFWlUItT24B0QAynnb3/7G8QqXCAAWugxXtAP1yfb16efgsrPjP3wGlyITjMzmBrYwa0tzSE00Pa2jig7J7SDCxbtjsS5M8nLI4DmG8n0j4uJVErjYOph74XUkJudqs1MyctOhTLDRL+yKE3ggjdSpSK1MjEjVaxRJSGStlWBDYBOiEnEgWlJ6lRxqjxOIREmijjhDH9fb3dIiYlxAnZ4KJVCtLG2tIBBmZkZB228YOh/jv5/5W0L3rFr1w5Tsx3m5rusLEytLMxg9UKUQgaGgw9kUPAbZBwIENg2IOVSfAl+VO+QAHI4wy+Gx0yK56fIEFVZaplGBUOVZKgkWWpwjBx6TL57tdpYKMlNEaSIo2RJfIVMqE5LRIpDVXLWtt+M8sBjACgEtr0nTlPEK6QiSZIghs8KpBMpvnhHjIOp6S4Y9GdImH3wlanpDjMzWFrmkOihLIR61xEF5xoU+NbZCePiDLkRql5n8D8UmTB6sg8eimkqieBH8Qr0I4YEkhnBVGYIPZIZEMUJFfJZCSIuVHaQt5XgidQk0ABFPLgoNys1NysF/AY9sHZMfvp+81RP8aHWnNYKeakmIU0mUEgEqfJ4iMzsbW2gyhiWYEA0AkY3GqWmpySAA2Hm5EnCOBEvRhDB5zM5nJDw8IDgIDKd5kWlepJJBDLZEwoXf5pPkJ8vI5DKCKIxg6Eu84tgBLCZQbyIkKhIRjSHCX4QRofHCiLihRzIWGAAYIBzJIkCqFcBWVKMQhKrUsIwYFSIc8BRGWmwZGRg52ggvSuhNfnp7y8vHqw+N1h2qqfoaJu2u1ZVlZuskQvTZLEqeRzI+Oglo0JoVcp4I6AKVh3klfQUsToF6Ydb2CcgYj+KT1WIlFKhLClansiXi6NlSTC4aAAMiGfAaCglcUpJPLQwaJgmmFk4ZMDojS2AfCIVZhZ+Mxla6IHXwGlpyFN4BCMEH4BCxID4RISd7i09P1hxaajywkD5me6iL/bnDjSoG4rl2jTwBnwmFqJOkyrOUiHeQxyFGBLQA79rRJ4shBZ0wkRAa9QMb/41ko2yU+UiMKBne90iLYC8tp2xtleycYjGqUFWjnG4RlWIPLCR2+R0ZCrhZ2HNJ6TI4iGLwBhgRpBZSBH/D9IcvcNdgAO9AAAAAElFTkSuQmCC";
        String picture2 ="data:image/png;base64,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";
        Car laguna_black = new Car("renault", "LAGUNA", "black", "1", 49, (float)0.15, "B", picture1);
        Car laguna_white = new Car("renault", "LAGUNA", "white", "1", 49, (float)0.15, "B", picture2);
        System.out.println(laguna_black.getPhoto());
        carList.add(laguna_white); carList.add(laguna_black);
        */
