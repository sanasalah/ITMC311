package com.example.brodcastreceiver;

import androidx.appcompat.app.AppCompatActivity;
import android.os.BatteryManager;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent i) {
            int level = i.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int status = i.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);

            ProgressBar pb = findViewById(R.id.progressbar);
            TextView tv = findViewById(R.id.textfield);
            TextView statusView = findViewById(R.id.statusTextView);

            pb.setProgress(level);
            tv.setText(getString(R.string.battery_level_text, level));

            if (level <= 20) {
                pb.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            } else if (level <= 50) {
                pb.getProgressDrawable().setColorFilter(Color.YELLOW, android.graphics.PorterDuff.Mode.SRC_IN);
            } else {
                pb.getProgressDrawable().setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
            }

            if (status == BatteryManager.BATTERY_PLUGGED_USB) {
                statusView.setText(getString(R.string.charging_usb));
            } else if (status == BatteryManager.BATTERY_PLUGGED_AC) {
                statusView.setText(getString(R.string.charging_ac));
            } else if (status == BatteryManager.BATTERY_PLUGGED_WIRELESS) {
                statusView.setText(getString(R.string.charging_wireless));
            } else {
                statusView.setText(getString(R.string.not_charging));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mBatInfoReceiver);
    }
}
