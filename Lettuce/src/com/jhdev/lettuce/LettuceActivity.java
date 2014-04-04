package com.jhdev.lettuce;

import java.io.ByteArrayOutputStream;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class LettuceActivity extends Activity {
	
	//Define stuff
	private List<ParseObject> Posts;	
	TextView textView;	
	Button uploadButton;	
	
	
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Parse tracking how many times your app is opened
		ParseAnalytics.trackAppOpened(getIntent());
		
		//Run a test Parse action
		//savePlace();		
		
		//Locate the button
		uploadButton = (Button) findViewById(R.id.uploadButton);
		//Capture button click
		uploadButton.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//			}			
			
			//dummy code that uploads an image
            public void onClick(View arg0) {
                // Locate the image in res > drawable-hdpi
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_launcher);
                // Convert it to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();
 
                // Create the ParseFile
                ParseFile file = new ParseFile("image.png", image);
                // Upload the image into Parse Cloud
                file.saveInBackground();
 
                // Create a New Class called "ImageUpload" in Parse
                ParseObject imgupload = new ParseObject("ImageUpload");
 
                // Create a column named "ImageName" and set the string
                imgupload.put("ImageName", "AndroidBegin Logo");
 
                // Create a column named "ImageFile" and insert the image
                imgupload.put("ImageFile", file);
 
                // Create the class and the columns
                imgupload.saveInBackground();
 
                // Show a simple toast message
                Toast.makeText(LettuceActivity.this, "Image Uploaded",
                        Toast.LENGTH_SHORT).show();
            }
			
			
			
		});
		
		
		
		
		
		
		
	}
	
	// Test parse action
	public void savePlace () {
		ParseObject post = new ParseObject("Post");
		post.put("place", "McDonalds");
		post.put("playerAddress", "Burger Road");
		post.put("placeCoordinates", "1.232,1.232");
		post.saveInBackground();
	}
	
	
}
