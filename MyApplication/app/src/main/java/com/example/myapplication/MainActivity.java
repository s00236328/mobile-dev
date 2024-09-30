package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
public double height;
public double weight;
public EditText H;
public EditText W;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button btnClear = findViewById(R.id.clear);
        Button btnNext = findViewById(R.id.next);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        OnReturn();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Grab(v);
                if (weight>=20 && weight<200 && height>= 80 && height<300)
                {

                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("height", height); // Pass height
                    intent.putExtra("weight", weight); // Pass weight
                    startActivity(intent);
                    //bring to next page + calculations
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"one of your inputs doesn't meet the criteria" +
                            "make sure your height is in cm and weight is in kg", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Grab(v);
                H.setText("0");
                W.setText("0");
                height=0;
                weight=0;
            }
        });

    }
    public void Grab(View view)
    {
        H = findViewById(R.id.height);
        W = findViewById(R.id.weight);

        // Check if the height or weight EditText is empty
        if (H.getText() == null || H.getText().toString().isEmpty() ||
                W.getText() == null || W.getText().toString().isEmpty()) {
            height = 0;
            weight = 0;
            Toast.makeText(getApplicationContext(), "Please enter valid height and weight.", Toast.LENGTH_SHORT).show();
        } else {
            // Parse height and weight if they are not empty
            try {
                height = Double.parseDouble(H.getText().toString());
                weight = Double.parseDouble(W.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Please enter valid numerical values.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
