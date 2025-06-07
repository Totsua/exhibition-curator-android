package com.example.exhibitioncuratorandroid.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.exhibitioncuratorandroid.model.ArtworkResults;
import com.example.exhibitioncuratorandroid.repository.SearchArtworkRepository;

import java.util.List;

public class SearchArtworkResultsViewModel extends AndroidViewModel {
    SearchArtworkRepository searchArtworkRepository;
    public SearchArtworkResultsViewModel(@NonNull Application application) {
        super(application);
        this.searchArtworkRepository = new SearchArtworkRepository(application);
    }
    public MutableLiveData<List<ArtworkResults>> getArtworkSearchResults(String query, Integer page){
        return searchArtworkRepository.getMutableLiveData(query,page);
    }
}
