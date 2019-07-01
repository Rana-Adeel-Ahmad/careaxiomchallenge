package com.howfiv.trivia.careaxiomchallenge;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.howfiv.trivia.careaxiomchallenge.networking.ApiModel;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private MainRepository mainRepository;


    public MainViewModel(@NonNull Application application) {
        super(application);
        mainRepository = new MainRepository(application);
    }
    public LiveData<List<ApiModel>> getApiData() { return mainRepository.getStatus();}




}
