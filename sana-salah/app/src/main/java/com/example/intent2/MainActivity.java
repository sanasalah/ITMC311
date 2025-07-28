package com.example.intent2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button explicitIntentButton = findViewById(R.id.explicit_intent_button);
        explicitIntentButton.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "جاري الانتقال إلى الشاشة الثانية...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });

        Button implicitIntentButton = findViewById(R.id.implicit_intent_button);
        implicitIntentButton.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "جاري فتح المتصفح...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.google.com"));
            startActivity(intent);
        });
    }
}
