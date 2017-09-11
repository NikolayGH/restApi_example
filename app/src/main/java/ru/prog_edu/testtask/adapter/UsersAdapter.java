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
import ru.prog_edu.testtask.model.UserModel;

public class UsersAdapter extends ArrayAdapter<UserModel>{

    List<UserModel> userList; //создаем переменную будущего массива считываемых объектов
    Context context; //создаем переменную для контекста
    private LayoutInflater userInflater; // переменная для "надувателя"
            // конструктор адаптера, которому на вход должен приходить контекст и Список объектов
    public UsersAdapter(Context context, List<UserModel> users){
        super(context, 0, users);
        this.context = context;
        this.userInflater = LayoutInflater.from(context);
        userList = users;
    }
    //метод, возвращающий объект конкретной модели из листа по указанной на входе позиции
    @Override
    public  UserModel getItem(int position){
        return userList.get(position);
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = userInflater.inflate(R.layout.user_item, parent, false);
            vh = ViewHolder.create((ConstraintLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        UserModel item = getItem(position);//получаем конкретный элемент по позиции
        vh.textViewName.setText(item.getName());//ссетим конкретные данные в нужные вью
        vh.textViewEmail.setText(item.getEmail());
        //с адресом посложнее, так как данных больше
        vh.textViewAddress.setText(item.getAddress().getSuite()+ " " + item.getAddress().getStreet() + "st. " + item.getAddress().getCity());
        return vh.rootView;
    }
        //Вложенный класс
    private static class ViewHolder {
        public final ConstraintLayout rootView;
        public final TextView textViewName;
        public final TextView textViewEmail;
        public final TextView textViewAddress;
        //Конструктор
        private ViewHolder(ConstraintLayout rootView, TextView textViewName, TextView textViewEmail, TextView textViewAddress) {
            this.rootView = rootView;
            this.textViewName = textViewName;
            this.textViewEmail = textViewEmail;
            this.textViewAddress = textViewAddress;
        }
        //метод для создания элемента списка вьюХолдером
        public static ViewHolder create(ConstraintLayout rootView) {
            TextView textViewName = (TextView) rootView.findViewById(R.id.textUserId);
            TextView textViewEmail = (TextView) rootView.findViewById(R.id.albumId);
            TextView textViewAddress = (TextView) rootView.findViewById(R.id.textAlbumName);
            return new ViewHolder(rootView, textViewName, textViewEmail, textViewAddress);
        }
    }
}
