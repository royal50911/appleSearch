package com.example.denniskim.crisp;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denniskim.crisp.UI.Login_SignUp.FavAppleDB;


public class ApplesProfile extends AppCompatActivity {

    TextView apple,uses,origin,available,date;
    private MyDatabase db;
    private FavAppleDB appleDB;
    private Cursor mDB;
    String currentApple, currentTable;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apples_profile);
        ActionBar bar=getSupportActionBar();
        bar.hide();

        appleDB = new FavAppleDB(this);
        apple = (TextView)findViewById(R.id.apple);
        uses = (TextView)findViewById(R.id.uses);
        origin = (TextView)findViewById(R.id.origin);
        available = (TextView)findViewById(R.id.available);
        date = (TextView)findViewById(R.id.date);
        image = (ImageView)findViewById(R.id.image);
        db = new MyDatabase(this);

        Bundle appleData = getIntent().getExtras();
        if (appleData == null)
            return;
        currentApple = appleData.getString("Apple name");
        currentTable = appleData.getString("username");
        Log.e("apple name",currentApple);
        Log.e("username",currentTable);
        apple.setText(currentApple);
        String appleLower = currentApple.toLowerCase();
        String newApple = appleLower.replaceAll("\\s", "");
        String uri = "drawable/"+ newApple;
        Log.e("NAME", newApple);
        int resID = getResources().getIdentifier(uri, null, getPackageName());
        Drawable appleImage = getResources().getDrawable(resID);
        image.setImageDrawable(appleImage);

        Cursor c = db.getApple(currentApple);
        String one = c.getString(1);
        String two = c.getString(2);
        String three = c.getString(5);
        String four = c.getString(4);

        uses.setText(one);
        origin.setText(two);
        available.setText(three);
        date.setText(four);

        ImageView btnNew = (ImageView) findViewById(R.id.favBtn);
        btnNew.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mDB = appleDB.checkApple(currentTable,currentApple);
                if(mDB.moveToFirst()==false)
                    appleDB.insert(currentTable,currentApple);
                Toast.makeText(getApplicationContext(),
                        "Your apple has been added to your basket",
                        Toast.LENGTH_LONG).show();
            }

        });
    }

}

