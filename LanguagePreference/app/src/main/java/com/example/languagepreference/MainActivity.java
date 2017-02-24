package com.example.languagepreference;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    final String TAG = "LanguagePreference";
    SharedPreferences sharedPreferences;
    TextView myTextView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.english:
                Log.i(TAG, "English selected.");
                selectLanguage(1);
                return true;
            case R.id.spanish:
                Log.i(TAG, "Spanish selected.");
                selectLanguage(2);
                return true;
            default:
                return false;
        }
    }

    public void selectLanguage(int language) {
        String stored_language = sharedPreferences.getString("language", "");

        switch (language) {
            case 1:
                if(stored_language.compareTo("English") != 0) {//if not English
                    sharedPreferences.edit().putString("language", "English").apply();
                    myTextView.setText("English");
                    Log.i(TAG, "Changing language to English");
                }
                break;
            case 2:
                if(stored_language.compareTo("Spanish") != 0) { //if not English
                    sharedPreferences.edit().putString("language", "Spanish").apply();
                    myTextView.setText("Spanish");
                    Log.i(TAG, "Changing language to Spanish");
                }
                break;
            default:
                break;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myTextView = (TextView) findViewById(R.id.selectedLanguageText);

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("LanguagePreference")
                .setMessage("Choose Language.")
                .setPositiveButton("English", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG, "Selected English.");
                        selectLanguage(1);
                    }
                })
                .setNegativeButton("Spanish", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG, "Selected Spanish.");
                        selectLanguage(2);
                    }
                })
                .show();

        sharedPreferences = this.getSharedPreferences("com.example.languagepreference", Context.MODE_PRIVATE);

        String stored_language = sharedPreferences.getString("language", "");

        myTextView.setText(stored_language);
    }
}
