package com.example.exhibitioncuratorandroid.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.exhibitioncuratorandroid.model.ArtworkResults;
import com.example.exhibitioncuratorandroid.service.CuratorAPIService;
import com.example.exhibitioncuratorandroid.service.RetroFitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchArtworkRepository {
    private MutableLiveData<List<ArtworkResults>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public SearchArtworkRepository(Application application){this.application = application;}

    public MutableLiveData<List<ArtworkResults>> getMutableLiveData(String query, Integer page){
        CuratorAPIService curatorAPIService = RetroFitInstance.getService();
        Call<List<ArtworkResults>> call = curatorAPIService.getArtworkSearchResults(query, page);
        call.enqueue(new Callback<List<ArtworkResults>>() {
            @Override
            public void onResponse(Call<List<ArtworkResults>> call, Response<List<ArtworkResults>> response) {
                if(response.code() == 200){
                    List<ArtworkResults> results = response.body();
                    mutableLiveData.setValue(results);
                }else{ // todo: server codes and what they mean as Toasts
                    Toast.makeText(application, "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ArtworkResults>> call, Throwable t) {
                Toast.makeText(application, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });

        return mutableLiveData;

    }

}
