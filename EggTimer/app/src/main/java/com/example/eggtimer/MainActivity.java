package com.example.eggtimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    int timeout;
    TextView myTextView;

    public void buttonClicked(View view) {
        Button myButton = (Button) findViewById(R.id.myButton);
        new CountDownTimer(timeout * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                setRemainingTime((int) (millisUntilFinished / 1000));
            }

            public void onFinish() {
                myTextView.setText("done!");
            }
        }.start();

    }

    public void setRemainingTime(int seconds) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
        String myDate = dateFormat.format(new Date(TimeUnit.SECONDS.toMillis(seconds)));
        Log.i("EggTimer", myDate);
        myTextView.setText(myDate);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar mySeekBar = (SeekBar)findViewById(R.id.mySeekBar);
        myTextView = (TextView) findViewById(R.id.myTextView);

        mySeekBar.setMax(600);
        mySeekBar.setProgress(30);
        setRemainingTime(30);
        timeout = 30;

        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timeout = progress;
                Log.i("EggTimer", "Timeout: " + timeout);
                setRemainingTime(timeout);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
