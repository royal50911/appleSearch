package com.example.denniskim.crisp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.denniskim.crisp.UI.Login_SignUp.FavAppleDB;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class FavoriteActivity extends AppCompatActivity {


    private String currentTable;
    private ArrayList<HashMap<String, String>> reformedBundle;
    private String available= "This Apple is currently in Season";
    private String unavailable= "Unfortunately, it is NOT in Season";
    private ListView results;
    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    private Button add;
    private Button remove;
    private EditText removeText;
    private Cursor apples,names;
    private MyDatabase db;
    private FavAppleDB apple_name;
    private String curMonth,apple;
    private String [] months={"January","February","March","April","May",
            "June","July","August","September","October","November","December"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ActionBar bar=getSupportActionBar();
        bar.hide();

        Bundle appleData = getIntent().getExtras();
        if (appleData == null)
            return;
        currentTable = appleData.getString("username");
        Log.e("username",currentTable);

        removeText = (EditText)findViewById(R.id.removeText);


        Calendar fCalendar = Calendar.getInstance();
        final int currentmonth = fCalendar.get(Calendar.MONTH);
        curMonth = months[currentmonth];
        db = new MyDatabase(this);
        apple_name = new FavAppleDB(this);
        changeTheview();


        results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String, String> itemSong = (HashMap<String, String>)parent.getItemAtPosition(position);
                String apple = itemSong.get("line1");
                Intent myIntent = new Intent(FavoriteActivity.this, ApplesProfile.class);
                myIntent.putExtra("Apple name", apple);
                myIntent.putExtra("username", currentTable);
                startActivity(myIntent);
            }
        });


        // remove button
        remove = (Button)findViewById(R.id.remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                apple_name.delete(currentTable, removeText.getText().toString());
                removeText.setText("");
                changeTheview();
            }
        });

    }

    public void changeTheview(){

        results = (ListView)findViewById(R.id.apple_view);
        HashMap<String,String> item;
        list.clear();

        names = apple_name.getApple(currentTable);
        if(names.moveToFirst()==true) {
            while (true) {
                apple = names.getString(0);
                apples = db.getFav(apple, curMonth);
                if (apples.moveToFirst() == true){
                    item = new HashMap<String,String>();
                    item.put( "line1", apple);
                    item.put( "line2", available);
                    list.add( item );
                }
                else{
                    item = new HashMap<String,String>();
                    item.put( "line1", apple);
                    item.put( "line2", unavailable);
                    list.add( item );

                }

                if (names.moveToNext() == false)
                    break;
            }

        }
        SimpleAdapter sa = new SimpleAdapter(this, list,
                android.R.layout.two_line_list_item ,
                new String[] { "line1","line2" },
                new int[] {android.R.id.text1, android.R.id.text2});
        results.setAdapter(sa);

    }
}
