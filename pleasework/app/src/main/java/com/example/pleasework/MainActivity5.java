package com.example.pleasework;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity5 extends AppCompatActivity {
    public TextView score;
    public int rngint;
    public int Level;
    public ArrayList<Integer> NumberList = new ArrayList<>();
    public ArrayList<ColorStateList> ColorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main5);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Level = getIntent().getIntExtra("StartLevel", 2);

        AddLevel();
        score = findViewById(R.id.ScoreTxt);
        sequence();
        score.setText(NumberList.toString());

        for (int i = 0; i < Level; i++) {
            ColorList.add(ChangeToColor(NumberList.get(i)));
        }


        Log.d("DEBUG", "ColorList: " + ColorList.toString());

    }

    public void sequence() {
        for (int i = 1; i <= Level; i++) {
            RNG();
            NumberList.add(rngint);
        }
    }

    public void RNG() {
        rngint = (int) (Math.random() * 4 + 1);
        System.out.println(rngint);
    }

    public void AddLevel() {
        Level += 2;
    }

    public ColorStateList ChangeToColor(int RNGC) {
        switch (RNGC) {
            case 1:
                return ColorStateList.valueOf(Color.BLUE);
            case 2:
                return ColorStateList.valueOf(Color.YELLOW);
            case 3:
                return ColorStateList.valueOf(Color.RED);
            case 4:
                return ColorStateList.valueOf(Color.GREEN);
            default:
                return ColorStateList.valueOf(Color.GRAY);
        }
    }
}
