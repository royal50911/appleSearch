package com.example.denniskim.crisp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.denniskim.crisp.R;
import com.example.denniskim.crisp.UI.Login_SignUp.MainLogin;
import com.example.denniskim.crisp.UI.Login_SignUp.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class Profile extends AppCompatActivity {



//    private Button mSearch;
//    private Button inSeason;
//    private Button basketButton;
    private String currentTable;

    private String [] months={"January","February","March","April","May",
            "June","July","August","September","October","November","December"};


    UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar bar=getSupportActionBar();
        bar.hide();



        setContentView(R.layout.activity_profile);

        // get notifications from the updated months
        DateFormat dateFormat = new SimpleDateFormat("MMMM");
        Date date = new Date();
        //Log.d("Month",dateFormat.format(date));
        Calendar mCalendar = Calendar.getInstance();
        int currentMonth = mCalendar.get(Calendar.MONTH);
        String curMonth = months[currentMonth];
        // sends the notification for when new apples are in season
        if(dateFormat.format(date) == curMonth){
            notificationSetter();
        }
        // initializing user's session
        session = new UserSession(getApplicationContext());

        //this will check if the user is logged in. if not: redirected to MainLogin

        session.checkLogin();

        HashMap<String,String> user = session.getUserDetails();

        if(user.get(UserSession.KEY_NAME) == null)
        {
            Intent intent = new Intent(this, MainLogin.class);
            // these flags are added to make the login activity be the front of our app. so when you
            // press the back button, it goes to the home page of the phone
            // saying that login activity should be new task and the old task should be cleared
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }


//        Bundle appleData = getIntent().getExtras();
//        if (appleData == null)
//            return;
        //currentTable = appleData.getString("username");
        //Log.e("current", currentTable);



    }

    public void notificationSetter(){

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context
                .NOTIFICATION_SERVICE);

        int notificationId = 001;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("New Apples that are in season!")
                        .setContentText("Check out the new apples that are in season");

        Intent resultIntent = new Intent(this, Apple_Season_Activity.class);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT
                );

        notificationManager.notify(notificationId, mBuilder.build());


    }
    public void logOutButton(View l){
        session.logoutUser();
    }

    public void inSeasonSearch(View w){
        Intent intent = new Intent(Profile.this, Apple_Season_Activity.class);
        HashMap<String,String> user = session.getUserDetails();
        String users = user.get(UserSession.KEY_NAME);
        Log.d("this is the ", users);
        intent.putExtra("username", users);

        startActivity(intent);
    }

    public void searchApples(View view){
        Intent intent = new Intent(Profile.this, SearchActivity.class);
        HashMap<String,String> user = session.getUserDetails();
        String users = user.get(UserSession.KEY_NAME);
        Log.d("this is the ", users);
        intent.putExtra("username", users);

        startActivity(intent);
    }

    public void myFavorites(View t){
        Intent intent = new Intent(Profile.this, FavoriteActivity.class);
        HashMap<String,String> user = session.getUserDetails();
        String users = user.get(UserSession.KEY_NAME);
        Log.d("this is the ", users);
        intent.putExtra("username", users);

        startActivity(intent);
    }



}
