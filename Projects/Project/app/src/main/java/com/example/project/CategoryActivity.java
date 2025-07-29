package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CategoryActivity extends AppCompatActivity {

    Button btnLetters, btnNumbers, btnColors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // ربط الأزرار
        btnLetters = findViewById(R.id.btnNature);
        btnNumbers = findViewById(R.id.btnAnimals);
        btnColors = findViewById(R.id.btnColors);

        // تحميل الأنيميشن
        Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
        Animation pulseOnceAnimation = AnimationUtils.loadAnimation(this, R.anim.pulse_once);

        // تطبيق نبضة واحدة عند بداية الشاشة
        btnLetters.startAnimation(pulseOnceAnimation);
        btnNumbers.startAnimation(pulseOnceAnimation);
        btnColors.startAnimation(pulseOnceAnimation);

        // عند النقر على الأزرار
        btnLetters.setOnClickListener(v -> {
            v.startAnimation(zoomAnimation);
            openDetailActivity("nature");
        });

        btnNumbers.setOnClickListener(v -> {
            v.startAnimation(zoomAnimation);
            openDetailActivity("animals");
        });

        btnColors.setOnClickListener(v -> {
            v.startAnimation(zoomAnimation);
            openDetailActivity("colors");
        });
    }

    private void openDetailActivity(String category) {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("category", category);
            startActivity(intent);
        }, 300); // تأخير الانتقال لإكمال الأنيميشن
    }
}


