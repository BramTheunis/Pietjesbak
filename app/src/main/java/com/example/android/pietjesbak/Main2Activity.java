package com.example.android.pietjesbak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class Main2Activity extends AppCompatActivity {

    TextView tv1;
    String st1;
    TextView tv2;
    String st2;
    public static final Random RANDOM = new Random();
    private Button rollDice;
    private ImageView dice1, dice2, dice3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tv1 = findViewById(R.id.namePlayer1);
        tv2 = findViewById(R.id.namePlayer2);

        st1 = getIntent().getExtras().getString("Value1");
        tv1.setText(st1);
        st2 = getIntent().getExtras().getString("Value2");
        tv2.setText(st2);

        rollDice = (Button) findViewById(R.id.rollDice);
        dice1 = (ImageView) findViewById(R.id.dice1);
        dice2 = (ImageView) findViewById(R.id.dice2);
        dice3 = (ImageView) findViewById(R.id.dice3);

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

    public static int randomDiceValue() {
        return RANDOM.nextInt(6) + 1;
    }
}
