package com.example.questionsapp;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ChoiceList extends ArrayAdapter<Choice> {

    private Activity context;
    private List<Choice> choiceList;

    public ChoiceList(Activity context, List<Choice> choiceList){
        super(context, R.layout.choice_list, choiceList);
        this.context = context;
        this.choiceList = choiceList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.choice_list, null, true);

        CheckBox checkBoxChoice = (CheckBox) listViewItem.findViewById(R.id.checkBox_choice1);
        ImageView imageView = (ImageView) listViewItem.findViewById(R.id.imageView_image);


        Choice choice = choiceList.get(position);

    /*    textViewName.setText(choice.getName());
        textViewUrl.setText(choice.getServer());
       // textViewGenre.setText(artist.getGenre());

     */
        checkBoxChoice.setText(choice.getTitle());

        if (choice.getImage() != null && !choice.getImage().equals("")){
            Picasso.get().load(choice.getImage()).into(imageView);
            Log.i("image url", choice.getImage());
        }

        return listViewItem;
    }

    public void setChoiceList(List<Choice> list){
        this.choiceList = list;
    }
    public List<Choice> getChoiceList(){
        return this.choiceList;
    }
}
