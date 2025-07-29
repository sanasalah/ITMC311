package com.example.game   ;

import android.os.Bundle;

import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button buttonLeft, buttonRight;
    TextView scoreText;
    int score = 0;
    Random rand = new Random();

    void generateNumbers() {
        int left = rand.nextInt(100);
        int right = rand.nextInt(100);

        while (left == right) {
            right = rand.nextInt(100);
        }

        buttonLeft.setText(String.valueOf(left));
        buttonRight.setText(String.valueOf(right));
    }

    void updateScore(boolean correct) {
        if (correct) {
            score++;
        } else {
            score = Math.max(0, score - 1);
        }

        String scoreString = getString(R.string.score_text, score);
        scoreText.setText(scoreString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLeft = findViewById(R.id.buttonLeft);
        buttonRight = findViewById(R.id.buttonRight);
        scoreText = findViewById(R.id.scoreText);

        generateNumbers();

        buttonLeft.setOnClickListener(v -> {
            int left = Integer.parseInt(buttonLeft.getText().toString());
            int right = Integer.parseInt(buttonRight.getText().toString());

            updateScore(left > right);
            generateNumbers();
        });

        buttonRight.setOnClickListener(v -> {
            int left = Integer.parseInt(buttonLeft.getText().toString());
            int right = Integer.parseInt(buttonRight.getText().toString());

            updateScore(right > left);
            generateNumbers();
        });
    }
}