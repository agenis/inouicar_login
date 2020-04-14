package com.nuum.inouicar_login.app.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Car {

    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("is_available")
    @Expose
    private String isAvailable;
    @SerializedName("price_per_day")
    @Expose
    private float pricePerDay;
    @SerializedName("price_per_km")
    @Expose
    private float pricePerKm;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("has_remote_locking")
    @Expose
    private String has_remote_locking;
    @SerializedName("photo")
    @Expose
    private String photo;

    /**
     * No args constructor for use in serialization
     *
     */
    public Car() {
    }

    /**
     *
     * @param isAvailable
     * @param color
     * @param pricePerKm
     * @param photo
     * @param model
     * @param label
     * @param brand
     * @param pricePerDay
     * @param has_remote_locking
     */
    public Car(String brand, String model, String color, String isAvailable, float pricePerDay, float pricePerKm, String label, String has_remote_locking, String photo) {
        super();
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.isAvailable = isAvailable;
        this.pricePerDay = pricePerDay;
        this.pricePerKm = pricePerKm;
        this.label = label;
        this.has_remote_locking = has_remote_locking;
        this.photo = photo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public float getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(float pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public float getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(float pricePerKm) {
        this.pricePerKm = pricePerKm;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String gethas_remote_locking() {
        return has_remote_locking;
    }

    public void sethas_remote_locking(String has_remote_locking) {
        this.has_remote_locking = has_remote_locking;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Bitmap getPhotoBitmap() {

        String encodedString = this.photo; //"data:image/jpg;base64, ....";
        String pureBase64Encoded = encodedString.substring(encodedString.indexOf(",")  + 1);
        byte[] decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

    }


}