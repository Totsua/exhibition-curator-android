package com.example.exhibitioncuratorandroid.service;

import com.example.exhibitioncuratorandroid.model.ApiArtworkId;
import com.example.exhibitioncuratorandroid.model.Artwork;
import com.example.exhibitioncuratorandroid.model.ArtworkResults;
import com.example.exhibitioncuratorandroid.model.Exhibition;
import com.example.exhibitioncuratorandroid.model.ExhibitionCreateDTO;
import com.example.exhibitioncuratorandroid.model.ExhibitionPatchDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CuratorAPIService {


    @GET("search")
    Call<ArtworkResults> getArtworkSearchResults(@Query(value = "query") String query, @Query(value = "page")Integer page);

    @GET("random")
    Call<Artwork> getRandomMetArtwork();

    @POST("exhibitions/create")
    Call<Void> createExhibition(@Body ExhibitionCreateDTO exhibitionCreateDTO);

    @PATCH("exhibitions/{id}")
    Call<Exhibition> updateExhibition(@Path("id") Long exhibitionId, @Body ExhibitionPatchDTO exhibition);

    @GET("exhibitions")
    Call<List<Exhibition>> getAllExhibitions();

    @POST("exhibitions/{id}/artworks")
    Call<Void> addArtworkToExhibition(@Path("id") Long exhibitionId, @Body ApiArtworkId artworkIdDTO);

    @GET("exhibitions/{id}")
    Call<Exhibition> getExhibitionDetails(@Path("id") Long exhibitionId);

    @HTTP(method = "DELETE", path = "exhibitions/{exhibitionId}/artworks", hasBody = true)
    Call<Void> deleteArtworkFromExhibitions(@Path("exhibitionId") Long exhibitionId, @Body ApiArtworkId apiArtworkId);

    @DELETE("exhibitions/{id}")
    Call<Void> deleteExhibition(@Path("id") Long id);
}
