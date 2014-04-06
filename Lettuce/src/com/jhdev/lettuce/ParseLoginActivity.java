package com.jhdev.lettuce;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Build;

public class ParseLoginActivity extends Activity {
	
	Button btn_LoginIn = null;
	Button btn_SignUp = null;
	Button btn_ForgetPass = null;
	private EditText mUserNameEditText;
	private EditText mPasswordEditText;

	// flag for Internet connection status
	Boolean isInternetPresent = false;
	// Connection detector class
	ConnectionDetector cd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parselogin);

//		if (savedInstanceState == null) {
//			getFragmentManager().beginTransaction()
//				.add(R.id.container, new PlaceholderFragment()).commit();
//		}
		
		// creating connection detector class instance
		cd = new ConnectionDetector(getApplicationContext());

		btn_LoginIn = (Button) findViewById(R.id.btn_login);
		btn_SignUp = (Button) findViewById(R.id.btn_signup);
		btn_ForgetPass = (Button) findViewById(R.id.btn_ForgetPass);
		mUserNameEditText = (EditText) findViewById(R.id.username);
		mPasswordEditText = (EditText) findViewById(R.id.password);

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.parse_login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_parselogin,
					container, false);
			
			//setContentView(R.layout.activity_login);

//			mUsernameField = (EditText) findViewById(R.id.login_username);
//			mPasswordField = (EditText) findViewById(R.id.login_password);
//			mErrorField = (TextView) findViewById(R.id.error_messages);

			return rootView;

		}
	}

}
