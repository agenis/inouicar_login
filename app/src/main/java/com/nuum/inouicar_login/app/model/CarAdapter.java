package com.nuum.inouicar_login.app.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nuum.inouicar_login.R;

import java.util.List;

/*
* RecyclerView.Adapter
* RecyclerView.ViewHolder
 */
public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder>{

    private Context mCtx;
    private List<Car> carList;

    public CarAdapter(Context mCtx, List<Car> carList) {
        this.mCtx = mCtx;
        this.carList = carList;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.list_layout, null);
        return new CarViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {

        Car car = carList.get(position);
        holder.textViewModel.setText(car.getModel());
        holder.textViewBrand.setText(car.getBrand());
        holder.textViewColor.setText(car.getColor());
        holder.textViewPrice_per_day.setText(car.getPricePerDay() + "€/day");
        holder.imageView.setImageBitmap(car.getPhotoBitmap());

    }

    @Override
    public int getItemCount() {
        return this.carList.size();
    }

    class CarViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewBrand, textViewModel, textViewColor, textViewPrice_per_day;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewBrand = itemView.findViewById(R.id.textViewBrand);
            textViewColor = itemView.findViewById(R.id.textViewColor);
            textViewModel = itemView.findViewById(R.id.textViewModel);
            textViewPrice_per_day = itemView.findViewById(R.id.textViewPrice_per_day);

        }
    }

}
