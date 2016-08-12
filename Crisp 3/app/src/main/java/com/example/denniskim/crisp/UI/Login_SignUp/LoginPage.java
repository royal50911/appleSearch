package com.example.denniskim.crisp.UI.Login_SignUp;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.denniskim.crisp.ApplesProfile;
import com.example.denniskim.crisp.FavoriteActivity;
import com.example.denniskim.crisp.Profile;
import com.example.denniskim.crisp.R;
import com.example.denniskim.crisp.UserSession;


public class LoginPage extends AppCompatActivity {

    protected EditText mLoginText;
    protected EditText mPasswordText;
    protected Button mLoginButton;
    protected UserDB database;
    private FavAppleDB appleDB;
    private Cursor mDB;
    public UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        ActionBar bar=getSupportActionBar();
        bar.hide();

        session = new UserSession(getApplicationContext());

        appleDB = new FavAppleDB(this);


        mLoginText = (EditText)findViewById(R.id.loginText);
        mPasswordText = (EditText)findViewById(R.id.passwordText);
        mLoginButton = (Button)findViewById(R.id.loginButton);
        database = new UserDB(this,null,null,1);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* TODO implement the user info pattern */
                String username = mLoginText.getText().toString();
                String password = mPasswordText.getText().toString();
                String correctPass = database.getUser(username);

                username = username.trim();
                password = password.trim();

                if(username.isEmpty() || password.isEmpty())
                {
                    // constructing the dialog object
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginPage.this);
                    // parameter is using the initalized string variable in strings.xml
                    builder.setMessage(R.string.login_error_message);
                    builder.setTitle(R.string.login_error_title);
                    // type of button to be invoked when the user sees the dialog and wants it to go away
                    // setting the listener(2nd parameter) to null means that you dont want the button to do anything
                    builder.setPositiveButton(android.R.string.ok,null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                else if(correctPass == "NOT EXIST") {
                    Toast.makeText(getApplicationContext(), "Username Not Found.", Toast.LENGTH_LONG).show();
                }

                else if(!correctPass.equals(password)) {
                    Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();
                }

                else if(correctPass.equals(password)) {
                    // storing the user's session
                    session.createLoginSession(username);


                    Intent intent = new Intent(LoginPage.this, Profile.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Log.e("username", username);
                    mDB = appleDB.checkTable(username);
                    if(mDB.moveToFirst()==false) {
                        appleDB.createTable(username);
                    }
                    intent.putExtra("username", username);
                    startActivity(intent);
                }



            }
        });
    }
}
