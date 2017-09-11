package ru.prog_edu.testtask.retrofit.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import ru.prog_edu.testtask.model.UserModel;

/**
 * Created by Leno on 19.08.2017.
 */

public interface ApiService {

     String URL = "https://jsonplaceholder.typicode.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    @GET("/users")
    Call<List<UserModel>> getAllJSON();//делаю метод без аннотаций, так как вероятно будем использовать весь json


}
