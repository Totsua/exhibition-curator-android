package com.example.exhibitioncuratorandroid.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.exhibitioncuratorandroid.model.ArtworkResults;
import com.example.exhibitioncuratorandroid.repository.SearchArtworkRepository;


public class SearchArtworkResultsViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private SearchArtworkRepository searchArtworkRepository;
    public SearchArtworkResultsViewModel(@NonNull Application application) {
        super(application);
        this.searchArtworkRepository = new SearchArtworkRepository(application);
    }
    public MutableLiveData<ArtworkResults> getArtworkSearchResults(String query, Integer page){
        isLoading.setValue(true);
        return searchArtworkRepository.getMutableLiveData(query,page, isLoading);
    }
    public LiveData<Boolean> getIsLoading(){return isLoading;}
}
