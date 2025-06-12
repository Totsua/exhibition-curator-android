package com.example.exhibitioncuratorandroid.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.exhibitioncuratorandroid.model.ApiArtworkId;
import com.example.exhibitioncuratorandroid.model.Exhibition;
import com.example.exhibitioncuratorandroid.model.ExhibitionPatchDTO;
import com.example.exhibitioncuratorandroid.repository.ExhibitionsRepository;

import java.util.List;

public class ExhibitionsViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSuccessful = new MutableLiveData<>();
    private MutableLiveData<Boolean> isDeleted = new MutableLiveData<>();
    private ExhibitionsRepository exhibitionsRepository;

    public ExhibitionsViewModel(@NonNull Application application) {
        super(application);
        this.exhibitionsRepository = new ExhibitionsRepository(application);
    }
    public void createExhibition(String title){
        isLoading.setValue(true);
        isSuccessful.setValue(false);
        exhibitionsRepository.createExhibition(title,isLoading, isSuccessful);
    }
    public MutableLiveData<List<Exhibition>> getAllExhibitions(){
        isLoading.setValue(true);
        return exhibitionsRepository.getAllExhibitions(isLoading);
    }


    public void addArtworkToExhibition(Long exhibitionId,ApiArtworkId apiArtworkId){
        isLoading.setValue(true);
        exhibitionsRepository.addArtworkToExhibition(exhibitionId,apiArtworkId,isLoading);
    }

    public MutableLiveData<Exhibition> getExhibitionDetails(Long exhibitionId){
        isLoading.setValue(true);
        return exhibitionsRepository.getExhibitionDetails(exhibitionId,isLoading);
    }

    public void deleteArtworkFromExhibition(Long exhibitionId, ApiArtworkId apiArtworkId){
        isLoading.setValue(true);
        isDeleted.setValue(false);
        exhibitionsRepository.deleteArtworkFromExhibition(exhibitionId, apiArtworkId, isLoading,isDeleted);
    }

    public void deleteExhibition(Long exhibitionId){
        isLoading.setValue(true);
        isDeleted.setValue(false);
        exhibitionsRepository.deleteExhibition(exhibitionId,isLoading,isDeleted);
    }

    public void updateExhibition(Long exhibitionId, ExhibitionPatchDTO exhibitionPatchDTO){
        isLoading.setValue(true);
        isSuccessful.setValue(false);
        exhibitionsRepository.updateExhibition(exhibitionId,exhibitionPatchDTO ,isLoading,isSuccessful);
    }

    public LiveData<Boolean> getIsLoading(){return isLoading;}

    public LiveData<Boolean> getIsSuccessful(){return isSuccessful;}
    public LiveData<Boolean> getIsDeleted(){return isDeleted;}
}
