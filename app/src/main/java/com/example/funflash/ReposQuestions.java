package com.example.funflash;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

import database.AppDatabase;
import database.Question;
import database.QuestionDao;

public class ReposQuestions {
    private QuestionDao questionDao;
    private LiveData<List<Question>> questions;

    public ReposQuestions(Application application) {

        AppDatabase db = Room.databaseBuilder(application.getApplicationContext(),
                AppDatabase.class, "test").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        questionDao = db.questionDao();

    }
    public void insert(Question note) {
        questionDao.insertAll(note);
    }

    public void insertAll(Question... questions){
        questionDao.insertAll(questions);
    }

    public List<Question> getAllQuestions() {
        return questionDao.getAll();
    }

    public List<Question> getAllQuestionsLiveData() {
        return questionDao.getAll();
    }

    public LiveData<List<Question>> getRandomQuestions(int amount) {
        return questionDao.getRandomQuestions(amount);
    }

    public int getQuestionCount() {
        return  questionDao.getQuestionCount();
    }
}
