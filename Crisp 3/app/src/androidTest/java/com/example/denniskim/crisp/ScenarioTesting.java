package com.example.denniskim.crisp;
import com.example.denniskim.crisp.MyDatabase;
import com.example.denniskim.crisp.Profile;
import com.robotium.solo.Solo;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.InstrumentationTestCase;
import android.util.Log;
import android.widget.SearchView;
import android.widget.TextView;



public class ScenarioTesting extends ActivityInstrumentationTestCase2<Profile>
{
    private Solo solo;
    private UserSession Session;

    public ScenarioTesting()
    {
        super(Profile.class);
    }

    public void setUp() throws Exception
    {
        solo=new Solo(getInstrumentation(), getActivity());
        Session = new UserSession(getActivity());
    }

    public void tearDown() throws Exception
    {
        solo.finishOpenedActivities();
    }


    //GIVEN: I am on the home page and signed in
    //WHEN: I click on the "Apples in Season" button
    //THEN: I can see the apples that are in season
    public void testScenario1() throws Exception
    {
        if(Session.isLoggedIn())
        {
            solo.unlockScreen();
            solo.clickOnButton("In Season");
            solo.assertCurrentActivity("Expected In Season Activity", "Apple_Season_Activity");
            return;
        }

        else
        {
            solo.unlockScreen();
            solo.clickOnButton("Sign Up!");
            solo.typeText(0, "username");
            solo.typeText(1, "password");
            solo.typeText(2, "armonsafai@yahoo.com");
            solo.clickOnButton("SIGN UP");
            solo.clickOnButton("Log In with Account");
            solo.typeText(0, "username");
            solo.typeText(1, "password");
            solo.clickOnButton("SIGN IN");
            solo.clickOnButton("In Season");
            solo.assertCurrentActivity("Expected In Season Activity", "Apple_Season_Activity");
        }


    }


    //GIVEN: I am on the home page and signed in
    //WHEN: I click on the "Search Apples" button
    //WHEN: I click on the bramley apple
    //THEN: I am directed to bramley's apple profile
    public void testScenario2() throws Exception
    {
        if(Session.isLoggedIn())
        {
            solo.unlockScreen();
            solo.clickOnButton("Search");
            solo.clickLongInList(1);
            solo.assertCurrentActivity("Expected Apple Profile Activity", "ApplesProfile");
            return;
        }

        else
        {
            solo.unlockScreen();
            solo.clickOnButton("Sign Up!");
            solo.typeText(0, "username");
            solo.typeText(1, "password");
            solo.typeText(2, "armonsafai@yahoo.com");
            solo.clickOnButton("SIGN UP");
            solo.clickOnButton("Log In with Account");
            solo.typeText(0, "username");
            solo.typeText(1, "password");
            solo.clickOnButton("SIGN IN");
            solo.clickOnButton("Search");
            solo.clickLongInList(1);
            solo.assertCurrentActivity("Expected Apple Profile Activity", "ApplesProfile");
        }
    }


    //GIVEN: I am on the home page and signed in
    //WHEN: I click on the "My Basket" button
    //THEN: I am directed to the page where I can see my favorite apples
    public void testScenario3() throws Exception
    {
        if(Session.isLoggedIn())
        {
            solo.unlockScreen();
            solo.clickOnButton("My Basket");
            solo.assertCurrentActivity("Expected Basket Activity", "FavoriteActivity");
            return;
        }

        else
        {
            solo.unlockScreen();
            solo.clickOnButton("Sign Up!");
            solo.typeText(0, "username");
            solo.typeText(1, "password");
            solo.typeText(2, "armonsafai@yahoo.com");
            solo.clickOnButton("SIGN UP");
            solo.clickOnButton("Log In with Account");
            solo.typeText(0, "username");
            solo.typeText(1, "password");
            solo.clickOnButton("SIGN IN");
            solo.clickOnButton("My Basket");
            solo.assertCurrentActivity("Expected Basket Activity", "FavoriteActivity");
        }
    }

}