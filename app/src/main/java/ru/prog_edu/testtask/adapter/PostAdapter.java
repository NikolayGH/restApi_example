package ru.prog_edu.testtask.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import ru.prog_edu.testtask.R;
import ru.prog_edu.testtask.model.PostModel;
//Этот класс практически полностью повторяет класс UserAdapter (комментарии можно посмотреть там).
public class PostAdapter extends ArrayAdapter<PostModel>{

    List<PostModel> postsList;
    Context context;
    private LayoutInflater postsInflater;

    public PostAdapter(Context context, List<PostModel> posts) {
        super(context, 0, posts);
        this.context = context;
        this.postsInflater = LayoutInflater.from(context);
        postsList = posts;
    }

    @Override
    public PostModel getItem(int position){
        return postsList.get(position);
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        final ViewHolderPosts vhp;
        if(convertView == null){
            View view = postsInflater.inflate(R.layout.post_item, parent, false);
            vhp = ViewHolderPosts.create((ConstraintLayout) view);
            view.setTag(vhp);
        } else {
            vhp = (ViewHolderPosts) convertView.getTag();
        }

        PostModel item = getItem(position);
        vhp.tvNumberPost.setText(item.getId().toString());
        vhp.tvTitlePost.setText(item.getTitle());
        vhp.tvBodyPost.setText(item.getBody());
        return vhp.rootView;
    }

    private static class ViewHolderPosts{
        public final ConstraintLayout rootView;
        public final TextView tvNumberPost;
        public final TextView tvTitlePost;
        public final TextView tvBodyPost;

        public ViewHolderPosts(ConstraintLayout rootView, TextView tvNumberPost, TextView tvTitlePost, TextView tvBodyPost) {
            this.rootView = rootView;
            this.tvNumberPost = tvNumberPost;
            this.tvTitlePost = tvTitlePost;
            this.tvBodyPost = tvBodyPost;
        }
        public static ViewHolderPosts create(ConstraintLayout rootView){
            TextView tvNumberPost = (TextView) rootView.findViewById(R.id.numberPost);
            TextView tvTitlePost = (TextView) rootView.findViewById(R.id.titlePost);
            TextView tvBodyPost = (TextView) rootView.findViewById(R.id.post_body);
            return new ViewHolderPosts(rootView, tvNumberPost, tvTitlePost, tvBodyPost);
        }
    }
}
