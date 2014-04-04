package com.jhdev.lettuce;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.ParseFile;
import com.parse.ParseObject;

public class LettuceActivity extends Activity {
	
	//Define stuff
	private List<ParseObject> Posts;	
	TextView textView;	
	Button uploadButton;	
	
	// Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
 
    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Lettuce";
 
    private Uri fileUri; // file url to store image/video
    private ImageView imgPreview;	
	
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Parse tracking how many times your app is opened
		ParseAnalytics.trackAppOpened(getIntent());
		
		//Run a test Parse action
		//savePlace();

		//Show camera image taken
		imgPreview = (ImageView) findViewById(R.id.imgPreview);		
		
		//Locate the button
		uploadButton = (Button) findViewById(R.id.uploadButton);
		//Capture button click
		uploadButton.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View arg0) {
				captureImage();
			}				
		});		
	} //end OnCreate
	
    /**
     * Capturing Camera Image will launch camera app request image capture
     */
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
 
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
 
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
 
        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }
    
    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
 
        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }
 
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
 
        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }
    
    /**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // successfully captured the image
                // display it in image view
                previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }         
    }
 
    /**
     * Display image from a path to ImageView
     */
    private void previewCapturedImage() {
        try {
            // hide video preview 
            imgPreview.setVisibility(View.VISIBLE);
 
            // bitmap factory
            BitmapFactory.Options options = new BitmapFactory.Options();
 
            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;
 
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);
 
            imgPreview.setImageBitmap(bitmap);
            
            // upload this file
            uploadImage();
            
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
 


     
    /**
     * ------------ Helper Methods ---------------------- 
     * */
 
    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
 
    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {
 
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
 
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
 
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
 
        return mediaFile;
    }
   /**
    *  ----------------END HELPER-------------------------
    **/
    
    
    public void uploadImage () {
		//code that uploads an image to Parse
    	
        // bitmap factory
        BitmapFactory.Options options = new BitmapFactory.Options();

        // downsizing image as it throws OutOfMemory Exception for larger
        // images
        options.inSampleSize = 8;
        
        Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);

//        imgPreview.setImageBitmap(bitmap);    	    
          // Locate the image in res > drawable-hdpi
//          Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
          
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
	
	
	// Test parse action
	public void savePlace () {
		ParseObject post = new ParseObject("Post");
		post.put("place", "McDonalds");
		post.put("playerAddress", "Burger Road");
		post.put("placeCoordinates", "1.232,1.232");
		post.saveInBackground();
	}
	
	
}
