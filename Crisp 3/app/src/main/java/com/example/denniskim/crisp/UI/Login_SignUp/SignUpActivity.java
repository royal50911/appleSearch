package com.example.denniskim.crisp.UI.Login_SignUp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.denniskim.crisp.R;


public class SignUpActivity extends AppCompatActivity {

    private UserDB database;
    protected EditText mSignUpID;
    protected EditText mSignUpPassword;
    protected EditText mEmailSignUp;
    private EditText email;

    private String valid_email;

    /* singup button */
    protected Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_sign_up);
        ActionBar bar=getSupportActionBar();
        bar.hide();
        initilizeUI();

        mSignUpID = (EditText)findViewById(R.id.signUpID);
        mSignUpPassword = (EditText)findViewById(R.id.signUpPassword);
        mEmailSignUp = (EditText)findViewById(R.id.emailSignUp);
        database = new UserDB(this,null,null,1);

        mLoginButton = (Button)findViewById(R.id.loginButton);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mSignUpID.getText().toString();
                String password = mSignUpPassword.getText().toString();
                String email = mEmailSignUp.getText().toString();

                /* get rid of any white spaces */
                username = username.trim();
                password = password.trim();
                email = email.trim();


                /* unsuccessful sign up */
                if(username.isEmpty() || password.isEmpty() || email.isEmpty()){
                    // constructing the dialog object
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setMessage(R.string.signup_error_message);
                    builder.setTitle(R.string.signup_error_title);
                    builder.setPositiveButton(android.R.string.ok,null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                else if(password.length() <= 6)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setMessage(R.string.password_error_message);
                    builder.setTitle(R.string.password_error_title);
                    builder.setPositiveButton(android.R.string.ok,null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                /* successful sign up */
                else{
                    User user = new User(username,password);
                    database.addUser(user);
                    Toast.makeText(getApplicationContext(),
                            "Account Successfully Created ",
                            Toast.LENGTH_LONG).show();
                    Intent i = new Intent(SignUpActivity.this,MainLogin.class);
                    startActivity(i);
                }

            }
        });
    }


    private void initilizeUI() {
        // TODO Auto-generated method stub

        email = (EditText) findViewById(R.id.emailSignUp);

        email.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

                // TODO Auto-generated method stub
                Is_Valid_Email(email); // pass your EditText Obj here.
            }

            public void Is_Valid_Email(EditText edt) {
                if (edt.getText().toString() == null) {
                    edt.setError("Invalid Email Address");
                    valid_email = null;
                } else if (isEmailValid(edt.getText().toString()) == false) {
                    edt.setError("Invalid Email Address");
                    valid_email = null;
                } else {
                    valid_email = edt.getText().toString();
                }
            }

            boolean isEmailValid(CharSequence email) {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                        .matches();
            } // end of TextWatcher (email)
        });

    }



}
