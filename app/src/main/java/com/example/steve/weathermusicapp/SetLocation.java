package com.example.steve.weathermusicapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Steve on 4/3/2017.
 */

public class SetLocation extends AppCompatActivity {
    EditText addrZip, addrCC;
    Button button;
    Button btnShowLocation;
    String zip, cc;
    int layout;
    boolean check = false;

    public SetLocation() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            layout = extras.getInt("layout");

            check = true;

        }

        // Required call through to Activity.onCreate()
        // Restore any saved instance state
        super.onCreate(savedInstanceState);

        // Set content view
        setContentView(R.layout.activity_setlocation);

        // Initialize UI elements
        addrZip = (EditText) findViewById(R.id.zip);
        addrCC = (EditText) findViewById(R.id.countrycode);
        button = (Button) findViewById(R.id.mapButton);


        // Link UI elements to actions in code
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            // Called when user clicks the Show Map button
            public void onClick(View v) {
                zip = addrZip.getText().toString();
                cc = addrCC.getText().toString();

                if (check) {
                    Intent i = new Intent(SetLocation.this, Choice.class);
                    Bundle extras = new Bundle();
                    extras.putString("zip", zip);
                    extras.putString("cc", cc);
                    extras.putInt("layout", layout);
                    i.putExtras(extras);
                    startActivity(i);
                } else {
                    Intent i = new Intent(SetLocation.this, WeatherMusic.class);
                    Bundle extras = new Bundle();
                    extras.putString("zip", zip);
                    extras.putString("cc", cc);
                    extras.putInt("layout", layout);
                    i.putExtras(extras);
                    startActivity(i);

                }
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }



}


