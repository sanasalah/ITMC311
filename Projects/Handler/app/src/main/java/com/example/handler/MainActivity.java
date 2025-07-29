package com.example.handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ProgressBar bar1, bar2;
    TextView msgWorking, msgReturned;
    Button stopButton;

    boolean isRunning = false;
    static final int MAX_SEC = 60;
    String strTest = "Global value seen by all threads";
    int intTest = 0;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bar1 = findViewById(R.id.progress);
        bar2 = findViewById(R.id.progress2);
        msgWorking = findViewById(R.id.TextView01);
        msgReturned = findViewById(R.id.TextView02);
        stopButton = findViewById(R.id.stopButton);

        bar1.setMax(MAX_SEC);
        bar1.setProgress(0);

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                String returnedValue = (String) msg.obj;
                msgReturned.setText(getString(R.string.returned_value_template, returnedValue));
                bar1.incrementProgressBy(2);

                if (bar1.getProgress() >= MAX_SEC) {
                    msgWorking.setText(getString(R.string.done_text));
                    msgReturned.setText(getString(R.string.done_background));
                    bar1.setVisibility(View.INVISIBLE);
                    bar2.setVisibility(View.INVISIBLE);
                    stopButton.setEnabled(false);
                    isRunning = false;
                } else {
                    msgWorking.setText(getString(R.string.working_step_template, bar1.getProgress()));
                }
            }
        };

        stopButton.setOnClickListener(v -> {
            isRunning = false;
            msgWorking.setText(getString(R.string.stopped_text));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        isRunning = true;
        bar1.setProgress(0);
        bar1.setVisibility(View.VISIBLE);
        bar2.setVisibility(View.VISIBLE);
        stopButton.setEnabled(true);

        Thread background = new Thread(() -> {
            try {
                for (int i = 0; i < MAX_SEC && isRunning; i++) {
                    // Delay between updates
                    Thread.sleep(1000);

                    Random rnd = new Random();
                    String data = getString(R.string.thread_value_template,
                            rnd.nextInt(100), strTest, intTest++);
                    Message msg = handler.obtainMessage(1, data);
                    if (isRunning) {
                        handler.sendMessage(msg);
                    }
                }
            } catch (InterruptedException e) {
                Log.e("ThreadError", "Thread was interrupted", e);
            }
        });

        background.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
    }
}
