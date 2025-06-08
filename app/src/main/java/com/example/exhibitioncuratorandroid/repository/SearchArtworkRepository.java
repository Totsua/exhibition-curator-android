package com.example.exhibitioncuratorandroid.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.exhibitioncuratorandroid.model.ArtworkResults;
import com.example.exhibitioncuratorandroid.service.CuratorAPIService;
import com.example.exhibitioncuratorandroid.service.RetroFitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchArtworkRepository {
    private MutableLiveData<ArtworkResults> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public SearchArtworkRepository(Application application){this.application = application;}

    public MutableLiveData<ArtworkResults> getMutableLiveData(String query, Integer page, MutableLiveData<Boolean> isLoading){
        CuratorAPIService curatorAPIService = RetroFitInstance.getService();
        Call<ArtworkResults> call = curatorAPIService.getArtworkSearchResults(query, page);
        call.enqueue(new Callback<ArtworkResults>() {
            @Override
            public void onResponse(Call<ArtworkResults> call, Response<ArtworkResults> response) {
                isLoading.setValue(false);
                if(response.code() == 200){
                    ArtworkResults results = response.body();
                    mutableLiveData.setValue(results);
                }else{ // todo: server codes and what they mean as Toasts
                    Toast.makeText(application, "", Toast.LENGTH_SHORT).show();
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

}
