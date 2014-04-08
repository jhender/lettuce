package com.jhdev.lettuce;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v4.app.NavUtils;
//import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
 
public class SingleItemView extends Activity {
 
    String photo;
    String objectID;
    ImageLoader imageLoader = new ImageLoader(this);
    
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.singleitemview);
        getActionBar().setDisplayHomeAsUpEnabled(true);
 
        Intent i = getIntent();
        // Get the intent from ListViewAdapter
        photo = i.getStringExtra("photo");
        objectID = i.getStringExtra("objectID");
 
        // Locate the ImageView in singleitemview.xml
        ImageView imgphoto = (ImageView) findViewById(R.id.photo);
 
        // Load image into the ImageView
        imageLoader.DisplayImage(photo, imgphoto);
        
        // Local TextView in singleitemview.xml and set text
        final TextView title = (TextView) findViewById(R.id.textView1);
        final TextView description = (TextView) findViewById(R.id.textView2);
        
        //title.setText(objectID);
        
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ImageUpload");
        query.getInBackground(objectID, new GetCallback<ParseObject>() {
          public void done(ParseObject object, ParseException e) {
            if (e == null) {
              // retrieved object.
            	title.setText(object.getString("Title"));
            	description.setText(object.getString("Description"));

            } else {
              // something went wrong
            }
          }
        });
        
        
        
        
        
    }
    
    
    
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle presses on the action bar items
//        switch (item.getItemId()) {
////        case android.R.id.home:
////            NavUtils.navigateUpFromSameTask(this);
////            return true;
//            
////        case R.id.take_picture:
////            captureImage();
////            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//    
    
}