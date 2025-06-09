package com.example.exhibitioncuratorandroid.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.exhibitioncuratorandroid.model.Exhibition;
import com.example.exhibitioncuratorandroid.model.ExhibitionCreateDTO;
import com.example.exhibitioncuratorandroid.service.CuratorAPIService;
import com.example.exhibitioncuratorandroid.service.RetroFitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExhibitionsRepository {
    private MutableLiveData<List<Exhibition>> liveExhibitionList = new MutableLiveData<>();
    private Application application;

    public ExhibitionsRepository(Application application){this.application = application;}

    public void createExhibition(String title, MutableLiveData<Boolean> isLoading,MutableLiveData<Boolean> isSuccessful){
        CuratorAPIService curatorAPIService = RetroFitInstance.getService();
        Call<Void> call = curatorAPIService.createExhibition(new ExhibitionCreateDTO(title));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                isLoading.setValue(false);
                switch (response.code()){
                    case 201:
                        isSuccessful.setValue(true);
                        Toast.makeText(application, "Exhibition Created", Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(application,"Title Already Used",Toast.LENGTH_SHORT).show();
                        break;
                    case 500:
                        Toast.makeText(application, "Internal Server Error", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(application, "Unknown Server Error: Contact the developer", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                isLoading.setValue(false);
                Toast.makeText(application, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public MutableLiveData<List<Exhibition>> getAllExhibitions(MutableLiveData<Boolean> isLoading){
        CuratorAPIService curatorAPIService = RetroFitInstance.getService();
        Call<List<Exhibition>> call = curatorAPIService.getAllExhibitions();
        call.enqueue(new Callback<List<Exhibition>>() {
            @Override
            public void onResponse(Call<List<Exhibition>> call, Response<List<Exhibition>> response) {
                isLoading.setValue(false);
                switch (response.code()){
                    case 200:
                        List<Exhibition> exhibitionList = response.body();
                        liveExhibitionList.setValue(exhibitionList);
                        break;
                    default:
                        if (response.body() != null) {
                            Log.e("SearchArtworkRepository OkHttpClient", String.valueOf(response.code()));
                            Log.e("SearchArtworkRepository OkHttpClient",response.body().toString());
                        }

                        liveExhibitionList.setValue(new ArrayList<>());
                        Toast.makeText(application, "Unknown Error Occurred", Toast.LENGTH_SHORT).show();
                        break;
                }

            }

            @Override
            public void onFailure(Call<List<Exhibition>> call, Throwable t) {
                isLoading.setValue(false);
                Toast.makeText(application, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
        return liveExhibitionList;
    }

}
