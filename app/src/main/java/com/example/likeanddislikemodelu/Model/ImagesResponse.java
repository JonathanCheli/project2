package com.example.likeanddislikemodelu.Model;

public class ImagesResponse {


    private int sku;

    private String title;

    private int images;



    public ImagesResponse(int images, String title) {
        this.images = images;
        this.title = title;
    }

    public int getSku() {
        return sku;
    }

    public void setSku(int sku) {
        this.sku = sku;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }
}
