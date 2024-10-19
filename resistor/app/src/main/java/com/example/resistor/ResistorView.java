package com.example.resistor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ResistorView extends View {
    private Paint paint;

    // Constructor
    public ResistorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the body of the resistor
        paint.setColor(Color.rgb(194, 178, 128)); // Light brown
        canvas.drawRect(100, 100, 600, 250, paint);

        // Draw the bands
        drawColorBand(canvas, Color.RED, 120);   // First band
        drawColorBand(canvas, Color.RED, 180);   // Second band
        drawColorBand(canvas, Color.BLACK, 240); // Third band
        drawColorBand(canvas, Color.YELLOW, 460); // Fourth band

        // Optional: Draw the resistor leads
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(10);
        canvas.drawLine(50, 175, 100, 175, paint); // Left lead
        canvas.drawLine(600, 175, 650, 175, paint); // Right lead
    }

    // Helper method to draw color bands
    private void drawColorBand(Canvas canvas, int color, float x) {
        paint.setColor(color);
        canvas.drawRect(x, 100, x + 40, 250, paint); // Bands of 40 pixels width
    }
}
