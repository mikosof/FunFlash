package com.example.funflash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import mode.Question;


public class TestActivity extends AppCompatActivity {
    private ArrayList<mode.Question> questions = new ArrayList<>();
    private int currentQuestion = 0;
    private int score = 0;
    private int wrongs =0;
    private TestViewModel testViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        testViewModel = new ViewModelProvider(this).get(TestViewModel.class);
        testViewModel.getRandomQuestions(10).observe(this, new Observer<List<database.Question>>() {
            @Override
            public void onChanged(List<database.Question> questionDBEntities) {
                for (database.Question question : questionDBEntities) {
                    questions.add(new Question(question));
                }
                addTextToView();
            }
        });

    }

    public void answerClicked(View view) {
        Button buttonClicked = (Button) view;
        if(currentQuestion+1 == questions.size()) {
            Toast.makeText(getApplicationContext(), "You got all "+ score + " correct, with " + wrongs +" wrong answers", Toast.LENGTH_LONG).show();
            endTest();
        }

        else if(buttonClicked.getText().toString().equals(questions.get(currentQuestion).getCorrectAnswer())) {
            currentQuestion++;
            score++;
            addTextToView();
        } else {
            Toast.makeText(getApplicationContext(), "Wrong answer! Try again", Toast.LENGTH_SHORT).show();
            wrongs++;
        }
    }

    private void addTextToView() {
        TextView questionTextView = findViewById(R.id.questionId);
        questionTextView.setText(questions.get(currentQuestion).getQuestion());

        Button answer1 = findViewById(R.id.answer1Id);
        answer1.setText(questions.get(currentQuestion).getAnswers().get(0));

        Button answer2 = findViewById(R.id.answer2Id);
        answer2.setText(questions.get(currentQuestion).getAnswers().get(1));

        Button answer3 = findViewById(R.id.answer3Id);
        answer3.setText(questions.get(currentQuestion).getAnswers().get(2));

        Button answer4 = findViewById(R.id.answer4Id);
        answer4.setText(questions.get(currentQuestion).getAnswers().get(3));
    }

    private void endTest()
    {
        Intent intent = new Intent(TestActivity.this, ResultsSave.class);
        intent.putExtra("score", score);
        startActivity(intent);
    }
}