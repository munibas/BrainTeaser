package com.example.brainteaser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int a = 0;
    int b = 0;
    int answer;
    int score = 0;
    int noOfQuestionsAsked = 0;
    TextView scoreTextView;
    TextView sumTextView;
    TextView timerTextView;
    TextView resultTextView;
    //ConstraintLayout gameConstraintLayout;
    long timeInMilliSeconds = 30000;
    long endTime;


    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button playAgain;

    ArrayList<Integer> answersArrayList = new ArrayList<Integer>();

    CountDownTimer timer;



    public void startTimer(){

        endTime = System.currentTimeMillis() + timeInMilliSeconds;

        timer = new CountDownTimer(timeInMilliSeconds,1000) {
            @Override
            public void onTick(long l) {
                timeInMilliSeconds = l;
                Log.i("Seconds Left",String.valueOf(l/1000));
                UpdateTimer((int) l/1000);

                /* timerTextView.setText((int)l/1000+"s");*/
            }

            @Override
            public void onFinish() {
                noOfQuestionsAsked--;
                resultTextView.setText("Your Score: "+score+"/"+ noOfQuestionsAsked);
                playAgain.setVisibility(View.VISIBLE);
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
            }
        }.start();
    }

    public void generateQuestion(){

        Random rand = new Random();

        a = rand.nextInt(21);
        b = rand.nextInt(21);

        int locationOfCorrectAnswer = rand.nextInt(4);
        int incorrectAnswer = 0;

        sumTextView.setText(a+ " + "+b);
        answer = a+b;


        noOfQuestionsAsked++;
        scoreTextView.setText(Integer.toString(score));

        answersArrayList.clear();



        for (int i=0;i<4;i++){
            if (i == locationOfCorrectAnswer){
                answersArrayList.add(answer);
            }else {

                incorrectAnswer = rand.nextInt(41);

                while (incorrectAnswer == answer) {
                    incorrectAnswer = rand.nextInt(41);
                }

                answersArrayList.add(incorrectAnswer);

            }

        }


        button0.setText(answersArrayList.get(0).toString());
        button1.setText(answersArrayList.get(1).toString());
        button2.setText(answersArrayList.get(2).toString());
        button3.setText(answersArrayList.get(3).toString());
    }




    public void ChoosingAnswer(View view){


        Button buttonPressed = (Button) view;


        Log.i("button Pressed",buttonPressed.getTag().toString());

        Log.i("button Pressed",buttonPressed.getText().toString());

        if (answer == Integer.parseInt(buttonPressed.getText().toString())) {
            resultTextView.setText("Correct!");
            score++;
        }
        else {
            resultTextView.setText("Wrong!");
        }

        scoreTextView.setText(Integer.toString(score));
        generateQuestion();
    }



    public  void UpdateTimer(int secondsLeft){

        int minutes = (int) secondsLeft/60;

        int seconds = secondsLeft-minutes*60;

        if (seconds<10) {

            timerTextView.setText(minutes + ":0" + seconds);
        }else

            timerTextView.setText(minutes + ":" + seconds);
    }


    public void playAgain(View view){

        playAgain.setVisibility(View.INVISIBLE);
        score = 0;
        noOfQuestionsAsked = 0;
        timerTextView.setText("30s");
        scoreTextView.setText("0");
        resultTextView.setText("");
        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        timeInMilliSeconds = 30000;
        startTimer();
        generateQuestion();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {

            Toast.makeText(this, "savedInstanceState != null)", Toast.LENGTH_LONG).show();

            timeInMilliSeconds = savedInstanceState.getLong("timeInMilliSeconds",0);
            endTime = savedInstanceState.getLong("endTime",0);
            timeInMilliSeconds = endTime - System.currentTimeMillis();

            Log.i("Seconds Left",String.valueOf(timeInMilliSeconds));

            if(timeInMilliSeconds>0) {
                startTimer();
            }

        }



        sumTextView = (TextView) findViewById(R.id.sumTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        resultTextView = (TextView) findViewById(R.id.resultTextView);

        playAgain = (Button) findViewById(R.id.playAgainButton);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        playAgain.setVisibility(View.INVISIBLE);


        if (savedInstanceState == null) {

            Toast.makeText(this, "savedInstanceState == null", Toast.LENGTH_LONG).show();

            startTimer();
            generateQuestion();


        }


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("message", "This is a saved message");
        outState.putInt("score", score);
        outState.putInt("noOfQuestionsAsked", noOfQuestionsAsked);
        outState.putInt("a",a);
        outState.putInt("b",b);
        outState.putInt("answer",answer);
        outState.putIntegerArrayList("answersArrayList",answersArrayList);
        outState.putLong("timeInMilliSeconds",timeInMilliSeconds);
        outState.putLong("endTime",endTime);
       // Toast.makeText(getApplicationContext(), "onSaveInstanceState", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(getApplicationContext(), "onRestoreInstanceState", Toast.LENGTH_SHORT).show();
        score = savedInstanceState.getInt("score", 0);
        noOfQuestionsAsked = savedInstanceState.getInt("noOfQuestionsAsked", 0);
        scoreTextView.setText(Integer.toString(score));
        a = savedInstanceState.getInt("a",0);
        b = savedInstanceState.getInt("b",0);
        answer = savedInstanceState.getInt("answer",0);
        answersArrayList = savedInstanceState.getIntegerArrayList("answersArrayList");
        timeInMilliSeconds = savedInstanceState.getLong("timeInMilliSeconds",0);
        endTime = savedInstanceState.getLong("endTime",0);


        sumTextView.setText(a+ " + "+b);
        button0.setText(answersArrayList.get(0).toString());
        button1.setText(answersArrayList.get(1).toString());
        button2.setText(answersArrayList.get(2).toString());
        button3.setText(answersArrayList.get(3).toString());

    }
}