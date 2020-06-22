package com.projectrani.productapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    public static  final String EXTRA_SCORE = "extraScore";

    private TextView question;
    private TextView question_count;
    private TextView option_check;
    private TextView text_score;
    private RadioGroup radio_group;
    private RadioButton option1;
    private RadioButton option2;
    private RadioButton option3;
    private RadioButton option4;
    private Button btn_confirm_next;

    private ColorStateList textColorDefaultRb;
    private ColorStateList blueParagon;

    private List<Question> questionList;
    public int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;

    private int score;
    private boolean answered;

    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        question = findViewById(R.id.question);
        question_count = findViewById(R.id.question_count);
        option_check = findViewById(R.id.option_check);
        text_score = findViewById(R.id.score);
        radio_group = findViewById(R.id.radio_group);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        btn_confirm_next = findViewById(R.id.btn_confirm_next);

        textColorDefaultRb = option1.getTextColors();
        blueParagon = option_check.getTextColors();

        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionList = dbHelper.getAllQuestions();

        questionCountTotal = questionList.size();
        Collections.shuffle((questionList));

        showNextQuestion();

        btn_confirm_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (option1.isChecked() || option2.isChecked() || option3.isChecked() || option4.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                    option_check.setText("");
                }
            }
        });
    }

    private void showNextQuestion() {
        option1.setTextColor(textColorDefaultRb);
        option2.setTextColor(textColorDefaultRb);
        option3.setTextColor(textColorDefaultRb);
        option4.setTextColor(textColorDefaultRb);
        radio_group.clearCheck();

        if(questionCounter < 5) {
            currentQuestion = questionList.get(questionCounter);

            question.setText(currentQuestion.getQuestion());
            option1.setText(currentQuestion.getOption1());
            option2.setText(currentQuestion.getOption2());
            option3.setText(currentQuestion.getOption3());
            option4.setText(currentQuestion.getOption4());

            questionCounter++;
            question_count.setText("Question: " + questionCounter + "/ 5");
            answered = false;
            btn_confirm_next.setText("Confirm");

        } else {
            finishQuiz();
        }

    }

    private void checkAnswer() {
        answered = true;

        RadioButton rbSelected = findViewById(radio_group.getCheckedRadioButtonId());
        int answerNr = radio_group.indexOfChild(rbSelected) + 1;

        if (answerNr == currentQuestion.getAnswerNr()) {
            score = score + 20;
            text_score.setText("Score: " + score);

            switch (currentQuestion.getAnswerNr()) {
                case 1:
                    option1.setTextColor(blueParagon);
                    break;
                case 2:
                    option2.setTextColor(blueParagon);
                    break;
                case 3:
                    option3.setTextColor(blueParagon);
                    break;
                case 4:
                    option4.setTextColor(blueParagon);
                    break;
            }
            option_check.setText("Your Answer is Correct");
            option_check.setTextColor(blueParagon);
        } else {
            switch (answerNr) {
                case 1:
                    option1.setTextColor(Color.RED);
                    break;
                case 2:
                    option2.setTextColor(Color.RED);
                    break;
                case 3:
                    option3.setTextColor(Color.RED);
                    break;
                case 4:
                    option4.setTextColor(Color.RED);
                    break;
            }
            option_check.setText("Your Answer is Wrong");
            option_check.setTextColor(Color.RED);
        }

        if (questionCounter < 5) {
            btn_confirm_next.setText("Next");
        } else {
            btn_confirm_next.setText("Finish");
        }
    }

    private void finishQuiz(){
        Intent resultIntent = new Intent(QuizActivity.this, StartQuizActivity.class);
        resultIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, resultIntent);
        finish();
        //startActivity(resultIntent);
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            Intent intent = new Intent(QuizActivity.this, StartQuizActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Press back again to exit quiz", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}