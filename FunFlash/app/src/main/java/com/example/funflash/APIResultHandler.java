package com.example.funflash;

import android.app.Application;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import database.Question;

public class APIResultHandler {

    ArrayList<Question> questions = new ArrayList<>();
    ReposQuestions repo;

    public APIResultHandler(Application application) {
        repo = new ReposQuestions(application);
    }

    public void parseData(String data)
    {
        JSONObject wholeJSON = null;
        try {
            wholeJSON = new JSONObject(data);
        } catch (JSONException e) {
        }
        try {
            JSONArray tempArray = wholeJSON.getJSONArray("results");

            for(int i = 0; i < tempArray.length(); i++)
            {
                Question temp = new Question();
                JSONObject jo = null;
                try {
                    jo = (JSONObject) tempArray.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    temp.question = (String) jo.getString("question");
                    temp.correctAnswer = jo.getString("correct_answer");
                    JSONArray incorrectAnswers = (JSONArray) jo.get("incorrect_answers");
                    temp.answer1 = (String) incorrectAnswers.get(0);
                    temp.answer2 = (String) incorrectAnswers.get(1);
                    temp.answer3 = (String) incorrectAnswers.get(2);
                    temp.answer4 = temp.correctAnswer;
                    questions.add(temp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (JSONException e) {
        }catch(NullPointerException e)
        {

        }

    }

    public void writeToDB() {
        repo.insertAll(questions.toArray(new Question[0]));
    }
}
