package com.example.exhibitioncuratorandroid.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.exhibitioncuratorandroid.model.Exhibition;
import com.example.exhibitioncuratorandroid.repository.ExhibitionsRepository;

import java.util.List;

public class ExhibitionsViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private ExhibitionsRepository exhibitionsRepository;

    public ExhibitionsViewModel(@NonNull Application application) {
        super(application);
        this.exhibitionsRepository = new ExhibitionsRepository(application);
    }
    public void createExhibition(String title){
        isLoading.setValue(true);
        exhibitionsRepository.createExhibition(title,isLoading);
    }
    public MutableLiveData<List<Exhibition>> getAllExhibitions(){
        isLoading.setValue(true);
        return exhibitionsRepository.getAllExhibitions(isLoading);
    }
    public LiveData<Boolean> getIsLoading(){return isLoading;}
}
