package com.tricloudcommunications.ce.instagramclone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Boolean signUpModeActive = true;
    Button signUpButton;
    TextView changeSignUpTextView;

    public void signUp(View view){

        EditText userNameEditText = (EditText) findViewById(R.id.userNameLogInEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.userPasswordLogInEditText);

        if (userNameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")){

            Toast.makeText(MainActivity.this, "A username and password is required", Toast.LENGTH_LONG).show();

        }else {

            ParseUser parseUser = new ParseUser();
            parseUser.setUsername(userNameEditText.getText().toString());
            parseUser.setPassword(passwordEditText.getText().toString());

            parseUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null){

                        Log.i("parseUser Event", "Sign sucessfull");

                    }else{

                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
            });

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        changeSignUpTextView = (TextView) findViewById(R.id.changeSignUpTextView);
        signUpButton = (Button) findViewById(R.id.signUpButton);

        changeSignUpTextView.setOnClickListener(this);


        /**
        //Check if the user is already logged in.
        if (ParseUser.getCurrentUser() != null){

            Log.i("curentUser", "User is logged in " + ParseUser.getCurrentUser().getUsername());

        }else {

            Log.i("curentUser", "User is Not logged in ");

        }
         **/

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

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.changeSignUpTextView){

            if (signUpModeActive){
                signUpModeActive = false;
                signUpButton.setText("LOG IN");
                changeSignUpTextView.setText("or, Sign Up");

            }else{

                signUpModeActive = true;
                signUpButton.setText("SIGN UP");
                changeSignUpTextView.setText("or, Log in");

            }

            //Log.i("AppInfo", "Changed Sign Up Mode");
        }

    }
}
