package com.example.questionsapp;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class QuestionController {

    static String TAG = "QuestionController";

    static int scenarioCounter;
    static int questionCounter;
    static int lastQuestion;
    static int lastScenario;
    static boolean isNext; // if there's a next question

    final static String BUTTON_STATE_CONFIRM = "Confirm";
    final static String BUTTON_STATE_NEXT = "Next";
    final static String BUTTON_STATE_FINISH = "Finish";

    final static String IMAGE_DIRECTORY = "file:///android_asset/image/";

    TextView textViewScenarioScenariosCount;
    //TextView textViewScenarioSubject;
    TextView textViewScenarioTitle;
    TextView textViewScenarioDescription;
    ImageView imageViewScenarioImage;

    TextView textViewQuestionSubject;
    TextView textViewQuestionTitle;
    TextView textViewQuestionDescription;
    TextView textViewQuestionMultipleChoice;
    ImageView imageViewQuestionImage;
    EditText editTextQuestionUserAnswer;
    TextView textViewQuestionCorrectChoice;
    CheckBox checkBoxChoice1;
    CheckBox checkBoxChoice2;
    CheckBox checkBoxChoice3;
    CheckBox checkBoxChoice4;
    CheckBox checkBoxChoice5;
    CheckBox checkBoxChoice6;
    ImageView imageViewChoice1;
    ImageView imageViewChoice2;
    ImageView imageViewChoice3;
    ImageView imageViewChoice4;
    ImageView imageViewChoice5;
    ImageView imageViewChoice6;
    ImageView imageViewCorrectChoice;

    Button buttonConfirm;
    private ColorStateList textColorDefaultRb; //hold the default color value of the radio buttons


    Scenario currentScenario;
    Question currentQuestion;


    ArrayList<Scenario> scenarioList;
    ArrayList<Question> questionList;
    ArrayList<Choice> choiceList;

    QuestionList questionAdapter;
    ChoiceList choiceAdapter;

    String selectedSubject;

    DataManagerControllable db;


    Activity activity;


    public QuestionController(Activity activity, String selectedSubject) {
        this.activity = activity;
        this.selectedSubject = selectedSubject;
        instantiateView(activity);
    }

    /**
     * instantiate activity view items
     * notice: if u add new view adjust disableView()
     * @param activity Activity
     */
    public void instantiateView(Activity activity) {
        textViewScenarioScenariosCount = (TextView) activity.findViewById(R.id.textView_scenarios_count);
        textViewScenarioTitle = (TextView) activity.findViewById(R.id.textView_title);
        textViewScenarioDescription = (TextView) activity.findViewById(R.id.textView_description);
        imageViewScenarioImage = (ImageView) activity.findViewById(R.id.imageView_image);

        textViewQuestionSubject = (TextView) activity.findViewById(R.id.textView_q_subject);
        textViewQuestionTitle = (TextView) activity.findViewById(R.id.textView_q_title);
        textViewQuestionDescription = (TextView) activity.findViewById(R.id.textView_q_description);
        imageViewQuestionImage = (ImageView) activity.findViewById(R.id.imageView_q_image);
        editTextQuestionUserAnswer = (EditText) activity.findViewById(R.id.editText_q_user_answer);
        textViewQuestionCorrectChoice = (TextView) activity.findViewById(R.id.textView_correct_choice);
        textViewQuestionMultipleChoice = (TextView) activity.findViewById(R.id.textView_q_multiple_choice);
        checkBoxChoice1 = (CheckBox) activity.findViewById(R.id.checkBox_choice1);
        checkBoxChoice2 = (CheckBox) activity.findViewById(R.id.checkBox_choice2);
        checkBoxChoice3 = (CheckBox) activity.findViewById(R.id.checkBox_choice3);
        checkBoxChoice4 = (CheckBox) activity.findViewById(R.id.checkBox_choice4);
        checkBoxChoice5 = (CheckBox) activity.findViewById(R.id.checkBox_choice5);
        checkBoxChoice6 = (CheckBox) activity.findViewById(R.id.checkBox_choice6);
        imageViewChoice1 = (ImageView) activity.findViewById(R.id.imageView_c1_image);
        imageViewChoice2 = (ImageView) activity.findViewById(R.id.imageView_c2_image);
        imageViewChoice3 = (ImageView) activity.findViewById(R.id.imageView_c3_image);
        imageViewChoice4 = (ImageView) activity.findViewById(R.id.imageView_c4_image);
        imageViewChoice5 = (ImageView) activity.findViewById(R.id.imageView_c5_image);
        imageViewChoice6 = (ImageView) activity.findViewById(R.id.imageView_c6_image);
        imageViewCorrectChoice = (ImageView) activity.findViewById(R.id.textView_correct_choice_image);

        textColorDefaultRb = checkBoxChoice1.getTextColors();  //save default color of a checkbox
        buttonConfirm = (Button) activity.findViewById(R.id.button_confirm);

        //load json file of the selected subject
        db = new ListDataManager(activity, selectedSubject);


        //TODO: add search criteria
        scenarioList = (ArrayList<Scenario>) ((ListDataManager) db).getScenarios();
        Log.d(TAG, "scenarioList: " + scenarioList.size());

        scenarioCounter = 0;
        questionCounter = 0;
        lastScenario =scenarioList.size() -1;

                buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (buttonConfirm.getText().toString()){
                    case BUTTON_STATE_CONFIRM:
                        Log.d(TAG, "onClick: confirm");
                        showSolution(currentQuestion);
                        break;
                    case BUTTON_STATE_NEXT:
                        Log.d(TAG, "onClick: next");
                        showNextQuestion(currentScenario);
                        break;
                    case BUTTON_STATE_FINISH:
                        Log.d(TAG, "onClick: finish");
                        finish();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + buttonConfirm.getText().toString());
                }

            }
        });

        fillScenario();
    }

    /**
     * fill currentScenario and currentQuestion
     */
    private void fillScenario() {
        //disable all views at the beginning of every question
        disableViews();

        //identify current scenario
        currentScenario = scenarioList.get(scenarioCounter);
        //identify last question of current scenario
        lastQuestion =currentScenario.getQuestions().size() -1;

        //isNext = lastQuestion > 0 || lastScenario > 0;
        isNext = questionCounter < lastQuestion || scenarioCounter < lastScenario ;
        currentQuestion = currentScenario.getQuestions().get(questionCounter);

        Log.d(TAG, "getQuestions: " + currentScenario.getQuestions().size());
        Log.d(TAG, "getChoices: " + currentQuestion.getChoices().size());


        //if scenario is not empty fill scenario view items
        if (null != currentScenario.getTitle()){

            String scenarioHeader = "Q: "+ (questionCounter +1) + "/"+ (lastQuestion+1) + ", "+
                    "S: "+ (scenarioCounter +1) + "/"+ (lastScenario +1) + ", "+
                      currentScenario.getType() +
                    ", "+ currentScenario.getYear() +", " +currentScenario.getSubject() + ", P:"+ currentScenario.getPoints();

            textViewScenarioScenariosCount.setText(scenarioHeader);
            textViewScenarioScenariosCount.setVisibility(View.VISIBLE);

            textViewScenarioTitle.setText("S-Title: " + currentScenario.getTitle());
            textViewScenarioTitle.setVisibility(View.VISIBLE);

        }

        //scenario image
        if (currentScenario.getImage() != null && !currentScenario.getImage().equals("")){
            imageViewScenarioImage.setVisibility(View.VISIBLE);
            Log.i(TAG, "Image: "+IMAGE_DIRECTORY+currentScenario.getImage()+".JPG");
            Picasso.get().load(IMAGE_DIRECTORY+currentScenario.getImage()).into(imageViewScenarioImage);
        }

        //check question image
        if (currentQuestion.getImage() != null && !currentQuestion.getImage().equals("")){
            imageViewQuestionImage.setVisibility(View.VISIBLE);
            Log.i(TAG, "Image: "+activity.getAssets()+IMAGE_DIRECTORY+currentQuestion.getImage());
            Picasso.get().load(IMAGE_DIRECTORY+currentQuestion.getImage()).into(imageViewQuestionImage);
        }

        String questionHeader = "Q: "+currentQuestion.getType() +
                ", "+ currentQuestion.getYear() +", " +currentQuestion.getSubject() + ", P:"+ currentQuestion.getPoints();

        textViewQuestionSubject.setText(questionHeader);
        textViewQuestionSubject.setVisibility(View.VISIBLE);
        textViewQuestionTitle.setText("Q-Title: " + currentQuestion.getTitle());
        textViewQuestionTitle.setVisibility(View.VISIBLE);

        //activate and fill checkbox if question isMultipleChoice
        if (currentQuestion.isMultipleChoice()) {
            Log.d(TAG, "fillScenario: isMultiple");
            int counter = 1;
            //TODO: change this way
            for (Choice choice : currentQuestion.getChoices()) {
                Log.d(TAG, "fillScenario: counter" + counter + "titl:" + choice.getTitle());
                switch (counter) {
                    case 1:
                        Log.d(TAG, "switch: case" + 1);
                        checkCheckBox(checkBoxChoice1, imageViewChoice1, choice, counter);
                        counter++;
                        break;
                    case 2:
                        Log.d(TAG, "switch: case" + 2);
                        checkCheckBox(checkBoxChoice2, imageViewChoice2, choice, counter);
                        counter++;
                        break;
                    case 3:
                        Log.d(TAG, "switch: case" + 3);
                        checkCheckBox(checkBoxChoice3, imageViewChoice3, choice, counter);
                        counter++;
                        break;
                    case 4:
                        Log.d(TAG, "switch: case" + 4);
                        checkCheckBox(checkBoxChoice4, imageViewChoice4, choice, counter);
                        counter++;
                        break;
                    case 5:
                        Log.d(TAG, "switch: case" + 5);
                        checkCheckBox(checkBoxChoice5, imageViewChoice5, choice, counter);
                        counter++;
                        break;
                    case 6:
                        Log.d(TAG, "switch: case" + 6);
                        checkCheckBox(checkBoxChoice6, imageViewChoice6, choice, counter);
                        counter++;
                        break;
                }
            }
        }
        else{
            editTextQuestionUserAnswer.setVisibility(View.VISIBLE);
        }
        buttonConfirm.setText(BUTTON_STATE_CONFIRM);
    }

    /**
     * disable all views at every question
     */
    private void disableViews() {
        textViewScenarioScenariosCount.setVisibility(View.GONE);
        textViewScenarioTitle.setVisibility(View.GONE);
        textViewScenarioDescription.setVisibility(View.GONE);
        textViewQuestionSubject.setVisibility(View.GONE);
        textViewQuestionTitle.setVisibility(View.GONE);
        textViewQuestionDescription.setVisibility(View.GONE);
        editTextQuestionUserAnswer.setVisibility(View.GONE);
        textViewQuestionCorrectChoice.setVisibility(View.GONE);
        textViewQuestionMultipleChoice.setVisibility(View.GONE);
        checkBoxChoice1.setVisibility(View.GONE);
        checkBoxChoice2.setVisibility(View.GONE);
        checkBoxChoice3.setVisibility(View.GONE);
        checkBoxChoice4.setVisibility(View.GONE);
        checkBoxChoice5.setVisibility(View.GONE);
        checkBoxChoice6.setVisibility(View.GONE);
        imageViewScenarioImage.setVisibility(View.GONE);
        imageViewQuestionImage.setVisibility(View.GONE);
        imageViewChoice1.setVisibility(View.GONE);
        imageViewChoice2.setVisibility(View.GONE);
        imageViewChoice3.setVisibility(View.GONE);
        imageViewChoice4.setVisibility(View.GONE);
        imageViewChoice5.setVisibility(View.GONE);
        imageViewChoice6.setVisibility(View.GONE);
        imageViewCorrectChoice.setVisibility(View.GONE);
    }

    public void createQuestionGroup(String subject) {
        QuestionGroup questionGroup = new QuestionGroup();
    }

    /**
     * check how many choice does the current question have and initiate checkboxes accordingly used in fillScenario()
     * @param checkBox CheckBox the checkbox to be checked
     * @param imageView ImageView the image of the choice to be displayed
     * @param choice Choice the choice to be rendered in the checkbox
     * @param counter int is the choice number
     */
    public void checkCheckBox(CheckBox checkBox, ImageView imageView,Choice choice, int counter){
        checkBox.setVisibility(View.VISIBLE);
        checkBox.setText(choice.getTitle());
        //check choice image
        if (choice.getImage() != null && !choice.getImage().equals("")){
            checkBox.setVisibility(View.VISIBLE);
            Picasso.get().load(IMAGE_DIRECTORY+choice.getImage()).into(imageView);
            if (choice.getTitle() == null || choice.getTitle().equals("")){
                checkBox.setText("choice "+counter);
            }
        }
    }
    /**
     * check user selected choice and if the choice is correct
     * @param currentQuestion Question
     */
    private void showSolution(Question currentQuestion) {
        if (currentQuestion.isMultipleChoice()) {
            List<Choice> choices = currentQuestion.getChoices();
            int counter = 1;
            for (Choice choice : choices) {
                //TODO: change this way
                Log.d(TAG, "showSolution: counter" + counter + "titl:" + choice.getTitle());
                switch (counter) {
                    case 1:
                        Log.d(TAG, "switch: case" + 1);
                        validateCheckBoxChoice(checkBoxChoice1, choice);
                        counter++;
                        break;
                    case 2:
                        Log.d(TAG, "switch: case" + 2);
                        validateCheckBoxChoice(checkBoxChoice2, choice);
                        counter++;
                        break;
                    case 3:
                        Log.d(TAG, "switch: case" + 3);
                        validateCheckBoxChoice(checkBoxChoice3, choice);
                        counter++;
                        break;
                    case 4:
                        Log.d(TAG, "switch: case" + 4);
                        validateCheckBoxChoice(checkBoxChoice4, choice);
                        counter++;
                        break;
                    case 5:
                        Log.d(TAG, "switch: case" + 5);
                        validateCheckBoxChoice(checkBoxChoice5, choice);
                        counter++;
                        break;
                    case 6:
                        Log.d(TAG, "switch: case" + 6);
                        validateCheckBoxChoice(checkBoxChoice6, choice);
                        counter++;
                        break;
                }

            }
        }
        else {
            String userAnswer = String.valueOf(editTextQuestionUserAnswer.getText());
            textViewQuestionCorrectChoice.setVisibility(View.VISIBLE);
            if (null != userAnswer){
                Log.d(TAG, "showSolution: userAnswer: "+userAnswer);
                Log.d(TAG, "showSolution: correctAnswer: "+currentQuestion.getCorrectAnswer());

                textViewQuestionCorrectChoice.setText(currentQuestion.getCorrectAnswer());
                if (currentQuestion.getCorrectAnswer() != null && currentQuestion.getCorrectAnswer().contains(userAnswer)){
                    textViewQuestionCorrectChoice.setTextColor(Color.GREEN);
                } else {
                    textViewQuestionCorrectChoice.setTextColor(Color.RED);
                }
                //check correct choice image
                if (currentQuestion.getCorrectAnswerImage() != null && !currentQuestion.getCorrectAnswerImage().equals("")){
                    imageViewCorrectChoice.setVisibility(View.VISIBLE);
                    Log.i(TAG, "Image: "+activity.getAssets()+IMAGE_DIRECTORY+currentQuestion.getImage());
                    Picasso.get().load(IMAGE_DIRECTORY+currentQuestion.getImage()).into(imageViewCorrectChoice);
                }
            }
        }
        showNextOrFinishButtonText();
    }

    /**
     * to validate each user-answer of a checkBox
     */
    private void validateCheckBoxChoice(CheckBox checkBox, Choice choice) {
        int answerColor = Color.RED;
        String message = " \n{Should not be selected!}";
        //if selected and isCorrect
        if (checkBox.isChecked()) {
            if (choice.isCorrect()) {
               // checkBox.setTextColor(Color.GREEN);
                //return;
                answerColor=Color.GREEN;
            }
        } else {
            //if not selected and is not Correct
            if (!choice.isCorrect()) {
                //checkBox.setTextColor(Color.GREEN);
                //return;
                answerColor=Color.GREEN;
            }
        }
        if (choice.isCorrect()){
            message ="\n{Should be selected!}";
        }
        //checkBox.setTextColor(Color.RED);
        checkBox.setText(checkBox.getText()+message);
        checkBox.setTextColor(answerColor);
    }

    /**
     * change the ConfirmButton to nextButton
     */
    private void showNextOrFinishButtonText(){
      //  if (scenarioCounter < lastScenario){
        if (isNext){
            //buttonConfirm.setText(getResources().getString(R.string.quiz_page_next_text));
            buttonConfirm.setText(BUTTON_STATE_NEXT);
           // isNext = true;
        }else {
         //   if (questionCounter == lastQuestion){
                buttonConfirm.setText(BUTTON_STATE_FINISH);
              //  isNext = false;
         //   }
        }
    }

    /**
     * used in showNextQuestion()
     * clear checkBoxes selection and return its default color.
     */
    private void clearChoicesSelection(){
        checkBoxChoice1.setTextColor(textColorDefaultRb); //set checkBox color to its default
        checkBoxChoice1.setChecked(false); //clear the previous selection
        checkBoxChoice2.setTextColor(textColorDefaultRb); //set checkBox color to its default
        checkBoxChoice2.setChecked(false); //clear the previous selection
        checkBoxChoice3.setTextColor(textColorDefaultRb); //set checkBox color to its default
        checkBoxChoice3.setChecked(false); //clear the previous selection
        checkBoxChoice4.setTextColor(textColorDefaultRb); //set checkBox color to its default
        checkBoxChoice4.setChecked(false); //clear the previous selection
        checkBoxChoice5.setTextColor(textColorDefaultRb); //set checkBox color to its default
        checkBoxChoice5.setChecked(false); //clear the previous selection
        checkBoxChoice6.setTextColor(textColorDefaultRb); //set checkBox color to its default
        checkBoxChoice6.setChecked(false); //clear the previous selection

        textViewQuestionCorrectChoice.setTextColor(textColorDefaultRb);
        textViewQuestionCorrectChoice.setText("");

        editTextQuestionUserAnswer.setText("");
    }

    /**
     * check if there's a next question then update questionCounter and scenarioCounter
     * @param scenario Scenario currentScenario
     */
    private void showNextQuestion(Scenario scenario){
        Log.d(TAG, "showNextQuestion: scenario"+scenario.getId());
        clearChoicesSelection();
        if (questionCounter < lastQuestion)
        {
            Log.d(TAG, "showNextQuestion: size < questionCounter "+ scenario.getQuestions().size());
            questionCounter++;
        }else {
            Log.d(TAG, "showNextQuestion: size > questionCounter "+ scenario.getQuestions().size());
            questionCounter = 0;
            scenarioCounter++;
        }
        Log.d(TAG, "showNextQuestion: questionCounter: "+questionCounter +" scenarioCounter"+ scenarioCounter);
        fillScenario();
    }

    /**
     * finish activity
     */
    private void finish(){
        //TODO: finish for result
        Log.d(TAG, "finish: ");
        activity.finish();
    }

}
