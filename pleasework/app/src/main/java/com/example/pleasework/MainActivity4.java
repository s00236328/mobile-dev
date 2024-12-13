package com.example.pleasework;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class MainActivity4 extends AppCompatActivity {
public int Highscore;
public TextView Name;
public TextView score;
private Button saveButton;
private Button Againbtn;
private Button Highscorebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize the db here
        DbHelper dbHelper = new DbHelper(this);


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        score = findViewById(R.id.txtScore);
        saveButton = findViewById(R.id.Savebtn);
        Highscorebtn = findViewById(R.id.highbtn);
        Againbtn = findViewById(R.id.againbtn);
        Highscore = getIntent().getIntExtra("score", 4);
        score.setText("you got " + Highscore);
        Name=findViewById(R.id.YourNametxt);

        //put into save onclick listener

        saveButton.setOnClickListener(v -> {
            // Save the player's name and score to the database
            String name =Name.toString();
            dbHelper.insertPlayer(name, Highscore);

            // Show a confirmation message
            Toast.makeText(MainActivity4.this, "Score saved successfully!", Toast.LENGTH_SHORT).show();
        });
        Againbtn.setOnClickListener(v -> {
            Intent Game = new Intent(MainActivity4.this, MainActivity.class);

            startActivity(Game);
         });
        Highscorebtn.setOnClickListener(v -> {
            Intent Game = new Intent(MainActivity4.this, MainActivity3.class);

            startActivity(Game);
           });
    }
}