package com.tricloudcommunications.ce.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePushBroadcastReceiver;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    Boolean signUpModeActive = true;
    Button signUpButton;
    TextView changeSignUpTextView;
    EditText userNameEditText;
    EditText userPasswordEditText;
    EditText userEmailEditText;
    RelativeLayout contentMainRelativeLayout;
    ImageView instagramLogoImageView;


    public void signUp(View view){

        if (signUpModeActive) {

            if (userNameEditText.getText().toString().matches("") || userPasswordEditText.getText().toString().matches("") || userEmailEditText.getText().toString().matches("")) {

                Toast.makeText(MainActivity.this, "A username, password and email are required", Toast.LENGTH_LONG).show();

            } else {

                ParseUser parseUser = new ParseUser();
                parseUser.setUsername(userNameEditText.getText().toString());
                parseUser.setPassword(userPasswordEditText.getText().toString());
                parseUser.setEmail(userEmailEditText.getText().toString());

                parseUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {

                            Intent intent = new Intent(MainActivity.this, HomeViewActivity.class);
                            startActivity(intent);

                            Log.i("parseUser Event", "Sign sucessfull");

                        } else {

                            if (e.getMessage().equals("invalid session token")){

                                ParseUser.logOut();
                                Toast.makeText(MainActivity.this, "Opps, something went wrong. Please try again.", Toast.LENGTH_LONG).show();

                            }else {

                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                            //Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            //handleParseError(e);
                        }
                    }
                });

            }
        }else{

            ParseUser.logInInBackground(userNameEditText.getText().toString(), userPasswordEditText.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {

                    if (user != null){

                        Intent intent = new Intent(MainActivity.this, HomeViewActivity.class);
                        startActivity(intent);

                        Log.i("Login Event", "Successfully logged in");

                    }else {

                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            });
        }

    }

    public void handleParseError(Exception e){
        //Source: https://parseplatform.github.io//docs/android/guide/#handling-invalid-session-token-error
        //Source: http://parseplatform.github.io/docs/android/guide/#handling-invalid-session-token-error

        switch(e.getMessage()){

            case "invalid session token":
                ParseUser.logOut();
                userNameEditText.setText("");
                userPasswordEditText.setText("");

                break;
        }

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.changeSignUpTextView){

            if (signUpModeActive){
                signUpModeActive = false;
                userEmailEditText.setVisibility(View.INVISIBLE);
                signUpButton.setText("LOG IN");
                changeSignUpTextView.setText("or, Sign Up");

            }else{

                signUpModeActive = true;
                userEmailEditText.setVisibility(View.VISIBLE);
                signUpButton.setText("SIGN UP");
                changeSignUpTextView.setText("or, Log in");

            }

            //Log.i("AppInfo", "Changed Sign Up Mode");
        }else if (view.getId() == R.id.content_main || view.getId() == R.id.instagramLogoImageView){

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        }

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){

            signUp(v);
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        changeSignUpTextView = (TextView) findViewById(R.id.changeSignUpTextView);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        userNameEditText = (EditText) findViewById(R.id.userNameLogInEditText);
        userPasswordEditText = (EditText) findViewById(R.id.userPasswordLogInEditText);
        userEmailEditText = (EditText) findViewById(R.id.userEmailLoginEditText);
        contentMainRelativeLayout = (RelativeLayout) findViewById(R.id.content_main);
        instagramLogoImageView = (ImageView) findViewById(R.id.instagramLogoImageView);

        changeSignUpTextView.setOnClickListener(this);
        contentMainRelativeLayout.setOnClickListener(this);
        instagramLogoImageView.setOnClickListener(this);


        //Check if the user is already logged in.
        if (ParseUser.getCurrentUser() != null){

            Intent intent = new Intent(this, HomeViewActivity.class);
            startActivity(intent);

            Log.i("curentUser", "User is logged in " + ParseUser.getCurrentUser().getUsername());

        }else {

            if (signUpModeActive){

                userEmailEditText.setVisibility(View.VISIBLE);
                userEmailEditText.setOnKeyListener(this);

            }else {

                userPasswordEditText.setOnKeyListener(this);

            }

            Log.i("curentUser", "User is Not logged in ");

        }


        //Initialize Parse
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
