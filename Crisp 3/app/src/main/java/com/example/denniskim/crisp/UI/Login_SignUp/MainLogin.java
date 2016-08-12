package com.example.denniskim.crisp.UI.Login_SignUp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denniskim.crisp.Profile;
import com.example.denniskim.crisp.R;
import com.example.denniskim.crisp.UserSession;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


public class MainLogin extends AppCompatActivity {

    private LoginButton fbLoginButton;
    protected Button mEmailSignIn;
    protected Button mSignUpOption;
    //protected UserSession session;

    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();

        ActionBar bar=getSupportActionBar();
        bar.hide();

        getFbKeyHash("org.code2care.fbloginwithandroidsdk");
        setContentView(R.layout.activity_main_login);

        // initializing user's session
       // session = new UserSession(getApplicationContext());

        //this will check if the user is logged in. if not: redirected to MainLogin

       // session.checkLogin();
       // HashMap<String,String> user = session.getUserDetails();

        /*if(user.get(UserSession.KEY_NAME) != null)
        {
            Intent intent = new Intent(this, Profile.class);
            // these flags are added to make the login activity be the front of our app. so when you
            // press the back button, it goes to the home page of the phone
            // saying that login activity should be new task and the old task should be cleared
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } */

        fbLoginButton = (LoginButton)findViewById(R.id.fb_login_button);
        fbLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                /* getAccessToken retrieves access token
                   getToken - gets token in string type
                 */

                System.out.println("Facebook Login Successful!");
                System.out.println("Logged in user Details : ");
                System.out.println("--------------------------");
                System.out.println("User ID  : " + loginResult.getAccessToken().getUserId());
                System.out.println("Authentication Token : " + loginResult.getAccessToken().getToken());
                Toast.makeText(MainLogin.this, "Login Successful!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainLogin.this, "Login cancelled by user!", Toast.LENGTH_LONG).show();
                System.out.println("Facebook Login failed!!");
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(MainLogin.this, "Login unsuccessful!", Toast.LENGTH_LONG).show();
                System.out.println("Facebook Login failed!!");
            }
        });


        // function that takes you over to the email sign up page
        mEmailSignIn = (Button)findViewById(R.id.SignInButton);
        mEmailSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainLogin.this, LoginPage.class);
                startActivity(intent);
            }
        });

        // function that takes you to the login page
        mSignUpOption = (Button) findViewById(R.id.signUpOption);
        mSignUpOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainLogin.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

     private void getFbKeyHash(String packageName) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("YourKeyHash :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                System.out.println("YourKeyHash: "+ Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    /* method is to receive and handle the results */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       // mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

   /* @Override
    public void onBackPressed(){
        onFinsish();

    } */


}
