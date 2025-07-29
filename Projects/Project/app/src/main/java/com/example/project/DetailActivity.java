package com.example.project;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private TextView titleText, descriptionText;
    private ImageView imageView;
    private Button btnListen, btnNext, btnPrevious;
    private ImageButton btnBack;
    private String category;
    private int currentIndex = 0;
    private String[] titles, descriptions;
    private int[] images, sounds;
    private MediaPlayer mediaPlayer;
    private Animation scaleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initializeViews();
        setupAnimations();
        loadCategoryData();
        setupButtonListeners();
        updateUI();
    }

    private void initializeViews() {
        titleText = findViewById(R.id.titleText);
        descriptionText = findViewById(R.id.descriptionText);
        imageView = findViewById(R.id.imageView);
        btnListen = findViewById(R.id.btnListen);
        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setupAnimations() {
        scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);
    }

    private void loadCategoryData() {
        category = getIntent().getStringExtra("category");

        switch (category) {
            case "nature":
                titles = getResources().getStringArray(R.array.nature_titles);
                descriptions = getResources().getStringArray(R.array.nature_descriptions);
                images = new int[]{R.drawable.sun, R.drawable.moon, R.drawable.cloud};
                sounds = new int[]{R.raw.sun_sound, R.raw.moon_sound, R.raw.cloud_sound};
                break;

            case "animals":
                titles = getResources().getStringArray(R.array.animals_titles);
                descriptions = getResources().getStringArray(R.array.animals_descriptions);
                images = new int[]{R.drawable.cat, R.drawable.brid, R.drawable.limon};
                sounds = new int[]{R.raw.one_sound, R.raw.two_sound, R.raw.three_sound};
                break;

            case "colors":
                titles = getResources().getStringArray(R.array.colors_titles);
                descriptions = getResources().getStringArray(R.array.colors_descriptions);
                images = new int[]{R.drawable.red, R.drawable.blue, R.drawable.yellow};
                sounds = new int[]{R.raw.red_sound, R.raw.blue_sound, R.raw.yellow_sound};
                break;
        }
    }

    private void setupButtonListeners() {
        btnBack.setOnClickListener(v -> {
            v.startAnimation(scaleAnimation);
            finish();
        });

        btnListen.setOnClickListener(v -> {
            v.startAnimation(scaleAnimation);
            playCurrentSound();
        });

        btnPrevious.setOnClickListener(v -> navigateToPrevious());

        btnNext.setOnClickListener(v -> navigateToNext());
    }

    private void navigateToPrevious() {
        if (currentIndex > 0) {
            currentIndex--;
            updateUI();
        }
    }

    private void navigateToNext() {
        if (currentIndex < titles.length - 1) {
            currentIndex++;
        } else {
            currentIndex = 0; // العودة للبداية عند النهاية
            Toast.makeText(this, R.string.end_message, Toast.LENGTH_SHORT).show();
        }
        updateUI();
    }

    private void updateUI() {
        titleText.setText(titles[currentIndex]);
        descriptionText.setText(descriptions[currentIndex]);
        imageView.setImageResource(images[currentIndex]);

        // إظهار/إخفاء زر السابق
        btnPrevious.setVisibility(currentIndex > 0 ? View.VISIBLE : View.GONE);

        // تغيير نص زر التالي إذا كنا في العنصر الأخير
        btnNext.setText(currentIndex == titles.length - 1 ?
                getString(R.string.start) :
                getString(R.string.next));

        stopCurrentSound();
    }

    private void playCurrentSound() {
        stopCurrentSound();
        mediaPlayer = MediaPlayer.create(this, sounds[currentIndex]);
        mediaPlayer.start();
        imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce));
    }

    private void stopCurrentSound() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopCurrentSound();
    }
}