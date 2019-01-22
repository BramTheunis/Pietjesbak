package com.example.android.pietjesbak;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main2Activity extends AppCompatActivity {

    // declareren variabelen voor shakesensor
    private SensorManager sm;
    private float acelVal;
    private float acelLast;
    private float shake;

    // variabelen voor usernames
    TextView name1;
    String st1;
    TextView name2;
    String st2;

    // variabelen voor dobbelen
    public static final Random RANDOM = new Random();
    private Button rollDice;
    private ImageView dice1, dice2, dice3;

    // variabelen voor vastzetten dobbelstenen
    CheckBox c1, c2, c3;

    // variabelen voor spelers
    TextView scoreOne, scoreTwo, tempScore, turnPlayer1;

    private static int SCORE_PLAYER_ONE = 0;
    private static int SCORE_PLAYER_TWO = 0;

    private static int DICE_ROLL = 0;
    private static int TEMP_SCORE = 0;

    private Button passTurn;

    ImageView playerOneIndicator, playerTwoIndicator;

    boolean playerOne = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initializeWidgets();

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;

        name1 = findViewById(R.id.namePlayer1);
        name2 = findViewById(R.id.namePlayer2);

        st1 = getIntent().getExtras().getString("Value1");
        name1.setText(st1);
        st2 = getIntent().getExtras().getString("Value2");
        name2.setText(st2);

        rollDice = findViewById(R.id.rollDice);
        dice1 = findViewById(R.id.dice1);
        dice2 = findViewById(R.id.dice2);
        dice3 = findViewById(R.id.dice3);

        c1 = findViewById(R.id.checkBox1);
        c2 = findViewById(R.id.checkBox2);
        c3 = findViewById(R.id.checkBox3);

        // variabele turn


        rollDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c1.isChecked() && c2.isChecked()) {
                    int value3 = randomDiceValue();

                    int res3 = getResources().getIdentifier("dice_" + value3, "drawable", "com.example.android.pietjesbak");

                    dice3.setImageResource(res3);

                } else if (c1.isChecked() && c3.isChecked()) {
                    int value2 = randomDiceValue();

                    int res2 = getResources().getIdentifier("dice_" + value2, "drawable", "com.example.android.pietjesbak");

                    dice2.setImageResource(res2);

                } else if (c2.isChecked() && c3.isChecked()) {
                    int value1 = randomDiceValue();

                    int res1 = getResources().getIdentifier("dice_" + value1, "drawable", "com.example.android.pietjesbak");

                    dice1.setImageResource(res1);

                } else if (c1.isChecked()) {
                    int value2 = randomDiceValue();
                    int value3 = randomDiceValue();

                    int res2 = getResources().getIdentifier("dice_" + value2, "drawable", "com.example.android.pietjesbak");
                    int res3 = getResources().getIdentifier("dice_" + value3, "drawable", "com.example.android.pietjesbak");

                    dice2.setImageResource(res2);
                    dice3.setImageResource(res3);

                } else if (c2.isChecked()) {
                    int value1 = randomDiceValue();
                    int value3 = randomDiceValue();

                    int res1 = getResources().getIdentifier("dice_" + value1, "drawable", "com.example.android.pietjesbak");
                    int res3 = getResources().getIdentifier("dice_" + value3, "drawable", "com.example.android.pietjesbak");

                    dice1.setImageResource(res1);
                    dice3.setImageResource(res3);

                } else if (c3.isChecked()) {
                    int value1 = randomDiceValue();
                    int value2 = randomDiceValue();

                    int res1 = getResources().getIdentifier("dice_" + value1, "drawable", "com.example.android.pietjesbak");
                    int res2 = getResources().getIdentifier("dice_" + value2, "drawable", "com.example.android.pietjesbak");

                    dice1.setImageResource(res1);
                    dice2.setImageResource(res2);

                } else {
                    int value1 = randomDiceValue();
                    int value2 = randomDiceValue();
                    int value3 = randomDiceValue();

                    int res1 = getResources().getIdentifier("dice_" + value1, "drawable", "com.example.android.pietjesbak");
                    int res2 = getResources().getIdentifier("dice_" + value2, "drawable", "com.example.android.pietjesbak");
                    int res3 = getResources().getIdentifier("dice_" + value3, "drawable", "com.example.android.pietjesbak");

                    dice1.setImageResource(res1);
                    dice2.setImageResource(res2);
                    dice3.setImageResource(res3);
                }

                // beurt mechanisme
                TextView turnPlayer1 = (TextView) findViewById(R.id.turnPlayer1);
                String tp1 = turnPlayer1.getText().toString();
                int tp1_number = Integer.parseInt(tp1);

                TextView turnPlayer2 = (TextView) findViewById(R.id.turnPlayer2);
                String tp2 = turnPlayer2.getText().toString();
                int tp2_number = Integer.parseInt(tp2);

                if (playerOne == true) {
                    tp1_number = tp1_number+1;
                    String tp1_new_text = tp1_number + "";
                    turnPlayer1.setText(tp1_new_text);
                    if (tp1_number == 3) {
                        playerOne = false;
                        playerOneIndicator.setVisibility(View.INVISIBLE);
                        playerTwoIndicator.setVisibility(View.VISIBLE);
                    }
                } else if (playerOne == false) {
                    tp2_number = tp2_number+1;
                    String tp2_new_text = tp2_number + "";
                    turnPlayer2.setText(tp2_new_text);
                    tp1_number = tp1_number-1;
                    if (tp2_number-1 == tp1_number) {
                        // resetfunctie in de plaats zetten
                        playerOneIndicator.setVisibility(View.VISIBLE);
                        playerTwoIndicator.setVisibility(View.INVISIBLE);
                    }
                }


            }
        });
    }

    private void initializeWidgets() {
        playerOneIndicator = (ImageView) findViewById(R.id.imageViewPlayerOne);
        playerTwoIndicator = (ImageView) findViewById(R.id.imageViewPlayerTwo);
        passTurn = (Button) findViewById(R.id.passTurn);
        turnPlayer1 = (TextView) findViewById(R.id.turnPlayer1);
        scoreOne = (TextView) findViewById(R.id.scoreSpeler1);
        scoreTwo = (TextView) findViewById(R.id.scoreSpeler2);
    }

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            acelLast = acelVal;
            acelVal = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = acelVal - acelLast;
            shake = shake * 0.9f + delta;

            if (shake > 12) {
                if (c1.isChecked() && c2.isChecked()) {
                    int value3 = randomDiceValue();

                    int res3 = getResources().getIdentifier("dice_" + value3, "drawable", "com.example.android.pietjesbak");

                    dice3.setImageResource(res3);

                } else if (c1.isChecked() && c3.isChecked()) {
                    int value2 = randomDiceValue();

                    int res2 = getResources().getIdentifier("dice_" + value2, "drawable", "com.example.android.pietjesbak");

                    dice2.setImageResource(res2);

                } else if (c2.isChecked() && c3.isChecked()) {
                    int value1 = randomDiceValue();

                    int res1 = getResources().getIdentifier("dice_" + value1, "drawable", "com.example.android.pietjesbak");

                    dice1.setImageResource(res1);

                } else if (c1.isChecked()) {
                    int value2 = randomDiceValue();
                    int value3 = randomDiceValue();

                    int res2 = getResources().getIdentifier("dice_" + value2, "drawable", "com.example.android.pietjesbak");
                    int res3 = getResources().getIdentifier("dice_" + value3, "drawable", "com.example.android.pietjesbak");

                    dice2.setImageResource(res2);
                    dice3.setImageResource(res3);

                } else if (c2.isChecked()) {
                    int value1 = randomDiceValue();
                    int value3 = randomDiceValue();

                    int res1 = getResources().getIdentifier("dice_" + value1, "drawable", "com.example.android.pietjesbak");
                    int res3 = getResources().getIdentifier("dice_" + value3, "drawable", "com.example.android.pietjesbak");

                    dice1.setImageResource(res1);
                    dice3.setImageResource(res3);

                } else if (c3.isChecked()) {
                    int value1 = randomDiceValue();
                    int value2 = randomDiceValue();

                    int res1 = getResources().getIdentifier("dice_" + value1, "drawable", "com.example.android.pietjesbak");
                    int res2 = getResources().getIdentifier("dice_" + value2, "drawable", "com.example.android.pietjesbak");

                    dice1.setImageResource(res1);
                    dice2.setImageResource(res2);

                } else {
                    int value1 = randomDiceValue();
                    int value2 = randomDiceValue();
                    int value3 = randomDiceValue();

                    int res1 = getResources().getIdentifier("dice_" + value1, "drawable", "com.example.android.pietjesbak");
                    int res2 = getResources().getIdentifier("dice_" + value2, "drawable", "com.example.android.pietjesbak");
                    int res3 = getResources().getIdentifier("dice_" + value3, "drawable", "com.example.android.pietjesbak");

                    dice1.setImageResource(res1);
                    dice2.setImageResource(res2);
                    dice3.setImageResource(res3);
                }

                TextView turnPlayer1 = (TextView) findViewById(R.id.turnPlayer1);
                String tp1 = turnPlayer1.getText().toString();
                int tp1_number = Integer.parseInt(tp1);

                TextView turnPlayer2 = (TextView) findViewById(R.id.turnPlayer2);
                String tp2 = turnPlayer2.getText().toString();
                int tp2_number = Integer.parseInt(tp2);

                if (playerOne == true) {
                    tp1_number = tp1_number+1;
                    String tp1_new_text = tp1_number + "";
                    turnPlayer1.setText(tp1_new_text);
                    if (tp1_number == 3) {
                        playerOne = false;
                        playerOneIndicator.setVisibility(View.INVISIBLE);
                        playerTwoIndicator.setVisibility(View.VISIBLE);
                    }
                } else if (playerOne == false) {
                    tp2_number = tp2_number+1;
                    String tp2_new_text = tp2_number + "";
                    turnPlayer2.setText(tp2_new_text);
                    tp1_number = tp1_number-1;
                    if (tp2_number-1 == tp1_number) {
                        // resetfunctie in de plaats zetten
                        playerOneIndicator.setVisibility(View.VISIBLE);
                        playerTwoIndicator.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public static int randomDiceValue() {
        return RANDOM.nextInt(6) + 1;
    }


}
