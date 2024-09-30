package com.example.myapplication;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.text.DecimalFormat;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {
    public double height=0;
    public double weight=0;
    public double Bmi=0;
    public String size;
    public ProgressBar Bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button exit = findViewById(R.id.btnExit);
        TextView Textbmi=findViewById(R.id.txtBMI);
        Bar=findViewById((R.id.BmiProgressBar));
        TextView TxtThickness=findViewById(R.id.Thickness);
        height = getIntent().getDoubleExtra("height", 0);
        weight = getIntent().getDoubleExtra("weight", 0);
        calc();
        DecimalFormat df = new DecimalFormat("#.#");
        // Format the number
        String FBmi = df.format(Bmi);
        Textbmi.setText("Your BMI is "+ FBmi);
        TxtThickness.setText("You are "+size);
        Bar.setProgress((int) Bmi);
        setProgressBarTint(Bmi);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }
    public void calc()
    {
          double h = height/100;
          double w = weight;
          Bmi= w/Math.pow(h, 2);
          if (Bmi<18.5) {size="UnderWeight";}
          else if (Bmi>25){size="OverWeight";}
          else{size="Healthy";}
    }
    private void setProgressBarTint(double bmi) {
        // Change the color of the ProgressBar based on BMI range
        if (bmi < 18.5) {
            // Underweight (Light Blue)
            Bar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_blue)));

        } else if (bmi >= 18.5 && bmi < 24.9) {
            // Normal weight (Green)
            Bar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_green)));
        } else if (bmi >= 25 && bmi < 29.9) {
            // Overweight (Yellow)
            Bar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.yellow)));
        } else {
            // Obese (Red)
            Bar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red)));
        }
    }
}