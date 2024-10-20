package com.example.ohm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ResistorView extends View {
    private Paint paint;
    public int bandColor1 = Color.TRANSPARENT;   // Default colors
    public int bandColor2 = Color.TRANSPARENT;
    public int bandColor3 = Color.TRANSPARENT;
    public int bandColor4 = Color.TRANSPARENT;
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
        drawColorBand(canvas, bandColor1, 120);   // First band
        drawColorBand(canvas, bandColor2, 180);   // Second band
        drawColorBand(canvas, bandColor3, 240); // Third band
        drawColorBand(canvas, bandColor4, 460); // Fourth band

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
    // Methods to set the band colors
    public void setBandColor1(int color) {
        bandColor1 = color;
        invalidate(); // Request to redraw the view
    }

    public void setBandColor2(int color) {
        bandColor2 = color;
        invalidate(); // Request to redraw the view
    }

    public void setBandColor3(int color) {
        bandColor3 = color;
        invalidate(); // Request to redraw the view
    }

    public void setBandColor4(int color) {
        bandColor4 = color;
        invalidate(); // Request to redraw the view
    }

}
