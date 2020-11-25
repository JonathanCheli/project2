package com.example.likeanddislikemodelu;

import com.example.likeanddislikemodelu.Model.ImagesResponse;
import com.google.gson.annotations.SerializedName;

public class JSONResponse {


    private ImagesResponse [] articles;

    public ImagesResponse[] getArticles() {
        return articles;
    }

    public void setArticles(ImagesResponse[] articles) {
        this.articles = articles;
    }
}
