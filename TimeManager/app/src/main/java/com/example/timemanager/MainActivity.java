package com.example.timemanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final int MSG_START_TIMER = 0;
    final int MSG_STOP_TIMER = 1;
    final int MSG_UPDATE_TIMER = 2;

    Stopwatch timer = new Stopwatch();
    final int REFRESH_RATE = 100;
    final static String TAG = "TimeManager";

    private SQLiteDatabase myDatabase;
    
    long startTime, stopTime;
	private Calendar cl;
	
    Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_START_TIMER:
                    startTime = timer.start(); //start timer:
					timerStarted(startTime);

                   
                    mHandler.sendEmptyMessage(MSG_UPDATE_TIMER);
                    break;

                case MSG_UPDATE_TIMER:
                    setElapsedTime((int)timer.getElapsedTimeSecs());
                    mHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIMER,REFRESH_RATE); //text view is updated every second,
                    break;                                  //though the timer is still running
                case MSG_STOP_TIMER:
                    mHandler.removeMessages(MSG_UPDATE_TIMER); // no more updates.
                    stopTime = timer.stop();//stop timer
					timerStopped(stopTime);
                    setElapsedTime((int)timer.getElapsedTimeSecs());
                    break;

                default:
                    break;
            }
        }
    };

    TextView timerView;
    FloatingActionButton btnStart,btnStop;

    private void setElapsedTime(int seconds) {
		int offset = cl.getTimeZone().getOffset(cl.getTimeInMillis());
		seconds -= offset / 1000;
		Date date = new Date(TimeUnit.SECONDS.toMillis(seconds));
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String myDate = dateFormat.format(date);

        timerView.setText(myDate);
    }

	private void timerStarted(long startTime) {
        cl.setTimeInMillis(startTime);
        String date = "" + cl.get(Calendar.DAY_OF_MONTH) + ":" + cl.get(Calendar.MONTH) + ":" + cl.get(Calendar.YEAR);
        String time = "" + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND);
        Log.i(TAG, "Start: " + date + ", " + time);
					
        myDatabase.execSQL("INSERT INTO times (name, start, end) VALUES ('test', " +  Long.toString(startTime) +",-1)");
	}
	
	private void timerStopped(long stopTime) {
        cl.setTimeInMillis(stopTime);
        String date = "" + cl.get(Calendar.DAY_OF_MONTH) + ":" + cl.get(Calendar.MONTH) + ":" + cl.get(Calendar.YEAR);
        String time = "" + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND);
        Log.i(TAG, "Stop: " + date + ", " + time);
		
        myDatabase.execSQL("UPDATE times SET end=" + Long.toString(stopTime) + " WHERE name = 'test'");
	}
	
    public void onClick(View v) {
        if(btnStart == v)
        {
            Snackbar.make(v, "Timer started!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            mHandler.sendEmptyMessage(MSG_START_TIMER);
        }else
        if(btnStop == v){
            Snackbar.make(v, "Timer ended!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            mHandler.sendEmptyMessage(MSG_STOP_TIMER);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        timerView = (TextView)findViewById(R.id.timerView);
        btnStart = (FloatingActionButton)findViewById(R.id.startFab);
        btnStop= (FloatingActionButton)findViewById(R.id.endFab);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        cl = Calendar.getInstance(TimeZone.getDefault());

        try {
            myDatabase = this.openOrCreateDatabase("Times", MODE_PRIVATE, null);

            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS times (name VARCHAR, start INTEGER, end INTEGER)");

            //myDatabase.execSQL("INSERT INTO times (name, age) VALUES ('Rob', '34')");
           // myDatabase.execSQL("INSERT INTO times (name, age) VALUES ('Trinity', '37')");

            Cursor c = myDatabase.rawQuery("SELECT * FROM times", null);
            int nameIndex = c.getColumnIndex("name");
            int startIndex = c.getColumnIndex("start");
            int endIndex = c.getColumnIndex("end");
            c.moveToFirst();

            while(c != null) {
                Log.i(TAG, "name: " + c.getString(nameIndex) + ", start: " +
                        Integer.toString(c.getInt(startIndex)) + ", end: " + Integer.toString(c.getInt(endIndex)));
                c.moveToNext();
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
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
