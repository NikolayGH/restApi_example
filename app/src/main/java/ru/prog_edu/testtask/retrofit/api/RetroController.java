package ru.prog_edu.testtask.retrofit.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Leno on 19.08.2017.
 */

public class RetroController {
    private static final String URL = "https://jsonplaceholder.typicode.com/";

    private static Retrofit getRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
           }

    public static ApiPostPosts getRetroController(){
        return getRetrofitInstance().create(ApiPostPosts.class);
    }
}
