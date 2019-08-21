package com.example.android.pietjesbak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    EditText et1;
    String st1;
    EditText et2;
    String st2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // onclicklistener toevoegen aan button om spel te starten
        btn = (Button) findViewById(R.id.button);
        et1 = (EditText) findViewById(R.id.namePlayer1);
        et2 = (EditText) findViewById(R.id.namePlayer2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame();
            }
        });
    }

    // Intent aanmaken om naar volgende activity te gaan
    public void openGame() {
        Intent intent = new Intent(this, Main2Activity.class);
        st1 = et1.getText().toString();
        st2 = et2.getText().toString();
        intent.putExtra("Value1", st1);
        intent.putExtra("Value2", st2);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch  (item.getItemId()) {
            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));

                break;
        }

        return true;
    }
}
