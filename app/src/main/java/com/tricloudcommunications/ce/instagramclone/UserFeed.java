package com.tricloudcommunications.ce.instagramclone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.List;

public class UserFeed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        Intent intent = getIntent();
        final String activeUsername = intent.getStringExtra("username");
        setTitle(activeUsername +"'s Feed");

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Image");
        query.whereEqualTo("username", activeUsername);
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null){

                    if (objects.size() > 0){

                        for (ParseObject object : objects){

                            ParseFile file = (ParseFile) object.get("image");
                            file.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {

                                    if (e == null && data != null){

                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                        Bitmap resizeBitmap = Bitmap.createScaledBitmap(bitmap, 600, 730, true);
                                        ImageView imageView = new ImageView(getApplicationContext());
                                        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT
                                        ));

                                        //See Soource on getDrawable: http://stackoverflow.com/a/38525429/5047666
                                        //imageView.setImageDrawable(getResources().getDrawable(R.drawable.instagramlogo, null));
                                        imageView.setImageBitmap(resizeBitmap);

                                        linearLayout.addView(imageView);
                                    }

                                }
                            });
                        }
                    }else{

                        Toast.makeText(UserFeed.this, activeUsername + " does not have any images", Toast.LENGTH_LONG).show();

                    }

                }else{

                    Log.i("User Feed Error", e.toString());

                }
            }
        });






    }

}
