package ru.prog_edu.testtask.retrofit.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.prog_edu.testtask.model.PostModel;


public interface ApiPostsServise {
    String URL_POSTRS = "https://jsonplaceholder.typicode.com/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL_POSTRS)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("/user/{userId}/posts")
    Call<List<PostModel>> getPostsJSON(@Path("userId") String userID);


}
