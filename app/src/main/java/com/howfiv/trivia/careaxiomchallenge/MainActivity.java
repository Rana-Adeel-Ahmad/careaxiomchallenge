package com.howfiv.trivia.careaxiomchallenge;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.howfiv.trivia.careaxiomchallenge.networking.ApiModel;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

import java.util.List;

import static java.util.stream.Collectors.toList;


@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {

    MainViewModel mainViewModel;
    ProgressDialog dialog;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        loadingDialog();

        mainViewModel.getApiData().observe(this, ApiResponseObserver);
    }
    Observer<? super List<ApiModel>> ApiResponseObserver = (Observer<List<ApiModel>>) apiModel -> {
        if(apiModel != null){
            dialog.dismiss();
            SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();
            int albumId = apiModel.get(apiModel.size()-1).getAlbumId();
            for(int i = 1; i <= albumId; i++){
                int finalI = i;
                List<ApiModel> newList = apiModel.stream().filter(apiModel1 -> apiModel1.getAlbumId() == finalI).collect(toList());
                sectionAdapter.addSection(new MySection(getApplicationContext(),"Album ID: "+i , newList));

            }

            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(sectionAdapter);
        }
    };
    private void loadingDialog() {
         dialog = ProgressDialog.show(MainActivity.this, "",
                "Fetching. Please wait...", true);
    }
}
