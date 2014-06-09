/**
 * 
 */
package com.eyerange.app;

import networkUtilities.NetworkCommsFeedback;
import android.app.Activity;
import android.os.Bundle;

/**
 * @author James
 * 
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
		// TODO Auto-generated method stub

	}

	private void setupInterfaceComponents() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoginComplete(boolean success, boolean honbuUser, String token) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLogoutComplete(boolean success) {
		// TODO Auto-generated method stub

	}
}
