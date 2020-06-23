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
    private OnCarListener mOnCarListener;

    public CarAdapter(Context mCtx, List<Car> carList, OnCarListener onCarListener) {
        this.mCtx = mCtx;
        this.carList = carList;
        this.mOnCarListener = onCarListener;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.list_layout, null);
        return new CarViewHolder(view, mOnCarListener);

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

    class CarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textViewBrand, textViewModel, textViewColor, textViewPrice_per_day;

        OnCarListener onCarListener;

        public CarViewHolder(@NonNull View itemView, OnCarListener onCarListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewBrand = itemView.findViewById(R.id.textViewBrand);
            textViewColor = itemView.findViewById(R.id.textViewColor);
            textViewModel = itemView.findViewById(R.id.textViewModel);
            textViewPrice_per_day = itemView.findViewById(R.id.textViewPrice_per_day);
            this.onCarListener = onCarListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onCarListener.onCarClick(getAdapterPosition());
        }
    }

    public interface OnCarListener{
        // video tuto pour gerer le clic sur un item de la liste de voiture: https://www.youtube.com/watch?v=69C1ljfDvl0
        // ici comme c'est une interface on détaille pas la méthode on met juste son nom:
        // elle est implementee dans CatalogueActivity
        void onCarClick(int position);
    }

}
