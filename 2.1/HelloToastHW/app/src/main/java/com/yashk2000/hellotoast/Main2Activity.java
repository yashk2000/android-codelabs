package com.yashk2000.hellotoast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private TextView scoreBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        int score = getIntent().getIntExtra("Score", 0);
        scoreBoard = findViewById(R.id.textView2);
        scoreBoard.setText(Integer.toString(score));
    }
}
