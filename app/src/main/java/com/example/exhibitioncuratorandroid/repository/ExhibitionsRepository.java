package com.example.exhibitioncuratorandroid.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.exhibitioncuratorandroid.model.ApiArtworkId;
import com.example.exhibitioncuratorandroid.model.Exhibition;
import com.example.exhibitioncuratorandroid.model.ExhibitionCreateDTO;
import com.example.exhibitioncuratorandroid.model.ExhibitionPatchDTO;
import com.example.exhibitioncuratorandroid.service.CuratorAPIService;
import com.example.exhibitioncuratorandroid.service.RetroFitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExhibitionsRepository {
    private MutableLiveData<List<Exhibition>> liveExhibitionList = new MutableLiveData<>();
    private MutableLiveData<Exhibition> liveExhibitionDetails = new MutableLiveData<>();
    private Application application;

    public ExhibitionsRepository(Application application) {
        this.application = application;
    }

    public void createExhibition(ExhibitionCreateDTO exhibitionCreateDTO, MutableLiveData<Boolean> isLoading, MutableLiveData<Boolean> isSuccessful) {
        CuratorAPIService curatorAPIService = RetroFitInstance.getService();
        Call<Void> call = curatorAPIService.createExhibition(exhibitionCreateDTO);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                isLoading.setValue(false);
                switch (response.code()) {
                    case 201:
                        isSuccessful.setValue(true);
                        Toast.makeText(application, "Exhibition Created", Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(application, "Title Already Used", Toast.LENGTH_SHORT).show();
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

    public MutableLiveData<List<Exhibition>> getAllExhibitions(MutableLiveData<Boolean> isLoading) {
        CuratorAPIService curatorAPIService = RetroFitInstance.getService();
        Call<List<Exhibition>> call = curatorAPIService.getAllExhibitions();
        call.enqueue(new Callback<List<Exhibition>>() {
            @Override
            public void onResponse(Call<List<Exhibition>> call, Response<List<Exhibition>> response) {
                isLoading.setValue(false);
                switch (response.code()) {
                    case 200:
                        List<Exhibition> exhibitionList = response.body();
                        liveExhibitionList.setValue(exhibitionList);
                        if(exhibitionList.isEmpty()){
                            Toast.makeText(application, "There are no exhibitions", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        if (response.body() != null) {
                            Log.e("SearchArtworkRepository OkHttpClient", String.valueOf(response.code()));
                            Log.e("SearchArtworkRepository OkHttpClient", response.body().toString());
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

    public void addArtworkToExhibition(Long exhibitionId, ApiArtworkId apiArtworkId, MutableLiveData<Boolean> isLoading) {
        CuratorAPIService apiService = RetroFitInstance.getService();
        Call<Void> call = apiService.addArtworkToExhibition(exhibitionId, apiArtworkId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                isLoading.setValue(false);
                switch (response.code()) {
                    case 201:
                        Toast.makeText(application, "Artwork Successfully Added", Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(application, "No Exhibitions With Chosen ID", Toast.LENGTH_SHORT).show();
                        break;
                    case 409:
                        Toast.makeText(application, "Artwork Already In Chosen Exhibition", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(application, "Unknown Server Error", Toast.LENGTH_SHORT).show();
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

    public MutableLiveData<Exhibition> getExhibitionDetails(Long exhibitionId, MutableLiveData<Boolean> isLoading) {
        CuratorAPIService apiService = RetroFitInstance.getService();
        Call<Exhibition> call = apiService.getExhibitionDetails(exhibitionId);
        call.enqueue(new Callback<Exhibition>() {
            @Override
            public void onResponse(Call<Exhibition> call, Response<Exhibition> response) {
                isLoading.setValue(false);
                switch (response.code()) {
                    case 200:
                        Exhibition exhibition = response.body();
                        liveExhibitionDetails.setValue(exhibition);
                        break;
                    case 404:
                        Toast.makeText(application, "Exhibition Does Not Exist", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(application, "Internal Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Exhibition> call, Throwable t) {
                isLoading.setValue(false);
            }
        });
        return liveExhibitionDetails;
    }

    public void deleteArtworkFromExhibition(Long exhibitionId, ApiArtworkId apiArtworkId,
                                            MutableLiveData<Boolean> isLoading, MutableLiveData<Boolean> isDeleted) {
        CuratorAPIService apiService = RetroFitInstance.getService();
        Call<Void> call = apiService.deleteArtworkFromExhibitions(exhibitionId, apiArtworkId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                isLoading.setValue(false);
                switch (response.code()) {
                    case 200:
                    case 204:
                        isDeleted.setValue(true);
                        Toast.makeText(application, "Artwork Removed Successfully", Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(application, "Exhibition Doesn't Exist", Toast.LENGTH_SHORT).show();
                        break;
                    case 400:
                        Toast.makeText(application, "This Artwork Is Not Saved", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(application, "Internal Server Error", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                isLoading.setValue(false);
                Log.e("RetrofitError", t.getMessage(), t);
                Toast.makeText(application, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void deleteExhibition(Long exhibitionId, MutableLiveData<Boolean> isLoading, MutableLiveData<Boolean> isDeleted){
        CuratorAPIService apiService = RetroFitInstance.getService();
        Call<Void> call = apiService.deleteExhibition(exhibitionId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                isLoading.setValue(false);
                switch (response.code()){
                    case 204:
                        isDeleted.setValue(true);
                        Toast.makeText(application, "Exhibition Successfully Deleted", Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(application, "Exhibition Does Not Exist", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(application, "Internal Server Error", Toast.LENGTH_SHORT).show();
                        Log.d("Exhibition Repository", String.valueOf(response.code()));
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                isLoading.setValue(false);
                Log.e("RetrofitError", t.getMessage(), t);
                Toast.makeText(application, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateExhibition(Long exhibitionId, ExhibitionPatchDTO exhibition, MutableLiveData<Boolean> isloading, MutableLiveData<Boolean> isSuccessful){
        CuratorAPIService apiService = RetroFitInstance.getService();
        Call<Exhibition> call = apiService.updateExhibition(exhibitionId, exhibition);
        call.enqueue(new Callback<Exhibition>() {
            @Override
            public void onResponse(Call<Exhibition> call, Response<Exhibition> response) {
                isloading.setValue(false);
                switch (response.code()){
                    case 200:
                        isSuccessful.setValue(true);
                        Toast.makeText(application, "Update Successful", Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(application, "Exhibition Does Not Exist", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(application, "Internal Server Error", Toast.LENGTH_SHORT).show();
                        Log.d("Exhibition Repository", String.valueOf(response.code()));
                        break;
                }
            }

            @Override
            public void onFailure(Call<Exhibition> call, Throwable t) {
                isloading.setValue(false);
                Log.e("RetrofitError", t.getMessage(), t);
                Toast.makeText(application, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
