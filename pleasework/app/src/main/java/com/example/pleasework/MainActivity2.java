package com.example.pleasework;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity2 extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float[] accelerometerValues = new float[3];
    private ArrayList<Integer> sequence;
    private int currentIndex = 0;
    private int score = 0;
    private boolean isProcessing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize score and sequence
        score = getIntent().getIntExtra("score", 0);
        sequence = getIntent().getIntegerArrayListExtra("sequence");
        if (sequence == null) {
            sequence = new ArrayList<>();
        }



        // Initialize sensor manager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (isProcessing) {
                return;
            }

            System.arraycopy(event.values, 0, accelerometerValues, 0, accelerometerValues.length);

            String direction = detectTilt();
            if (direction == null) {
                return; // Skip if no tilt
            }

            Log.d("GameActivity", "Detected direction: " + direction);
            Log.d("GameActivity", "Expected direction: " + sequence.get(currentIndex));

            if (direction.equals(sequence.get(currentIndex))) {
                currentIndex++;
                if (currentIndex == sequence.size()) {
                    // Player completed the sequence
                    score += sequence.size();
                    Intent intent = new Intent(MainActivity2.this, MainActivity5.class);

                    intent.putStringArrayListExtra("sequence", (ArrayList<Integer>) sequence);
                  //  intent.putExtra("score", score);
                    startActivity(intent);
                    finish();
                }
            } else {
                // Player failed
                Intent intent = new Intent(MainActivity2.this, MainActivity4.class);
                intent.putExtra("score", score);
                startActivity(intent);
                finish();
            }

            // Set the processing buffer
            isProcessing = true;
            new android.os.Handler().postDelayed(() -> isProcessing = false, 500);
        }
    }

    private String detectTilt() {
        if (accelerometerValues[0] > 5) return "Blue";  // Tilt down
        if (accelerometerValues[0] < -5) return "Red";  // Tilt up
        if (accelerometerValues[1] > 5) return "Yellow";// Tilt right
        if (accelerometerValues[1] < -5) return "Green";// Tilt left
        return null;
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }
}
