package com.example.verdidacusap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class endgame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endgame);

        TextView userScores = findViewById(R.id.userScore);
        Button playAgain = findViewById(R.id.tryAgain);
        String userScore = getIntent().getStringExtra("userScore");
        userScores.setText(userScore);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(endgame.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}