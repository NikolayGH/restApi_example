package ru.prog_edu.testtask.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.prog_edu.testtask.R;
import ru.prog_edu.testtask.adapter.UsersAdapter;
import ru.prog_edu.testtask.model.UserModel;
import ru.prog_edu.testtask.retrofit.api.ApiService;

public class MainActivity extends AppCompatActivity {

    private ListView listView; //переменная для списка
    private UsersAdapter adapter; //переменная для кастомного адаптера
    private ArrayList<UserModel> userList; //создаем список объектов модели пользователя

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userList = new ArrayList<>();//создаем лист с будущими объектами запроса
        listView = (ListView) findViewById(R.id.listView);//привязываем ЛистВью
        //листенер для открытия активити для отображения информации о пользователе и передачей в активити номер выбранного юзера
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, SelectedUserActivity.class);
                intent.putExtra("selectedUser", position);//передаем позицию в интент
                intent.putExtra("selectedUserName", userList.get(position).getName());//здесь пробуем получить имя выбранного юзера и передать его в следующее активити.
                startActivity(intent);//стартуем активити с интентом
            }
        });

    ApiService apiService = ApiService.retrofit.create(ApiService.class);//объект сервиса с созданием ретрофит
    final Call<List<UserModel>> call = apiService.getAllJSON(); //создаем список типа ЮзерМоделей и помещаем туда результаты запроса прописанного в интерфейсе

        call.enqueue(new Callback<List<UserModel>>(){//вызов асинхронного метода
            @Override
            public void onResponse
            (Call < List < UserModel >> call, Response < List < UserModel >> response){

            if (response.isSuccessful()) {
                userList.addAll(response.body());//добавляем весь запрос как он есть в лист
                adapter = new UsersAdapter(MainActivity.this, userList);//создаем объект своего кастомизированного адаптера и помещаем туда данные запроса
                listView.setAdapter(adapter);//сетим адаптер в листВью
        }
    }
        @Override
        public void onFailure (Call < List < UserModel >> call, Throwable t){
        Toast toast = Toast.makeText(getApplicationContext(), "FAIL", Toast.LENGTH_LONG);
                toast.show(); //в случае неудачи отображаем соответствующее сообщение.
            }
        });
    }
}
