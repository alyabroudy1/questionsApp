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

import java.util.ArrayList;
import java.util.List;

public class QuestionController {

    static String TAG = "QuestionController";

    static int scenarioCounter;
    static int questionCounter;
    static int lastQuestion;
    static int lastScenario;
    static boolean isNext; // if have next question or questions finished

    final static String BUTTON_STATE_CONFIRM = "Confirm";
    final static String BUTTON_STATE_NEXT = "Next";
    final static String BUTTON_STATE_FINISH = "Finish";

    TextView textViewScenarioScenariosCount;
    TextView textViewScenarioQuestionsCount;
    TextView textViewScenarioPoints;
    TextView textViewScenarioType;
    TextView textViewScenarioYear;
    TextView textViewScenarioSubject;
    TextView textViewScenarioTitle;
    TextView textViewScenarioDescription;
    ImageView imageViewScenarioImage;


    TextView textViewQuestionPoints;
    TextView textViewQuestionSubject;
    TextView textViewQuestionType;
    TextView textViewQuestionYear;
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
    Button buttonConfirm;
    private ColorStateList textColorDefaultRb; //hold the default color value of the radio buttons


    Scenario currentScenario;
    Question currentQuestion;


    ArrayList<Scenario> scenarioList;
    ArrayList<Question> questionList;
    ArrayList<Choice> choiceList;

    QuestionList questionAdapter;
    ChoiceList choiceAdapter;

    DataManagerControllable db;


    Activity activity;


    public QuestionController(Activity activity) {
        this.activity = activity;
        instantiateView(activity);
    }

    /**
     * instantiate activity view items
     * @param activity Activity
     */
    public void instantiateView(Activity activity) {
        textViewScenarioScenariosCount = (TextView) activity.findViewById(R.id.textView_scenarios_count);
        textViewScenarioQuestionsCount = (TextView) activity.findViewById(R.id.textView_questions_count);
        textViewScenarioPoints = (TextView) activity.findViewById(R.id.textView_points);
        textViewScenarioType = (TextView) activity.findViewById(R.id.textView_type);
        textViewScenarioYear = (TextView) activity.findViewById(R.id.textView_year);
        textViewScenarioSubject = (TextView) activity.findViewById(R.id.textView_subject);
        textViewScenarioTitle = (TextView) activity.findViewById(R.id.textView_title);
        textViewScenarioDescription = (TextView) activity.findViewById(R.id.textView_description);
        imageViewScenarioImage = (ImageView) activity.findViewById(R.id.imageView_image);
        imageViewScenarioImage.setVisibility(View.GONE);

        textViewQuestionPoints = (TextView) activity.findViewById(R.id.textView_q_points);
        textViewQuestionType = (TextView) activity.findViewById(R.id.textView_q_type);
        textViewQuestionType.setVisibility(View.GONE);
        textViewQuestionYear = (TextView) activity.findViewById(R.id.textView_q_year);
        textViewQuestionYear.setVisibility(View.GONE);
        textViewQuestionSubject = (TextView) activity.findViewById(R.id.textView_q_subject);
        textViewQuestionTitle = (TextView) activity.findViewById(R.id.textView_q_title);
        textViewQuestionDescription = (TextView) activity.findViewById(R.id.textView_q_description);
        imageViewQuestionImage = (ImageView) activity.findViewById(R.id.imageView_q_image);
        imageViewQuestionImage.setVisibility(View.GONE);
        editTextQuestionUserAnswer = (EditText) activity.findViewById(R.id.editText_q_user_answer);
        editTextQuestionUserAnswer.setVisibility(View.GONE);
        textViewQuestionCorrectChoice = (TextView) activity.findViewById(R.id.textView_correct_choice);
        textViewQuestionCorrectChoice.setVisibility(View.GONE);
        textViewQuestionMultipleChoice = (TextView) activity.findViewById(R.id.textView_q_multiple_choice);
        checkBoxChoice1 = (CheckBox) activity.findViewById(R.id.checkBox_choice1);
        checkBoxChoice2 = (CheckBox) activity.findViewById(R.id.checkBox_choice2);
        checkBoxChoice3 = (CheckBox) activity.findViewById(R.id.checkBox_choice3);
        checkBoxChoice4 = (CheckBox) activity.findViewById(R.id.checkBox_choice4);
        checkBoxChoice5 = (CheckBox) activity.findViewById(R.id.checkBox_choice5);
        checkBoxChoice6 = (CheckBox) activity.findViewById(R.id.checkBox_choice6);
        textColorDefaultRb = checkBoxChoice1.getTextColors();  //save default color of a checkbox
        buttonConfirm = (Button) activity.findViewById(R.id.button_confirm);

        db = new ListDataManager(activity);


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

        currentScenario = scenarioList.get(scenarioCounter);

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
            textViewScenarioTitle.setText("S-Title: " + currentScenario.getTitle());
                    /*   textViewScenarioPoints.setText("S-Points: " + currentScenario.getPoints());
            textViewScenarioSubject.setText("S-Subject: " + currentScenario.getSubject());
            textViewScenarioTitle.setText("S-Title: " + currentScenario.getTitle());
            textViewScenarioDescription.setText("S-Description: " + currentScenario.getDescription());
            textViewScenarioType.setText("Type: "+currentScenario.getType());
            textViewScenarioYear.setText("Year: "+currentScenario.getYear());

          */
        }else {
            //disable all scenario view if its null
            textViewScenarioTitle.setVisibility(View.GONE);
            textViewQuestionYear.setVisibility(View.VISIBLE);
            textViewQuestionType.setVisibility(View.VISIBLE);
        }
        //disable unnecessary views
        textViewScenarioPoints.setVisibility(View.GONE);
        textViewScenarioSubject.setVisibility(View.GONE);
        textViewScenarioDescription.setVisibility(View.GONE);
        textViewScenarioType.setVisibility(View.GONE);
        textViewScenarioYear.setVisibility(View.GONE);
        textViewScenarioQuestionsCount.setVisibility(View.GONE);

