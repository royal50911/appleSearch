package com.example.denniskim.crisp;


import android.content.Intent;
import android.database.Cursor;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

public class Apple_Season_Activity extends AppCompatActivity {

    private String [] months={"January","February","March","April","May",
                        "June","July","August","September","October","November","December"};
    private String curMonth, currentTable;
    private Cursor apples;
    private MyDatabase db;
    private ListView results;
    ArrayList<String> arrayApples;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apple_season);
        ActionBar bar=getSupportActionBar();
        bar.hide();

        Bundle appleData = getIntent().getExtras();
        if (appleData == null)
            return;
        currentTable = appleData.getString("username");
        Log.e("username", currentTable);


        Calendar fCalendar = Calendar.getInstance();
        //Example: Get current day of the month
      //  int CurrentDay = fCalendar.get(Calendar.DAY_OF_MONTH);

        //Example: Get current month
        int currentmonth = fCalendar.get(Calendar.MONTH);

     //   int Currentyear = fCalendar.get(Calendar.YEAR);
        results = (ListView)findViewById(R.id.listView_apples);
        arrayApples = new ArrayList<String>();
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayApples);
        results.setAdapter(adapter);
        curMonth = months[currentmonth];
        db = new MyDatabase(this);
        apples = db.getApple(curMonth);
        while (true) {
            arrayApples.add(apples.getString(0));
            if (apples.moveToNext() == false)
                break;
        }

        //this updates the listView
        adapter.notifyDataSetChanged();
        apples.close();

        results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String apple = String.valueOf(parent.getItemAtPosition(position));
                Intent myIntent = new Intent(Apple_Season_Activity.this, ApplesProfile.class);
                myIntent.putExtra("Apple name", apple);
                myIntent.putExtra("username", currentTable);
                startActivity(myIntent);
            }
        });


    }


}
