package com.howfiv.trivia.careaxiomchallenge;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.howfiv.trivia.careaxiomchallenge.networking.ApiModel;
import com.howfiv.trivia.careaxiomchallenge.networking.ApiService;
import com.howfiv.trivia.careaxiomchallenge.networking.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class MainRepository {
    private SharedPreferences sharedPreferences;
    public static final String SharedPrefrenceData = "api_shared_prefernce_data";
    MutableLiveData<List<ApiModel>> status = new MutableLiveData<>();
    Context context;

    MainRepository(Application application) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application);
        context = application.getBaseContext();

    }

    public LiveData<List<ApiModel>> getStatus() {
        // Shared perference used for storing
        if (sharedPreferences.getString(SharedPrefrenceData, null) != null) {
            String data = sharedPreferences.getString(SharedPrefrenceData, null);
            Gson json = new Gson();
            Type type = new TypeToken<List<ApiModel>>() {}.getType();

            status.setValue(json.fromJson(data, type));

        }

        if (status.getValue() == null) {
            CallApi();
        }
        return status;
    }

    private void CallApi() {
        ApiService apiService = RestClient.getService(RestClient.BASE_URL);
        apiService.getApiData().enqueue(new Callback<List<ApiModel>>() {
            @Override
            public void onResponse(Call<List<ApiModel>> call, Response<List<ApiModel>> response) {
                if (response.body() != null) {
                    //update Shared preference
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    editor.putString(SharedPrefrenceData, gson.toJson(response.body()));
                    editor.apply();
                    //update Mutable Live Data
                    status.setValue(response.body());

                }

            }

            @Override
            public void onFailure(Call<List<ApiModel>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                status.setValue(new ArrayList<ApiModel>());
            }
        });
    }

}
