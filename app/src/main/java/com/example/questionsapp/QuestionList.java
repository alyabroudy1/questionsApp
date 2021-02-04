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

public class QuestionList extends ArrayAdapter<Question> {

    private Activity context;
    private List<Question> questionList;

    public QuestionList(Activity context, List<Question> questionList){
        super(context, R.layout.question_list, questionList);
        this.context = context;
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.question_list, null, true);

        TextView textViewPoints = (TextView) listViewItem.findViewById(R.id.textView_points);
        TextView textViewSubject = (TextView) listViewItem.findViewById(R.id.textView_subject);
        TextView textViewTitle = (TextView) listViewItem.findViewById(R.id.textView_title);
        TextView textViewDescription = (TextView) listViewItem.findViewById(R.id.textView_description);
        TextView textViewMultipleChoice = (TextView) listViewItem.findViewById(R.id.textView_multiple_choice);
        ImageView imageView = (ImageView) listViewItem.findViewById(R.id.imageView_image);


        Question question = questionList.get(position);

    /*    textViewName.setText(Question.getName());
        textViewUrl.setText(Question.getServer());
       // textViewGenre.setText(artist.getGenre());

     */
        textViewPoints.setText(question.getPoints());
        textViewSubject.setText(question.getSubject());
        textViewTitle.setText(question.getTitle());
        textViewDescription.setText(question.getDescription());
        textViewMultipleChoice.setText(question.isMultipleChoice() ? "Multiple Choice" : " Not");

        if (question.getImage() != null && !question.getImage().equals("")){
            Picasso.get().load(question.getImage()).into(imageView);
            Log.i("image url", question.getImage());
        }

        return listViewItem;
    }

    public void setQuestionList(List<Question> list){
        this.questionList = list;
    }
    public List<Question> getQuestionList(){
        return this.questionList;
    }
}
