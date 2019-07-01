package com.howfiv.trivia.careaxiomchallenge.networking;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Created by Adeel Ahmad on 27/06/2019.
 */

public interface ApiService {
	@GET("photos")
	Call<List<ApiModel>> getApiData();
}
