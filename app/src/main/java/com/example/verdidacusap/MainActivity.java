package com.example.verdidacusap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    public static MediaPlayer player;
    private AutoCompleteTextView difficulty,operation;

    public String selectedStage,selectedOperation;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Button start = findViewById(R.id.play);
            setDifficulty();
            setOperation();

            Button play = findViewById(R.id.play);

            player = MediaPlayer.create(MainActivity.this, R.raw.intro);
            player.setLooping(true); // Set looping
            player.setVolume(80, 80);


            player.start();


            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =  new Intent(MainActivity.this,game.class);
                    intent.putExtra("difficulty", selectedStage);
                    intent.putExtra("operation", selectedOperation);
                    startActivity(intent);
                }
            });

        }

        void setDifficulty (){

            difficulty = findViewById(R.id.difficulty);

            ArrayList<String> stages = new ArrayList<>();

            stages.add("Easy");
            stages.add("Normal");
            stages.add("Hard");

            ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item,stages);
            difficulty.setAdapter(difficultyAdapter);

            difficulty.setOnItemClickListener((adapterView, view, i, l) ->{
                selectedStage = adapterView.getItemAtPosition(i).toString();
            });
        }

        void setOperation (){

            operation = findViewById(R.id.operation);

            ArrayList<String> Operation = new ArrayList<>();

            Operation.add("addition");
            Operation.add("subtraction");
            Operation.add("multiplication");
            Operation.add("division");

            ArrayAdapter<String> operationAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item,Operation);
            operation.setAdapter(operationAdapter);

            operation.setOnItemClickListener((adapterView, view, i, l) ->{
                selectedOperation = adapterView.getItemAtPosition(i).toString();
            });
        }



    }
