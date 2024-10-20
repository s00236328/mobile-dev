package com.example.ohm;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class MainActivity extends AppCompatActivity {

    public double C1;
    public double C2;
    public double C3;
    public double Total;
    public double Lesst;
    public double Moret;
    public double Tolerance;
    public double Tolerancetoper;
    public TextView tot;
    public Button[] buttons;
    private ResistorView resistorView;
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
        resistorView = findViewById(R.id.resistorView); // Ensure this ID is correct

        GetButtonsAndValues();
         tot = findViewById(R.id.txtTotal);
        Button calcButton = findViewById(R.id.calc);
        Button resetBtn = findViewById(R.id.Reset);
        resetBtn.setOnClickListener(v->{
            Reset();
                });
        calcButton.setOnClickListener(v -> {
            // Call the method to update the total
            Calculate();
        });
    }
public void Calculate(){
       // txtTotal calc reset
    Math();
    tot.setText("Total = " + Total + " Ω With Tolerance of " + Tolerancetoper + "%" +
            "\n " + Lesst + " < x < " + Moret + " Ω");


    }
public void Math()
{
    Tolerancetoper =Tolerance*100;
    Total= ((C1*10)+C2)*C3;
    Moret=Total + Total*Tolerance;
    Lesst=Total- Total*Tolerance;
}
public void Reset(){
      C1=0;
      C2=0;
      C3=0;
      Tolerance=0;
    tot.setText("Input new values");

    resistorView.setBandColor1(0);resistorView.setBandColor2(0);resistorView.setBandColor3(0);
    resistorView.setBandColor4(0);
}
    public void GetButtonsAndValues() {
        int[] firstBandBtnIds = {
                R.id.B1,  // Black
                R.id.BR1, // Brown
                R.id.R1,  // Red
                R.id.O1,  // Orange
                R.id.Y1,  // Yellow
                R.id.G1,  // Green
                R.id.BL1, // Blue
                R.id.V1,  // Violet
                R.id.GR1, // Gray
                R.id.W1   // White
        };

        int[] secondBandBtnIds = {
                R.id.B2,  // Black
                R.id.BR2, // Brown
                R.id.R2,  // Red
                R.id.O2,  // Orange
                R.id.Y2,  // Yellow
                R.id.G2,  // Green
                R.id.BL2, // Blue
                R.id.V2,  // Violet
                R.id.GR2, // Gray
                R.id.W2   // White
        };

        double[] bandValues = {
                0,  // Black
                1,  // Brown
                2,  // Red
                3,  // Orange
                4,  // Yellow
                5,  // Green
                6,  // Blue
                7,  // Violet
                8,  // Gray
                9   // White
        };

        int[] multiplierBtnIds = {
                R.id.B3,  // Black
                R.id.BR3, // Brown
                R.id.R3,  // Red
                R.id.O3,  // Orange
                R.id.Y3,  // Yellow
                R.id.G3,  // Green
                R.id.BL3, // Blue
                R.id.V3,  // Violet
                R.id.GR3, // Gray
                R.id.W3,  // White
                R.id.GO3, // Gold
                R.id.S3   // Silver
        };
        double[] multiplierValues = {
                1,   // Black
                10,  // Brown
                100, // Red
                1000,// Orange
                10000, // Yellow
                100000, // Green
                1000000, // Blue
                10000000, // Violet
                100000000, // Gray
                1000000000, // White
                0.1, //Gold
                0.01 //Silver
        };
        int[] toleranceBtnId = {
                R.id.BR4, // Brown
                R.id.R4,  // Red
                R.id.G4,  // Green
                R.id.BL4, // Blue
                R.id.V4,  // Violet
                R.id.GR4,  // Gray
                R.id.GO4,
                R.id.S4,
                R.id.N4
        };
        double ToleranceValues[]={
                0.01,
                0.02,
                0.005,
                0.0025,
                0.001,
                0.0005,
                0.05,
                0.1,
                0.2
        };

        // Set OnClickListeners for first band buttons
        for (int i = 0; i < firstBandBtnIds.length; i++) {
            Button button = findViewById(firstBandBtnIds[i]);
            final double c1Value = bandValues[i]; // Use the corresponding value
            final int color = getColorForBand(i);
            button.setOnClickListener(v -> {
                C1 = c1Value; // Set C1 based on button clicked
                resistorView.setBandColor1(color); // Set color in ResistorView

                //Toast.makeText(this, "C1 is now: " + C1, Toast.LENGTH_SHORT).show();
            });
        }
        for (int i = 0; i < secondBandBtnIds.length; i++) {
            Button button = findViewById(secondBandBtnIds[i]);
            final double c2Value = bandValues[i]; // Use the corresponding value
            final int color = getColorForBand(i);
            button.setOnClickListener(v -> {
                resistorView.setBandColor2(color);
                C2 = c2Value; // Set C1 based on button clicked
                //Toast.makeText(this, "C2 is now: " + C2, Toast.LENGTH_SHORT).show();
            });
        }
        for (int i = 0; i < multiplierBtnIds.length; i++) {
            Button button = findViewById(multiplierBtnIds[i]);
            final double c3Value = multiplierValues[i]; // Use the corresponding value
            final int color = getColorForBand(i);
            button.setOnClickListener(v -> {
                resistorView.setBandColor3(color);
                C3 = c3Value; // Set C1 based on button clicked
                //Toast.makeText(this, "C3 is now: " + C3, Toast.LENGTH_SHORT).show();
            });
        }
        for (int i = 0; i < toleranceBtnId.length; i++) {
            Button button = findViewById(toleranceBtnId[i]);
            final double tolValue = ToleranceValues[i]; // Use the corresponding value
            final int color = getColorForTolerance(i);
            button.setOnClickListener(v -> {
                resistorView.setBandColor4(color);
                Tolerance = tolValue; // Set C1 based on button clicked
               // Toast.makeText(this, "Tolerance is now: " + Tolerance, Toast.LENGTH_SHORT).show();
            });
        }
    }
    private int getColorForBand(int index) {
        switch (index) {
            case 0: return Color.BLACK;
            case 1: return Color.parseColor("#A52A2A"); // Brown
            case 2: return Color.RED;
            case 3: return Color.parseColor("#FFA500"); // Orange
            case 4: return Color.YELLOW;
            case 5: return Color.GREEN;
            case 6: return Color.BLUE;
            case 7: return Color.parseColor("#EE82EE"); // Violet
            case 8: return Color.GRAY;
            case 9: return Color.WHITE;
            case 10: return Color.rgb(255, 215, 0); // Gold
            case 11: return Color.rgb(192, 192, 192); // Silver
            default: return Color.TRANSPARENT; // Fallback
        }
    }
    private int getColorForTolerance(int index){
        switch (index){
        case 0: return Color.parseColor("#A52A2A"); // Brown
        case 1: return Color.RED;
        case 2: return Color.GREEN;
        case 3: return Color.BLUE;
        case 4: return Color.parseColor("#EE82EE"); // Violet
        case 5: return Color.GRAY;
        case 6: return Color.rgb(255, 215, 0); // Gold
        case 7: return Color.rgb(192, 192, 192); // Silver
        default: return Color.TRANSPARENT;}
    }

}
