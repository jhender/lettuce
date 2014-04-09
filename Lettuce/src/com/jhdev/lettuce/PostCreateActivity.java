package com.jhdev.lettuce;

import java.io.ByteArrayOutputStream;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class PostCreateActivity extends Activity {

	private Uri fileUri;
	private ImageView imgPreview;
	Button btnSave;
	String stringTitle;
	String stringDescription;
	ParseFile file;
	String imageFileName;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_create);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        //Receive image from main activity
        fileUri = getIntent().getData();
        imageFileName = getIntent().getStringExtra("filename");
        //Toast.makeText(PostCreateActivity.this, "Image: " + imageFileName, Toast.LENGTH_LONG).show();
        
        //Set image into the preview box
        imgPreview = (ImageView) findViewById(R.id.imgPreview);
        imgPreview.setImageURI(fileUri);
        
        btnSave = (Button) findViewById(R.id.saveButton1);
        final EditText editTextTitle = (EditText) findViewById(R.id.editTextTitle);        
        final EditText editTextDescription = (EditText) findViewById(R.id.editTextDescription);        
        
        
		//code that uploads an image to Parse immediately.
        //if user presses back or cancels, file is still on server but not linked to a imageUpload.
    	
        // bitmap factory
        BitmapFactory.Options options = new BitmapFactory.Options();

        // down-sizing image as it throws OutOfMemory Exception for larger
        // images
        options.inSampleSize = 4;
        
        Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
          
		// Convert it to byte
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		// Compress image to lower quality scale 1 - 100
		bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
		byte[] image = stream.toByteArray();
		
		// Create the ParseFile
		file = new ParseFile(imageFileName, image);
		// Upload the image into Parse Cloud
		file.saveInBackground();
        

		btnSave.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View arg0) {
		        stringTitle = editTextTitle.getText().toString();
		        stringDescription = editTextDescription.getText().toString();
				savePost();
			}				
		});		
	}
	
	private void savePost(){

      // Create a New Class called "Photo" in Parse
      ParseObject imgupload = new ParseObject("ImageUpload");

      // Create a column named "ImageName" and set the string          
      imgupload.put("ImageName", imageFileName);

      // Create a column named "ImageFile" and insert the image
      imgupload.put("Photo", file);

      imgupload.put("Title", stringTitle);
      imgupload.put("Description", stringDescription);
      
      //set user who created this. TODO add check that user is logged in.
      imgupload.put("createdBy", ParseUser.getCurrentUser());
      
      // Create the class and the columns
      imgupload.saveInBackground();

      // Show a simple toast message
      Toast.makeText(PostCreateActivity.this, "Image Uploaded",
              Toast.LENGTH_SHORT).show();
      
      Intent returnIntent = new Intent();
      //returnIntent.putExtra("result",result);
      setResult(RESULT_OK,returnIntent);     
      finish();
	}


}
