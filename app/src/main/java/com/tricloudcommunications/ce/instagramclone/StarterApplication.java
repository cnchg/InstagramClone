package com.tricloudcommunications.ce.instagramclone;

/**
 * Created by CE on 1/4/2017.
 */

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class StarterApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("udemyInstagramApp")
                .clientKey("mila12130906")
                .server("https://ovh.tricloudcommunications.com:1338/parseInstagram/")
                .build()
        );

        /**
         ParseObject object = new ParseObject("ExampleObject");
         object.put("myNumber", "123");
         object.put("myString", "rob");
         object.saveInBackground(new SaveCallback () {
        @Override
        public void done(ParseException ex) {
        if (ex == null) {
        Log.i("Parse Result", "Successful!");
        } else {
        Log.i("Parse Result", "Failed" + ex.toString());
        }
        }
        });
         */


        //ParseUser.enableAutomaticUser(); //Used if you want the app to automatically create a new user. In most cases we do not, so we will comment this out.

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);


        //Initialize Parse Push notifications
        ParseInstallation.getCurrentInstallation().saveInBackground();

    }


}
