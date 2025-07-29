package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // تهيئة العناصر
        Button startButton = findViewById(R.id.startButton);
        LottieAnimationView lottieBooma = findViewById(R.id.lottie_booma);
        LottieAnimationView lottieTopLeft = findViewById(R.id.lottie_top_left);
        LottieAnimationView lottieTopRight = findViewById(R.id.lottie_top_right);
        LottieAnimationView lottieBottomLeft = findViewById(R.id.lottie_bottom_left);
        LottieAnimationView lottieBottomRight = findViewById(R.id.lottie_bottom_right);

        // تحميل تأثيرات الأنيميشن
        Animation shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake);
        Animation bounceAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        Animation zoomAnim = AnimationUtils.loadAnimation(this, R.anim.zoom);

        // تطبيق bounce على booma
        lottieBooma.startAnimation(bounceAnim);

        // ضبط سرعة الحركات الأخرى
        lottieBooma.setSpeed(1.0f);
        lottieTopLeft.setSpeed(1.5f);
        lottieTopRight.setSpeed(1.5f);
        lottieBottomLeft.setSpeed(1.5f);
        lottieBottomRight.setSpeed(1.5f);

        // زر البدء عند لمسه يعمل shake + zoom
        startButton.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.startAnimation(zoomAnim);
                v.startAnimation(shakeAnim); // يمكن حذف هذا إن كنت تريده فقط zoom
            }
            return false;
        });

        // الانتقال إلى صفحة الأقسام
        startButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CategoryActivity.class));
        });
    }
}
