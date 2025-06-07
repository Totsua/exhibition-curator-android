package com.example.exhibitioncuratorandroid.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CuratorAPIService {


    @GET("/search")
    Call<List<ArtworkResults>> getArtworkSearchResults(@Query(value = "query") String query,@Query(value = "page")Integer page);

    @POST("/exhibitions/create")
    Call<Exhibition> createExhibition(@Body ExhibitionCreateDTO exhibitionCreateDTO);

    @GET("/exhibitions")
    Call<List<Exhibition>> getAllExhibitions();

    @DELETE("/exhibitions/{id}")
    Call<Void> deleteExhibition(@Path("id") Integer id);
}
