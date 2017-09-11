package ru.prog_edu.testtask.retrofit.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import ru.prog_edu.testtask.model.PostModel;


public interface ApiPostPosts {

    @POST("/posts")//указываем конечный фрагмент адреса
    @FormUrlEncoded// эта аннотация позволяет использовать кодировку UTF-8
    Call<PostModel> savePost(@Field("title") String title,//аннотация @Field("key") задает ключи для запроса
                             @Field("body") String body,
                             @Field("userId") long userId);

    @POST("/posts")//указываем конечный фрагмент адреса
    @FormUrlEncoded
    Call<PostModel> savePost(@Body PostModel post);
}
