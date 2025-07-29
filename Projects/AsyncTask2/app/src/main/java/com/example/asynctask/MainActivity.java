package com.example.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnProcess;
    private ProgressBar progressBar;
    private TextView txtPercentage, txtTimeRemaining;

    private DoingAsyncTask doingAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnProcess = findViewById(R.id.button_process);
        progressBar = findViewById(R.id.progressbar);
        txtPercentage = findViewById(R.id.txtpercentage);
        txtTimeRemaining = findViewById(R.id.txttime);

        progressBar.setMax(100);
        progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.yellow_soft), PorterDuff.Mode.SRC_IN);

        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnProcess.setEnabled(false);

                doingAsyncTask = new DoingAsyncTask();
                doingAsyncTask.execute();
            }
        });
    }

    private class DoingAsyncTask extends AsyncTask<Void, Integer, Void> {

        private int progressStatus = 0;
        private static final int TOTAL_PROGRESS = 100;
        private static final int STEP = 5;
        private static final long SLEEP_TIME_MS = 200;
        private final long totalDuration = (TOTAL_PROGRESS / STEP) * SLEEP_TIME_MS;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, getString(R.string.toast_start), Toast.LENGTH_SHORT).show();

            progressStatus = 0;
            txtPercentage.setText(getString(R.string.processing_percentage, progressStatus));
            txtTimeRemaining.setText(getString(R.string.time_remaining, totalDuration / 1000));

            progressBar.setProgress(0);
            progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.yellow_soft), PorterDuff.Mode.SRC_IN);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            while (progressStatus < TOTAL_PROGRESS) {

                if (isCancelled()) {
                    break;
                }

                progressStatus += STEP;
                publishProgress(progressStatus);

                SystemClock.sleep(SLEEP_TIME_MS);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            int progress = values[0];
            progressBar.setProgress(progress);

            txtPercentage.setText(getString(R.string.processing_percentage, progress));

            long elapsedTime = (progress / STEP) * SLEEP_TIME_MS;
            long remainingTimeMillis = totalDuration - elapsedTime;
            long remainingSeconds = remainingTimeMillis / 1000;

            if (remainingSeconds < 0) remainingSeconds = 0;

            txtTimeRemaining.setText(getString(R.string.time_remaining, remainingSeconds));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(MainActivity.this, getString(R.string.toast_complete), Toast.LENGTH_SHORT).show();

            txtPercentage.setText(getString(R.string.processing_complete));
            txtTimeRemaining.setText("");

            btnProcess.setEnabled(true);

            // بعد الانتهاء يظل لون الأصفر الناعم
            progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.yellow_soft), PorterDuff.Mode.SRC_IN);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            Toast.makeText(MainActivity.this, getString(R.string.toast_cancelled), Toast.LENGTH_SHORT).show();

            txtPercentage.setText(getString(R.string.processing_cancelled));
            txtTimeRemaining.setText("");

            btnProcess.setEnabled(true);

            progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.yellow_soft), PorterDuff.Mode.SRC_IN);
        }
    }
}
