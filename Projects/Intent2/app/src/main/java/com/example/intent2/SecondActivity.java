package com.example.intent2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Toast.makeText(this, "مرحبًا بك في الشاشة الثانية!", Toast.LENGTH_LONG).show();

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());
    }
}
