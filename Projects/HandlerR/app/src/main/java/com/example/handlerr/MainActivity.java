package com.example.handlerr;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
    ProgressBar progressBar;
    TextView txtCaption, txtResult;
    Button btnStart, btnStop;
    EditText edtInput;

    Handler myHandler = new Handler();
    Thread myThread;
    boolean isRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        txtCaption = findViewById(R.id.txtCaption);
        txtResult = findViewById(R.id.txtResult);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        edtInput = findViewById(R.id.edtInput);

        txtCaption.setText(getString(R.string.starting_msg));

        btnStart.setOnClickListener(v -> startBackgroundTask());
        btnStop.setOnClickListener(v -> stopBackgroundTask());
    }

    private void startBackgroundTask() {
        isRunning = true;
        progressBar.setVisibility(View.VISIBLE);
        myThread = new Thread(() -> {
            for (int progressStep = 0; progressStep <= 100 && isRunning; progressStep++) {
                try {
                    Thread.sleep(100); // Simulate background work
                    int finalProgressStep = progressStep; // Needed for lambda
                    myHandler.post(() -> {
                        progressBar.setProgress(finalProgressStep);
                        txtResult.setText(getString(R.string.total_seconds, finalProgressStep));
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                }
            }
            if (isRunning) {
                myHandler.post(() -> {
                    txtCaption.setText(R.string.background_work_over_msg);
                    progressBar.setVisibility(View.INVISIBLE);
                });
            }
        });
        myThread.start();
    }

    private void stopBackgroundTask() {
        isRunning = false;
        if (myThread != null) {
            myThread.interrupt();
        }
    }
}