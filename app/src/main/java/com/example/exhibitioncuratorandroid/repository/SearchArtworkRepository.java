package com.example.exhibitioncuratorandroid.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.exhibitioncuratorandroid.model.Artwork;
import com.example.exhibitioncuratorandroid.model.ArtworkResults;
import com.example.exhibitioncuratorandroid.service.CuratorAPIService;
import com.example.exhibitioncuratorandroid.service.RetroFitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchArtworkRepository {
    private MutableLiveData<ArtworkResults> mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Artwork> liveArtwork = new MutableLiveData<>();
    private Application application;

    public SearchArtworkRepository(Application application){this.application = application;}

    public MutableLiveData<ArtworkResults> getMutableLiveData(String query, Integer page, MutableLiveData<Boolean> isLoading){
        CuratorAPIService curatorAPIService = RetroFitInstance.getService();
        Call<ArtworkResults> call = curatorAPIService.getArtworkSearchResults(query, page);
        call.enqueue(new Callback<ArtworkResults>() {
            @Override
            public void onResponse(Call<ArtworkResults> call, Response<ArtworkResults> response) {
                isLoading.setValue(false);
                switch (response.code()){
                    case 200:
                        ArtworkResults results = response.body();
                        mutableLiveData.setValue(results);
                        break;
                    case 416:
                        Toast.makeText(application, "Page number out of bounds", Toast.LENGTH_SHORT).show();
                        break;
                    case 500:
                        if (response.body() != null) {
                            Log.e("SearchArtworkRepository OkHttpClient",response.body().toString());
                        }
                        ArtworkResults artwork500Results = new ArtworkResults(query,page,new ArrayList<>(),1);
                        mutableLiveData.setValue(artwork500Results);
                        Toast.makeText(application, "Server Error Occurred", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(application, "Unknown Server Error: Contact the developer", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArtworkResults> call, Throwable t) {
                isLoading.setValue(false);
                Toast.makeText(application, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });

        return mutableLiveData;

    }

    public MutableLiveData<Artwork> getRandomMetArtwork(MutableLiveData<Boolean> isLoading){
        CuratorAPIService apiService = RetroFitInstance.getService();
        Call<Artwork> call = apiService.getRandomMetArtwork();
        call.enqueue(new Callback<Artwork>() {
            @Override
            public void onResponse(Call<Artwork> call, Response<Artwork> response) {
                isLoading.setValue(false);
                switch (response.code()){
                    case 200:
                        liveArtwork.setValue(response.body());
                        break;
                    case 416:
                        Toast.makeText(application, "MAX ATTEMPTS TRY AGAIN", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(application,"Internal Server Error", Toast.LENGTH_SHORT).show();
                        Log.d("SearchArtwork Repository", String.valueOf(response.code()));
                        break;
                }
            }

            @Override
            public void onFailure(Call<Artwork> call, Throwable t) {
                isLoading.setValue(false);
                Toast.makeText(application, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
        return liveArtwork;
    }


}
