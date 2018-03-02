package com.example.widgets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    //dates
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();
    DatePicker datePicker;
    TimePicker mTimePicker;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar calendrier = Calendar.getInstance();

        int jour = calendrier.get(Calendar.DAY_OF_MONTH);

        tv = findViewById(R.id.tv);

        tv.setText(dateFormat.format(date));


        datePicker = findViewById(R.id.datePicker);

        datePicker.updateDate(datePicker.getYear(),3,11);


        mTimePicker = (TimePicker) findViewById(R.id.timePicker);
        mTimePicker.setIs24HourView(true);
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(MainActivity.this, "C'est vous qui voyez, il est donc " + String.valueOf(hourOfDay) + ":" + String.valueOf(minute), Toast.LENGTH_SHORT).show();
            }
        });




    }
}
