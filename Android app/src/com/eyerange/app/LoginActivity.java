/**
 * 
 */
package com.eyerange.app;

import networkUtilities.NetworkCommsFeedback;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import backgroundTasks.Login;

/**
 * This is the first screen the user will see. This class logs the user into the
 * server (for authentication) and then switches to the main activity
 * 
 * this will eventually need to check if the user is already logged in and go
 * straight to the main activity
 * 
 * @author James << FUCKING BENDER
 */
public class LoginActivity extends Activity implements NetworkCommsFeedback {

	EditText user, pass;
	Button submit;
	TextView notUser;
	NetworkCommsFeedback feedback;
	Context cont;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		feedback = this;
		cont = this;

		setupInterfaceComponents();
		setupActionListeners();
	}

	private void setupActionListeners() {
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Login(user.getText().toString(), pass.getText().toString(),
						feedback).execute();
			}
		});

		user.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i2,
					int i3) {
				// checks that the string entered for the first name, is not
				// empty, or containing whitespace
				// if this is the case, enable button
				submit.setEnabled(!user.getText().toString().trim().isEmpty());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});

		notUser.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(cont, RegisterUser.class));
			}
		});
	}

	private void setupInterfaceComponents() {
		user = (EditText) findViewById(R.id.username);
		pass = (EditText) findViewById(R.id.password);
		submit = (Button) findViewById(R.id.submitButton);
		notUser = (TextView) findViewById(R.id.notamember);
	}

	@Override
	public void onLoginComplete(boolean success, String token) {
		// when logged in, go to Main activity
		// new CheckLogin(token, feedback).execute();
		if (success) {
			Intent i = new Intent(this, MainActivity.class);
			Bundle b = new Bundle();
			b.putString("token", token);
			i.putExtras(b);
			startActivity(i);
			finish();
		} else {
			Toast.makeText(this, "Incorrect login details!", Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	public void onLogoutComplete(boolean success) {
	}

	@Override
	public void onCheckComplete(String status) {
		Toast.makeText(this, status, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onGotHoleDetails(String holeID, String distanceToPin, String par) {
	}
}
