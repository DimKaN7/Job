package com.example.diplom1.Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Material {

    int image;
    String title;
    String about;
    double price;

    public Material(int image, String title, String about, double price) {
        this.image = image;
        this.title = title;
        this.about = about;
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getAbout() {
        return about;
    }

    public double getPrice() { return price; }

    //    сериализованные имена - имена свойств класса Material на серваке
//    @SerializedName("Id")
//    @Expose
//    private Integer id;
//    @SerializedName("Title")
//    @Expose
//    private String title;
//    @SerializedName("MaterialCount")
//    @Expose
//    private String materialCount;
//    @SerializedName("MaterialCode")
//    @Expose
//    private String materialCode;
//    @SerializedName("CountryOfOrigin")
//    @Expose
//    private String countryOfOrigin;
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getMaterialCount() {
//        return materialCount;
//    }
//
//    public void setMaterialCount(String materialCount) {
//        this.materialCount = materialCount;
//    }
//
//    public String getMaterialCode() {
//        return materialCode;
//    }
//
//    public void setMaterialCode(String materialCode) {
//        this.materialCode = materialCode;
//    }
//
//    public String getCountryOfOrigin() {
//        return countryOfOrigin;
//    }
//
//    public void setCountryOfOrigin(String countryOfOrigin) {
//        this.countryOfOrigin = countryOfOrigin;
//    }
//
//    @Override
//    public String toString() {
//        return "Id: " + id.toString() + ", title: " + title + ", count: " + materialCount.toString();
//    }
}
