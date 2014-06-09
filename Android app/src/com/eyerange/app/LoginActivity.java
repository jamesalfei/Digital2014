/**
 * 
 */
package com.eyerange.app;

import networkUtilities.NetworkCommsFeedback;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * This is the first screen the user will see. This class logs the user into the
 * server (for authentication) and then switches to the main activity
 * 
 * this will eventually need to check if the user is already logged in and go
 * straight to the main activity
 * 
 * @author James
 */
public class LoginActivity extends Activity implements NetworkCommsFeedback {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		setupInterfaceComponents();
		setupActionListeners();
	}

	private void setupActionListeners() {
		// add button action listeners here
	}

	private void setupInterfaceComponents() {
		// setup all interface components here
	}

	@Override
	public void onLoginComplete(boolean success, boolean honbuUser, String token) {
		// when logged in, go to Main activity
		startActivity(new Intent(this, MainActivity.class));
	}

	@Override
	public void onLogoutComplete(boolean success) {
		// TODO Auto-generated method stub
	}
}
