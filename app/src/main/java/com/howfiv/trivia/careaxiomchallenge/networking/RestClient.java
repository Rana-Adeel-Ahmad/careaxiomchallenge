package com.howfiv.trivia.careaxiomchallenge.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Adeel Ahmad on 27/06/2019.
 */

public class RestClient {


	public static String BASE_URL = "https://jsonplaceholder.typicode.com/";


	private static synchronized Retrofit getInstance(String url) {

		return new Retrofit.Builder()
			.baseUrl(url)
			.addConverterFactory(GsonConverterFactory.create())
			.build();
	}

	/**
	 * Get API Service
	 *
	 * @return API Service
	 */

	public static ApiService getService(String url) {
		return getInstance(url).create(ApiService.class);
	}
}
