package com.example.verdidacusap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;

public class game extends AppCompatActivity {


    public int gameScore,gameStat,difficultyBound = 0;
    public int maxGame = 10;
    public String Operand;

    public int Answer = 0;
    public int[] Generated =  {0,0};
    public int[] GeneratedAnswer =  {0,0,0};
    MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Button slotOne = findViewById(R.id.slotOne);
        Button slotTwo = findViewById(R.id.slotTwo);
        Button num1 = findViewById(R.id.num1);
        Button num2 = findViewById(R.id.num2);
        Button num3 = findViewById(R.id.num3);
        Button operation = findViewById(R.id.operation);
        TextView score = findViewById(R.id.score);

        score.setText("0 / " + maxGame);
        setStage();

        setOperand();

        operation.setText(Operand);



        init(slotOne,slotTwo,num1,num2,num3);

        userAction(num1,slotOne,slotTwo,num1,num2,num3,score);
        userAction(num2,slotOne,slotTwo,num1,num2,num3,score);
        userAction(num3,slotOne,slotTwo,num1,num2,num3,score);

    }

    void userAction (Button btn,Button slot1,Button slot2,Button num1,Button num2,Button num3,TextView score){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameStat++;

                if(gameStat == maxGame){
                    Intent intent =  new Intent(game.this,endgame.class);
                    intent.putExtra("userScore", String.valueOf(gameScore));
                    startActivity(intent);
                }

                if(gameStat != maxGame){
                    if(Integer.parseInt(btn.getText().toString()) == Answer){
                        gameScore++;
                        score.setText(String.valueOf(gameScore) + " / 10");
                        player = MediaPlayer.create(game.this, R.raw.correct);
                        player.setLooping(false); // Set looping
                        player.setVolume(100, 100);
                        player.start();
                    }
                    if(Integer.parseInt(btn.getText().toString()) != Answer){
                        player = MediaPlayer.create(game.this, R.raw.wrong);
                        player.setLooping(false); // Set looping
                        player.setVolume(100, 100);
                        player.start();

                    }
                    init(slot1,slot2,num1,num2,num3);
                }

            }
        });
    }

    void init(Button slot1,Button slot2,Button num1,Button num2,Button num3){

        randomImage();


        generate();


        Answer = Calculate(Generated[0] + Operand +Generated[1]);


        GeneratedAnswer = generateAnswer(Answer);



        shuffleArray(GeneratedAnswer);

        slot1.setText(""+Generated[0]+"");
        slot2.setText(""+Generated[1]+"");

        num1.setText(""+GeneratedAnswer[0]+"");
        num2.setText(""+GeneratedAnswer[1]+"");
        num3.setText(""+GeneratedAnswer[2]+"");

    }

    void setStage(){
        switch(getIntent().getStringExtra("difficulty")){
            case "Easy":
                difficultyBound = 10;
                break;
            case "Normal":
                difficultyBound = 50;
                break;
            case "Hard":
                difficultyBound = 100;
                break;
        }
    }

    void setOperand(){
        switch(getIntent().getStringExtra("operation")){
            case "addition":
                Operand = "+";
                break;
            case "subtraction":
                Operand = "-";
                break;
            case "multiplication":
                Operand = "*";
                break;
            case "division":
                Operand = "/";
                break;
        }
    }

    void generate(){

        Random rand = new Random();


        do{

            if(getIntent().getStringExtra("operation").equals("addition")) {
                int randPlusOne = rand.nextInt(difficultyBound);
                int randPlusTwo = rand.nextInt(difficultyBound);
                Generated[0] = randPlusOne;
                Generated[1] = randPlusTwo;
                break;
            }

            if(getIntent().getStringExtra("operation").equals("subtraction")){
                int randMinusOne = rand.nextInt(difficultyBound);
                int roundMinusTwo = rand.nextInt(difficultyBound);

                if((randMinusOne - roundMinusTwo) <= -1){
                    continue;
                }else{
                    Generated[0] = randMinusOne;
                    Generated[1] = roundMinusTwo;
                    break;
                }
            }

            if(getIntent().getStringExtra("operation").equals("multiplication")) {
                int randMultOne = rand.nextInt(difficultyBound);
                int randMultTwo = rand.nextInt(difficultyBound);
                Generated[0] = randMultOne;
                Generated[1] = randMultTwo;
                break;
            }

            if(getIntent().getStringExtra("operation").equals("division")){
                int randDivideOne = rand.nextInt(difficultyBound);
                int randDivideTwo = rand.nextInt(difficultyBound);

                if((randDivideOne / randDivideTwo) == 0){
                    continue;
                }else{
                    Generated[0] = randDivideOne;
                    Generated[1] = randDivideTwo;
                    break;
                }
            }

        }while(true);


    }

    int[] generateAnswer(int Answer){
        int [] arr = {0,0,Answer};

        Random rand = new Random();
        for (int i = 0; i <= 1; i ++){
            int generate = rand.nextInt(100);
            if(i == 0){
                arr[i] = generate;
            }else{
                arr[i] = generate;
            }
        }

        return arr;
    }

    int Calculate (String str){

        String numberStr[] = str.replaceAll("[+*/()-]+"," ").split(" ");
        // will get all operators and store it to `operatorStr`
        String operatorStr[] = str.replaceAll("[0-9()]+","").split("");

        int total = Integer.parseInt(numberStr[0]);

        for (int i=0; i<operatorStr.length; i++) {
            switch (operatorStr[i]) {
                case "+" :
                    total += Integer.parseInt(numberStr[i+1]);
                    break;
                case "-" :
                    total -= Integer.parseInt(numberStr[i+1]);
                    break;
                case "*" :
                    total *= Integer.parseInt(numberStr[i+1]);
                    break;
                case "/" :
                    total /= Integer.parseInt(numberStr[i+1]);
                    break;
            }

            if(i+2 >= operatorStr.length) continue; // if meets the last operands already
            numberStr[i+1] = String.valueOf(total);

        }
        return  total;
    }

    void shuffleArray(int[] array)
    {
        int index, temp;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    void randomImage(){
        ImageView display = findViewById(R.id.display);
        int[] images = {
                R.drawable.emoji1,
                R.drawable.emoji2,
                R.drawable.emoji4,
                R.drawable.emoji5,
                R.drawable.emoji6,
                R.drawable.emoji7,
                R.drawable.emoji8,
                R.drawable.emoji9,
                R.drawable.emoji10,
        };
        Random rand = new Random();
        display.setImageResource(images[rand.nextInt(images.length)]);
    }
}