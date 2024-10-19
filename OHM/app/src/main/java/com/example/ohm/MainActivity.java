package com.example.ohm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public static class view extends View {
        private Paint resistorPaint;
        private Paint bandPaint;

        public view(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            // Paint for resistor body
            resistorPaint = new Paint();
            resistorPaint.setColor(Color.LTGRAY); // Light blue for the resistor body
            resistorPaint.setStyle(Paint.Style.FILL);

            // Paint for the bands
            bandPaint = new Paint();
            bandPaint.setStyle(Paint.Style.FILL);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // Draw resistor body (rounded rectangle)
            float left = 100, top = 200, right = 500, bottom = 300;
            float radius = 50;
            canvas.drawRoundRect(left, top, right, bottom, radius, radius, resistorPaint);

            // Draw color bands
            drawBand(canvas, 150, Color.RED); // First band (red)
            drawBand(canvas, 200, Color.BLACK); // Second band (black)
            drawBand(canvas, 250, Color.RED); // Third band (red)
            drawBand(canvas, 300, Color.YELLOW); // Fourth band (yellow)

            // Draw leads (wires)
            bandPaint.setColor(Color.GRAY);
            bandPaint.setStrokeWidth(10);
            canvas.drawLine(left - 100, (top + bottom) / 2, left, (top + bottom) / 2, bandPaint); // Left wire
            canvas.drawLine(right, (top + bottom) / 2, right + 100, (top + bottom) / 2, bandPaint); // Right wire
        }

        private void drawBand(Canvas canvas, float positionX, int color) {
            bandPaint.setColor(color);
            canvas.drawRect(positionX, 200, positionX + 20, 300, bandPaint); // Adjust band height/width
        }
    }
}