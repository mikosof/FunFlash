package com.example.funflash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ResultsSave extends AppCompatActivity {
    private int wrongs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_results_activity);
        setScoreTextView();
    }

    private void setScoreTextView()
    {
        TextView scoreTextView = (TextView) findViewById(R.id.resultText);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            wrongs = extras.getInt("wrongs");
        }

        scoreTextView.setText("You got " + wrongs + " mistakes. Enter your name below to save your results");
    }


    public void saveToFirebase(View view)
    {
        EditText nameTextView = findViewById(R.id.resultEntryView);
        String name = nameTextView.getText().toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("wrongs");

        Mistakes mistakesObj = new Mistakes(name, wrongs);
        String id = myRef.push().getKey();
        myRef.child(id).setValue(mistakesObj);
        Toast.makeText(this, "Results Added", Toast.LENGTH_LONG).show();

        Intent newIntent = new Intent(this, MainActivity.class);
        startActivity(newIntent);
    }

}