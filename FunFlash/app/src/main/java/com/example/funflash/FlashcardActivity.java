package com.example.funflash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import database.Question;

public class FlashcardActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TestViewModel testViewModel;
    ReposQuestions repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testViewModel = new TestViewModel(this.getApplication());
        repository = new ReposQuestions(this.getApplication());
        setContentView(R.layout.activity_flashcard);

        recyclerView = findViewById(R.id.recyclerView);



        List<Question> questions = repository.getAllQuestions();
        String[] question = new String[questions.size()];
        String[] answer = new String[questions.size()];
        for(int i = 0; i< questions.size(); i++) {
            question[i] = questions.get(i).question;
            answer[i] = questions.get(i).correctAnswer;
        }

        AdapterQuestions adapterQuestions = new AdapterQuestions(this, question, answer);
        recyclerView.setAdapter(adapterQuestions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}