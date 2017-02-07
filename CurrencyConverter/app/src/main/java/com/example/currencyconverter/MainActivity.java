package com.example.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void convertToKRW(View view) {
        EditText dollorField = (EditText) findViewById(R.id.dollorText);
        Integer convertedVal = Integer.parseInt(dollorField.getText().toString()) * 1141;

        Toast.makeText(MainActivity.this, convertedVal.toString(), Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
