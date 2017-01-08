package com.tricloudcommunications.ce.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeViewActivity extends AppCompatActivity {

    ListView usersLV;
    ArrayList<String> userArrayList;
    ArrayAdapter arrayAdapter;

    public void logOutNow(View view){

        ParseUser.logOut();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usersLV = (ListView) findViewById(R.id.usersListView);
        userArrayList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, userArrayList);
        usersLV.setAdapter(arrayAdapter);

        //userArrayList.add("James");
        //userArrayList.add(ParseUser.getCurrentUser().getUsername());

        ParseQuery<ParseUser> queryUsers = ParseUser.getQuery();
        queryUsers.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                if (e == null){

                    if (objects.size() > 0){

                        for (ParseObject users : objects){

                            userArrayList.add(users.getString("username"));
                            arrayAdapter.notifyDataSetChanged();

                            Log.i("FindInBackGround IO ", users.getString("username"));
                        }


                    }else{

                        userArrayList.add("No Users Found");
                        arrayAdapter.notifyDataSetChanged();

                        Log.i("FindInBackGround IO ", objects.toString());

                    }

                }else {

                    Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home_view_action_settings) {

            ParseUser.logOut();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
