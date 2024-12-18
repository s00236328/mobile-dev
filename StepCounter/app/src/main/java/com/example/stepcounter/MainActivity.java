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

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    // UI elements
    private TextView stepTxt, timeTxt;
    private Button resetBtn, nextBtn, startBtn, stopBtn;
    // Step and time tracking variables
    private int stepCount = 0;
    private long startTime;
    private long elapsedMillis;
    private long dateofrun;
    private boolean isTracking = false;

    // Sensor and timer management
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private Handler timeHandler;
    private int seconds;
    private long lastUpdateTime = 0;
    private static final float STEP_THRESHOLD = 40.0f;
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
        // Initialize SensorManager and accelerometer sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometerSensor == null) {
            stepTxt.setText("This device does not have an accelerometer sensor");
        }
        // Set button click listeners
        startBtn.setOnClickListener(v -> startTracking());
        stopBtn.setOnClickListener(v -> stopTracking());
        resetBtn.setOnClickListener(v -> resetTracking());
        nextBtn.setOnClickListener(v -> openNextPage());
    }
    // Starts step tracking and timer
    private void startTracking() {
        if (!isTracking) {
            startTime = System.currentTimeMillis();
            dateofrun = startTime;
            stepCount = 0;
            isTracking = true;}
            timeHandler = new Handler();
            timeHandler.post(timeRunnable);
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_UI);
            Toast.makeText(this, "Tracking started!", Toast.LENGTH_SHORT).show();

    }
    // Stops step tracking and timer
    private void stopTracking() {
        if (isTracking) {
            if (timeHandler != null) {
                timeHandler.removeCallbacks(timeRunnable);
            }
            sensorManager.unregisterListener(this);

            Toast.makeText(this, "Tracking stopped!", Toast.LENGTH_SHORT).show();
        }
    }
    // Resets step count, timer, and other variables
    private void resetTracking() {
        isTracking = false;
        stepCount = 0;
        startTime = System.currentTimeMillis();
        timeTxt.setText(String.format(Locale.getDefault(), "Time: %02d:%02d", 0, 0));
        stepTxt.setText("Total Steps:\n 0");
        Toast.makeText(this, "Tracking reset!", Toast.LENGTH_SHORT).show();
    }
    // Opens the next page and sends the tracking data
    private void openNextPage() {
        Intent intent = new Intent(MainActivity.this, secondPage.class);
        intent.putExtra("steps", stepCount);
        intent.putExtra("time", seconds);
        intent.putExtra("dateOfRun", dateofrun);
        startActivity(intent);
    }
    // Called when sensor data changes
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            // Filter out updates too close in time
            if ((currentTime - lastUpdateTime) > 100) { // Minimum delay between events
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                // Calculate the magnitude of the accelerometer vector
                float magnitude = (float) Math.sqrt(x * x + y * y + z * z);
                // Detect a step when the magnitude exceeds the threshold
                if (magnitude > STEP_THRESHOLD) {
                    stepCount++;
                    stepTxt.setText("Total Steps:\n " + stepCount);
                }
                lastUpdateTime = currentTime;
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Optional: Handle sensor accuracy changes if needed
    }
}
