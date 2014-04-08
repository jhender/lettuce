package com.jhdev.lettuce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v4.app.NavUtils;
import android.view.MenuItem;
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
        TextView title = (TextView) findViewById(R.id.textView1);
        title.setText(objectID);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
//        case android.R.id.home:
//            NavUtils.navigateUpFromSameTask(this);
//            return true;
            
//        case R.id.take_picture:
//            captureImage();
//            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
}