package ru.prog_edu.testtask.ui;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.prog_edu.testtask.R;
import ru.prog_edu.testtask.model.PostModel;
import ru.prog_edu.testtask.retrofit.api.ApiPostPosts;
import ru.prog_edu.testtask.retrofit.api.RetroController;
//активити для отображения в нем двух фрагментов в зависимости от положения свитч.
public class SelectedUserActivity extends AppCompatActivity implements AlbumListFragment.OnSelectedItemListener_albums,
        CompoundButton.OnCheckedChangeListener, FragmentDialog.DialogListener, PostsListFragment.OnSelectedItemListener_posts
{

    AlbumListFragment albumListFragment;//поля врагментов
    PostsListFragment postsListFragment;
    FragmentTransaction fTrans_user;//поле транзакции
    private ApiPostPosts apiPostPosts;//поле для апи размещения новых постов
    public String selectedUser;//поле выбранного юзера
    public String nameSelectedUser;//поле для имени выбранного юзера
    private TextView userDescriptionText;//поле номера выбранного юзера
    private Switch post_albums_selector;//селектор выбора отображаемых фрагментов
    FloatingActionButton  floatingActionButton;

    public String getSelectedUser() {// геттер для получения юзера для списка его альбомов
        return selectedUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_user);

        apiPostPosts = RetroController.getRetroController();//создаем ретрофит через метод в классе RetroController

        post_albums_selector = (Switch) findViewById(R.id.switch1);
        if(post_albums_selector!=null){
            post_albums_selector.setOnCheckedChangeListener(this);//устанавливаем листенер на свитч
        }
        // создаем FAB
        floatingActionButton = (FloatingActionButton) findViewById(R.id.activity_user_fab);
        View.OnClickListener fabOnclickListener = new View.OnClickListener() {
            @Override//привязываем на клик создание фрагмент-диалога для отправки поста.
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentDialog fragmentDialog = new FragmentDialog();
                fragmentDialog.show(manager, "dialog");
            }
        };

        floatingActionButton.setOnClickListener(fabOnclickListener);//цепляем на FAB созданный листенер
        floatingActionButton.setVisibility(View.INVISIBLE);
        albumListFragment = new AlbumListFragment();//создаем объекты фрагментов
        postsListFragment = new PostsListFragment();

        selectedUser = String.valueOf(getIntent().getIntExtra("selectedUser", 1));//переводим до стринга
        nameSelectedUser = String.valueOf(getIntent().getStringExtra("selectedUserName"));//получаем имя выбранного пользователя
        userDescriptionText = (TextView) findViewById(R.id.textViewUserDescription);
        userDescriptionText.setText(nameSelectedUser);//добавляем информацию в TextView

        fTrans_user = getFragmentManager().beginTransaction();//создаем транзакцию для фрагмента
        fTrans_user.add(R.id.frgm_main_Cont, albumListFragment);//добавляем фрагмент со списком альбомов в транзакцию
        fTrans_user.commit();
    }

    @Override//реализация метода из интерфейса для обработки нажатия на элемент списка альбомов
    public void onItemSelectedAlbums(int itemIndex) {
            //передаем номер выбранного альбома в новую активити.
            Intent intent = new Intent(this, AllUserAlbumActivity.class);
            intent.putExtra("selectedAlbum", itemIndex);
            startActivity(intent);
    }
    @Override//прописываем события для двух состояний свитч. (здесь необходимо еще прятать FAB при отображении альбомов)
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {//если включен, отображаются посты пользователя и кнопка
            fTrans_user = getFragmentManager().beginTransaction();
            fTrans_user.replace(R.id.frgm_main_Cont, postsListFragment);
            fTrans_user.commit();
            floatingActionButton.setVisibility(View.VISIBLE);
        }else{//если выключен, отображаются альбомы и прячется FAB
            fTrans_user = getFragmentManager().beginTransaction();
            fTrans_user.replace(R.id.frgm_main_Cont, albumListFragment);
            fTrans_user.commit();
            floatingActionButton.setVisibility(View.INVISIBLE);
        }
    }
        //метод, где при финише диалога вызываем метод для отправки постов
    public void onFinishDialog(String inputTextTitle, String inputBody) {
        sendPost(inputTextTitle, inputBody);
    }
    // здесь метод для отправки поста.
    public void sendPost(String title, String body){
        apiPostPosts.savePost(title, body, 1).enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "All Right", Toast.LENGTH_SHORT).show();//в случае успеха показываем соответствующее сообщение
                }
            }
            @Override
            public void onFailure(Call<PostModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Very Bad", Toast.LENGTH_SHORT).show();// в случае неудачи отправляем соответствующее сообщение.
            }
        });
    }

    @Override
    public void onItemSelectedPosts(int itemIndex) {
        //передаем номер выбранного поста в новую активити.
        Intent intent = new Intent(this, AllUserAlbumActivity.class);
        intent.putExtra("selectedPosts", itemIndex);
        startActivity(intent);
    }
}
