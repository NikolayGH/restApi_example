package ru.prog_edu.testtask.ui;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.prog_edu.testtask.R;
import ru.prog_edu.testtask.model.AlbumModel;
import ru.prog_edu.testtask.retrofit.api.ApiAlbumsService;

//фрагмент для отображения списка альбомов
public class AlbumListFragment extends ListFragment{

    private ArrayList<AlbumModel> albumsList;//поле листа типа AlbumModel
    String userNumber = ""; // поле для получения юзера
    Context context;//поле контекста для исполхования его в toast

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userNumber = ((SelectedUserActivity)getActivity()).getSelectedUser();// получаем поле с номером выбранного активити. По хорошему нужно делать через интерфейс, так как сейчас фрагмент привязан к одной активити. Модульность не соблюдается.
        int intUserNumber = Integer.parseInt(userNumber)+1;//добавляем единицу для получения точного ID юзера
        userNumber = String.valueOf(intUserNumber);//переводим все в  String Для использования в Retrofit.
        albumsList = new ArrayList<>(); //создаем лист
        ApiAlbumsService apiAlbumsService = ApiAlbumsService.retrofit.create(ApiAlbumsService.class);//объект апи для обработки результатов запроса
        final Call<List<AlbumModel>> callAlbums = apiAlbumsService.getUserAlbumsJSON(userNumber); // используя сервис, получаем список альбомов конкретного юзера.
        callAlbums.enqueue(new Callback<List<AlbumModel>>() {// используем метод enqueu для асинхронного запроса
            @Override
            public void onResponse(Call<List<AlbumModel>> call, Response<List<AlbumModel>> response) {
                if (response.isSuccessful()) {
                    albumsList.addAll(response.body());//добавляем в лист тело запроса
                    ArrayList<String> titleList = new ArrayList<String>();//создаем лист для названий альбомов пользователя

                    for (int i = 0; i < response.body().size(); i++) {//по размеру полученного ответа помещаем в лист названия альбомов
                        titleList.add(response.body().get(i).getTitle().toString());
                    }
                    //создаем некастомизированный адаптер
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                            android.R.layout.simple_list_item_1, titleList);
                            setListAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<AlbumModel>> call, Throwable t) {
                Toast toast = Toast.makeText(context, "FAIL", Toast.LENGTH_LONG);//в случае неудачи запроса выкидываем сообщение.
                toast.show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.albums_list_fragment, null);
    }
    //обрабатываем нажатие на элемент списка. Для дальнейшего использования в активити применяем интерфейс.
    public void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l,v,position,id);
        OnSelectedItemListener_albums listener_albums = (OnSelectedItemListener_albums) getActivity();// получение активити, которая использует фрагмент
        listener_albums.onItemSelectedAlbums(position);
            }
    //интерфейс для обязательной реалзации метода по выбору элемента списка.
    public interface OnSelectedItemListener_albums{
        void onItemSelectedAlbums(int itemIndex);
    }
}
