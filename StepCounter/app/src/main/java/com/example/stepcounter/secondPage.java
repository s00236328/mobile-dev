package com.example.stepcounter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class secondPage extends AppCompatActivity {

    // UI Elements
    private Button backBtn;
    private TextView caloriesTxt, timeTxt, dateTxt, distanceTxt;

    // Data variables
    private double calories;
    private double totalTimeInSeconds;
    private double distanceInMeters;
    private long dateOfRun;
    private int steps;
    private int seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second_page);

        // Apply insets for immersive UI
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI elements
        backBtn = findViewById(R.id.btnBack);
        caloriesTxt = findViewById(R.id.txtCalories);
        timeTxt = findViewById(R.id.txtTime1);
        dateTxt = findViewById(R.id.txtDate);
        distanceTxt = findViewById(R.id.txtMeter);

        // Retrieve data from intent
        Intent intent = getIntent();
        dateOfRun = intent.getLongExtra("dateOfRun", 0);
        steps = intent.getIntExtra("steps", 0);
        seconds = intent.getIntExtra("time", 0);

        // Set up button click listener to go back to MainActivity
        backBtn.setOnClickListener(v -> {
            Intent backIntent = new Intent(secondPage.this, MainActivity.class);
            startActivity(backIntent);
        });

        // Perform calculations and update UI
        calculateMetrics();
        updateUI();
    }

    // Calculates distance and calories based on steps, updates time
    private void calculateMetrics() {
        distanceInMeters = steps * 0.8; // Assuming 0.8 meters per step
        calories = steps * 0.04;       // Assuming 0.04 calories per step
        totalTimeInSeconds = seconds;
    }

    // Updates the UI with calculated metrics and formatted date
    private void updateUI() {
        Date date = new Date(dateOfRun);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String formattedDate = dateFormat.format(date);

        caloriesTxt.setText(String.format(Locale.getDefault(), "You burnt %.2f calories", calories));
        distanceTxt.setText(String.format(Locale.getDefault(), "You travelled %.2f meters", distanceInMeters));
        timeTxt.setText(String.format(Locale.getDefault(), "Your run was %.0f seconds", totalTimeInSeconds));
        dateTxt.setText(String.format("Start Time: %s", formattedDate));
    }
}
