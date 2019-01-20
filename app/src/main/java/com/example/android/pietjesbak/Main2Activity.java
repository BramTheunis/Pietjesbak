package com.example.android.pietjesbak;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Main2Activity extends AppCompatActivity {

    private SensorManager sm;

    private float acelVal;
    private float acelLast;
    private float shake;

    // variabelen voor usernames
    TextView name1;
    String st1;
    TextView name2;
    String st2;

    // variabelen voor te dobbelen
    public static final Random RANDOM = new Random();
    private Button rollDice;
    private ImageView dice1, dice2, dice3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

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

        rollDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

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
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public static int randomDiceValue() {
        return RANDOM.nextInt(6) + 1;
    }


}
