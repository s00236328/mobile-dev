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

    private static final float BAND_HEIGHT = 40;
    private static final float BAND_Y1 = 100;
    private static final float BAND_Y_OFFSET = BAND_HEIGHT + 20; // Offset between bands

    // Constructor
    public ColorHelper(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        bandColors = new int[4]; // Default to 4 bands
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the bands
        for (int i = 0; i < bandColors.length; i++) {
            drawColorBand(canvas, bandColors[i], BAND_Y1 + (i * BAND_Y_OFFSET)); // Adjust BAND_Y1, BAND_Y2, etc. based on spacing
        }
    }

    // Helper method to draw color bands
    private void drawColorBand(Canvas canvas, int color, float y) {
        paint.setColor(color);
        canvas.drawRect(100, y, 300, y + BAND_HEIGHT, paint); // Bands of 40 pixels height
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
