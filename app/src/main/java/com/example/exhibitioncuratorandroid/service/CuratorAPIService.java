package com.example.exhibitioncuratorandroid.service;

import com.example.exhibitioncuratorandroid.model.ApiArtworkId;
import com.example.exhibitioncuratorandroid.model.ArtworkResults;
import com.example.exhibitioncuratorandroid.model.Exhibition;
import com.example.exhibitioncuratorandroid.model.ExhibitionCreateDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CuratorAPIService {


    @GET("search")
    Call<ArtworkResults> getArtworkSearchResults(@Query(value = "query") String query, @Query(value = "page")Integer page);

    @POST("exhibitions/create")
    Call<Void> createExhibition(@Body ExhibitionCreateDTO exhibitionCreateDTO);

    @GET("exhibitions")
    Call<List<Exhibition>> getAllExhibitions();

    @POST("exhibitions/{id}/artworks")
    Call<Void> addArtworkToExhibition(@Path("id") Long exhibitionId, @Body ApiArtworkId artworkIdDTO);

    @GET("exhibitions/{id}")
    Call<Exhibition> getExhibitionDetails(@Path("id") Long exhibitionId);

    @HTTP(method = "DELETE", path = "exhibitions/{exhibitionId}/artworks", hasBody = true)
    Call<Void> deleteArtworkFromExhibitions(@Path("exhibitionId") Long exhibitionId, @Body ApiArtworkId apiArtworkId);

 /*   @DELETE("exhibitions/{id}")
    Call<Void> deleteExhibition(@Path("id") Integer id);*/
}