        //update counters
       // textViewScenarioScenariosCount.setText("S-Counts: " + (scenarioCounter +1) + "/"+ (lastScenario +1) );
       // textViewScenarioQuestionsCount.setText("Q-Counts: " + (questionCounter +1) + "/"+ (lastQuestion+1) );

        //check scenario image
        if (null != currentScenario.getImage()){
            imageViewScenarioImage.setVisibility(View.VISIBLE);
            //TODO: view image
        }

        //check question image
        if (null != currentQuestion.getImage()){
            imageViewQuestionImage.setVisibility(View.VISIBLE);
            //TODO: view image
        }


        //fill question
        /*textViewQuestionType.setText("Type: "+currentQuestion.getType());
        textViewQuestionYear.setText("Year: "+currentQuestion.getYear());
        textViewQuestionPoints.setText("Q-Points: " + currentQuestion.getPoints());
        textViewQuestionSubject.setText("Q-Subject: " + currentQuestion.getSubject());
        textViewQuestionTitle.setText("Q-Title: " + currentQuestion.getTitle());
        textViewQuestionDescription.setText("Q-Description: " + currentQuestion.getDescription());

         */

        textViewQuestionType.setVisibility(View.GONE);
        textViewQuestionYear.setVisibility(View.GONE);
        textViewQuestionPoints.setVisibility(View.GONE);
        textViewQuestionDescription.setVisibility(View.GONE);
        textViewQuestionMultipleChoice.setVisibility(View.GONE);


        String questionHeader = "Q: "+currentQuestion.getType() +
                ", "+ currentQuestion.getYear() +", " +currentQuestion.getSubject() + ", P:"+ currentQuestion.getPoints();

        textViewQuestionSubject.setText(questionHeader);
        textViewQuestionTitle.setText("Q-Title: " + currentQuestion.getTitle());


        //disable checkbox
        checkBoxChoice1.setVisibility(View.GONE);
        checkBoxChoice2.setVisibility(View.GONE);
        checkBoxChoice3.setVisibility(View.GONE);
        checkBoxChoice4.setVisibility(View.GONE);
        checkBoxChoice5.setVisibility(View.GONE);
        checkBoxChoice6.setVisibility(View.GONE);

        textViewQuestionCorrectChoice.setVisibility(View.GONE);
        editTextQuestionUserAnswer.setVisibility(View.GONE);

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
                        checkBoxChoice1.setVisibility(View.VISIBLE);
                        checkBoxChoice1.setText(choice.getTitle());
                        counter++;
                        break;
                    case 2:
                        Log.d(TAG, "switch: case" + 2);
                        checkBoxChoice2.setVisibility(View.VISIBLE);
                        checkBoxChoice2.setText(choice.getTitle());
                        counter++;
                        break;
                    case 3:
                        Log.d(TAG, "switch: case" + 3);
                        checkBoxChoice3.setVisibility(View.VISIBLE);
                        checkBoxChoice3.setText(choice.getTitle());
                        counter++;
                        break;
                    case 4:
                        Log.d(TAG, "switch: case" + 4);
                        checkBoxChoice4.setVisibility(View.VISIBLE);
                        checkBoxChoice4.setText(choice.getTitle());
                        counter++;
                        break;
                    case 5:
                        Log.d(TAG, "switch: case" + 5);
                        checkBoxChoice5.setVisibility(View.VISIBLE);
                        checkBoxChoice5.setText(choice.getTitle());
                        counter++;
                        break;
                    case 6:
                        Log.d(TAG, "switch: case" + 6);
                        checkBoxChoice6.setVisibility(View.VISIBLE);
                        checkBoxChoice6.setText(choice.getTitle());
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

    public void createQuestionGroup(String subject) {
        QuestionGroup questionGroup = new QuestionGroup();
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
            }
        }
        showNextOrFinishButtonText();
    }

    /**
     * to validate each user answer of a checkBox
     */
    private void validateCheckBoxChoice(CheckBox checkBox, Choice choice) {
        //if selected and isCorrect
        if (checkBox.isChecked()) {
            if (choice.isCorrect()) {
                checkBox.setTextColor(Color.GREEN);
                return;
            }
        } else {
            //if not selected and is not Correct
            if (!choice.isCorrect()) {
                checkBox.setTextColor(Color.GREEN);
                return;
            }
        }
        checkBox.setTextColor(Color.RED);
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
