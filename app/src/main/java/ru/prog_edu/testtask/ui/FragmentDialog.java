package ru.prog_edu.testtask.ui;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import ru.prog_edu.testtask.R;

public class FragmentDialog extends DialogFragment implements View.OnClickListener{

    private EditText titleText;
    private EditText bodyText;
    Button btn;
    //создаем интерфейс с методом для возможности расписать события в  активити при завершении диалога
    public interface DialogListener{
        void onFinishDialog(String inputTextTitle, String inputBody);
    }
    public FragmentDialog() {//создаем пустой конструктор. Должен быть прописан для DialodFragment
    }
        //Привязываем кастомный диалог
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container,false);
        titleText = (EditText) view.findViewById(R.id.etTitlePost);
        bodyText = (EditText) view.findViewById(R.id.etBodyPost);
        btn = (Button) view.findViewById(R.id.button);
        btn.setOnClickListener(this);
        return view;
    }
        //считываем внесенную информацию по клику и заносим ее в активити использующую фрагмент-диалог при помощи метода в используемом интерфейсе
    @Override
    public void onClick(View v) {
        this.dismiss();
        String title = titleText.getText().toString();
        String body = bodyText.getText().toString();
        DialogListener activity = (DialogListener)getActivity();
        activity.onFinishDialog(title, body);
    }
}
