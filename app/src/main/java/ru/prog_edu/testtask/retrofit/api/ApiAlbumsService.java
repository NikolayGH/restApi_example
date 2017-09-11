package ru.prog_edu.testtask.retrofit.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.prog_edu.testtask.model.AlbumModel;



//интерфейс для управления запросом
public interface ApiAlbumsService {
    String URL = "https://jsonplaceholder.typicode.com/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())//подключаем конвертер
            .build();

    @GET("/user/{userId}/albums")
    Call<List<AlbumModel>> getUserAlbumsJSON(
            @Path("userId") String userID
    );//метод с запросом перечня альбомов конкретного юзера.
}
