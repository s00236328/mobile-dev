package com.example.pleasework;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class ColorHelper extends View {

    private Paint paint;
    private List<Integer> bandColors; // List to store the colors of bands
    private static final float BAND_HEIGHT = 100;
    private static final float BAND_SPACING = 20;

    // Constructor
    public ColorHelper(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        bandColors = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float y = 100; // Starting y-coordinate for the bands
        for (int color : bandColors) {
            drawColorBand(canvas, color, y);
            y += BAND_HEIGHT + BAND_SPACING; // Update y-coordinate for next band
        }
    }

    // Helper method to draw color bands
    private void drawColorBand(Canvas canvas, int color, float y) {
        paint.setColor(color);
        canvas.drawRect(100, y, 100 + BAND_HEIGHT, y + BAND_HEIGHT, paint);
    }

    // Method to add a color band
    public void addBandColor(int color) {
        bandColors.add(color);
        invalidate(); // Request to redraw the view
    }

    // Method to clear all bands
    public void clearBands() {
        bandColors.clear();
        invalidate(); // Request to redraw the view
    }
}
