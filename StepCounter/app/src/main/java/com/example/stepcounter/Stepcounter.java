package com.example.stepcounter;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;




public class Stepcounter extends AppCompatActivity implements SensorEventListener {

    // UI elements
    private TextView stepTxt, timeTxt;
    private Button resetBtn, nextBtn, startBtn, stopBtn;

    // Step and time tracking variables
    private int totalSteps = 0;
    private long startTime;
    private long elapsedMillis;
    private long dateofrun;
    private boolean shouldReset = true;

    // Sensor and timer management
    private SensorManager sensorManager;
    private Sensor stepSensor;
    private Handler timeHandler;
    private int seconds;
    private int initialStepCount = 0;
    // Runnable to update the time display every second
    private final Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {
            elapsedMillis = System.currentTimeMillis() - startTime;
            seconds = (int) (elapsedMillis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            timeTxt.setText(String.format(Locale.getDefault(), "Time: %02d:%02d", minutes, seconds));
            timeHandler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Apply insets for immersive UI
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI elements
        resetBtn = findViewById(R.id.btnReset);
        nextBtn = findViewById(R.id.btnNext);
        startBtn = findViewById(R.id.btnStart);
        stopBtn = findViewById(R.id.btnStop);
        stepTxt = findViewById(R.id.txtSteps);
        timeTxt = findViewById(R.id.txtTime);

        // Initialize SensorManager and step counter sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepSensor == null) {
            stepTxt.setText("This device does not have a step counter sensor");
        }

        // Set button click listeners
        startBtn.setOnClickListener(v -> startTracking());
        stopBtn.setOnClickListener(v -> stopTracking());
        resetBtn.setOnClickListener(v -> resetTracking());
        nextBtn.setOnClickListener(v -> openNextPage());
    }

    // Starts step tracking and timer
    private void startTracking() {
        if (shouldReset) {
            startTime = System.currentTimeMillis();
            initialStepCount = totalSteps;
            shouldReset = false;
        }
        dateofrun=System.currentTimeMillis();
        timeHandler = new Handler();
        timeHandler.post(timeRunnable);
        sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        Toast.makeText(this, "Tracking started!", Toast.LENGTH_SHORT).show();
    }

    // Stops step tracking and timer
    private void stopTracking() {
        if (timeHandler != null) {
            timeHandler.removeCallbacks(timeRunnable);
        }
        sensorManager.unregisterListener(this);
        Toast.makeText(this, "Tracking stopped!", Toast.LENGTH_SHORT).show();
    }

    // Resets step count, timer, and other variables
    private void resetTracking() {
        shouldReset = true;
        totalSteps = 0;
        startTime = System.currentTimeMillis();
        timeTxt.setText(String.format(Locale.getDefault(), "Time: %02d:%02d", 0, 0));
        stepTxt.setText("Total Steps:\n 0");
    }

    // Opens the next page and sends the tracking data
    private void openNextPage() {
        Intent intent = new Intent(Stepcounter.this, secondPage.class);
        intent.putExtra("steps", totalSteps);
        intent.putExtra("time", seconds);
        intent.putExtra("dateOfRun", dateofrun);
        startActivity(intent);
    }

    // Called when sensor data changes
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            int currentSteps = (int) event.values[0];
            totalSteps = currentSteps - initialStepCount;
            stepTxt.setText("Total Steps:\n " + totalSteps);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Optional: Handle sensor accuracy changes if needed
    }
}
