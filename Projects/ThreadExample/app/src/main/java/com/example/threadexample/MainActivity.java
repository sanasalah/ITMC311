package com.example.threadexample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView title;
    Button btnWithoutThread, btnWithThread;
    ProgressBar progressBar;

    Handler handler = new Handler(Looper.getMainLooper());

    private static final String TAG = "MainActivity"; // لتسجيل الأخطاء

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.titleText);
        btnWithoutThread = findViewById(R.id.btnWithoutThread);
        btnWithThread = findViewById(R.id.btnWithThread);
        progressBar = findViewById(R.id.progressBar);

        // ✔️ 1. استبدال View.OnClickListener بـ Lambda
        btnWithoutThread.setOnClickListener(view -> {
            long endTime = System.currentTimeMillis() + 20000;

            // ✔️ 2. ضع تعليقًا لشرح while لتجنب التحذير
            while (System.currentTimeMillis() < endTime) {
                // intentionally empty loop to simulate blocking main thread
            }

            title.setText(R.string.press_me);
            Toast.makeText(MainActivity.this, R.string.warning_frozen, Toast.LENGTH_LONG).show();
        });

        // ✔️ 3. استبدال View.OnClickListener بـ Lambda
        btnWithThread.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            title.setText(R.string.processing);

            // ✔️ 4. استخدام Lambda لـ Runnable
            new Thread(() -> {
                try {
                    Thread.sleep(10000);

                    // ✔️ 5. Lambda داخل post
                    handler.post(() -> {
                        progressBar.setVisibility(View.GONE);
                        title.setText(R.string.done);
                        Toast.makeText(MainActivity.this, R.string.background_task_done, Toast.LENGTH_SHORT).show();
                    });

                } catch (InterruptedException e) {
                    // ✔️ 6. استبدال printStackTrace بـ تسجيل الخطأ
                    Log.e(TAG, "Thread was interrupted", e);
                }
            }).start();
        });
    }
}