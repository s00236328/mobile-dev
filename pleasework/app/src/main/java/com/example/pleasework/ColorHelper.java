package com.example.pleasework;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ColorHelper extends View {

    private Paint paint;
    private int[] bandColors; // Array to hold all band colors

    private static final float BAND_WIDTH = 40;
    private static final float BAND_X1 = 100;
    private static final float BAND_X_OFFSET = BAND_WIDTH + 20; // Offset between bands
    private static final float BAND_Y = 100; // Vertical position of bands

    // Constructor
    public ColorHelper(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        bandColors = new int[10000]; // Default to 4 bands
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the bands horizontally
        for (int i = 0; i < bandColors.length; i++) {
            drawColorBand(canvas, bandColors[i], BAND_X1 + (i * BAND_X_OFFSET)); // Adjust BAND_X1 based on spacing
        }
    }

    // Helper method to draw color bands
    private void drawColorBand(Canvas canvas, int color, float x) {
        paint.setColor(color);
        canvas.drawRect(x, BAND_Y, x + BAND_WIDTH, BAND_Y + BAND_WIDTH, paint); // Bands of 40 pixels width
    }

    // Methods to set the band colors
    public void setBandColor(int index, int color) {
        if (index >= 0 && index < bandColors.length) {
            bandColors[index] = color;
            invalidate(); // Request to redraw the view
        }
    }

    // Methods to set the band colors
    public void setBandColor1(int color) {
        bandColors[0] = color;
        invalidate(); // Request to redraw the view
    }

    public void setBandColor2(int color) {
        bandColors[1] = color;
        invalidate(); // Request to redraw the view
    }

    public void setBandColor3(int color) {
        bandColors[2] = color;
        invalidate(); // Request to redraw the view
    }

    public void setBandColor4(int color) {
        bandColors[3] = color;
        invalidate(); // Request to redraw the view
    }
}
