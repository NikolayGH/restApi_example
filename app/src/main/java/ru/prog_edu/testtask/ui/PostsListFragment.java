package ru.prog_edu.testtask.ui;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.prog_edu.testtask.R;
import ru.prog_edu.testtask.adapter.PostAdapter;
import ru.prog_edu.testtask.model.PostModel;
import ru.prog_edu.testtask.retrofit.api.ApiPostsServise;

//Фрагмент для отображения списка постов. Аналогичен AlbumListFragment за исключением кастомизированного элемента списка.Поэтому подробные комментарии избыточны
public class PostsListFragment extends ListFragment {

    private ArrayList<PostModel> postsList;
    private PostAdapter postAdapter;
    String userNumber;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.posts_list_fragment,null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        OnSelectedItemListener_posts listener_posts = (OnSelectedItemListener_posts) getActivity();
        listener_posts.onItemSelectedPosts(position);
    }

    public interface OnSelectedItemListener_posts{
        void onItemSelectedPosts(int itemIndex);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userNumber = ((SelectedUserActivity)getActivity()).getSelectedUser();
        int intUserNumber = Integer.parseInt(userNumber)+1;//добавляем единицу для получения точного ID юзера
        userNumber = String.valueOf(intUserNumber);
        postsList = new ArrayList<>();
        ApiPostsServise apiPostsServise = ApiPostsServise.retrofit.create(ApiPostsServise.class);
        final Call<List<PostModel>> callPosts = apiPostsServise.getPostsJSON(userNumber);
        callPosts.enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                if(response.isSuccessful()){
                    postsList.addAll(response.body());
                    postAdapter = new PostAdapter(getActivity(), postsList);
                    setListAdapter(postAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                Toast toast = Toast.makeText(context, "FAIL", Toast.LENGTH_LONG);//в случае неудачи запроса выкидываем сообщение.
                toast.show();
            }
        });
    }
}






















