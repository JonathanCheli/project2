package com.example.likeanddislikemodelu;

import com.example.likeanddislikemodelu.Model.ImagesResponse;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;
public interface APIinterface {



    @GET("/api/v2.0/articles/000000001000091266?appDomain=1&locale=de_DE")
    Call<JSONResponse> getAllImages();


}
