package com.eyerange.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;

/**
 * Class will be shown after login and will start detecting ibeacons (possibly
 * for the start of a course)
 * 
 * This service will continue until the game is ended
 * 
 * @author James Alfei
 */
public class MainActivity extends Activity {

	Context cont;
	ArrayAdapter<String> arrayAdapter;
	Bundle i;
	String token;
	Button b;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);

		setupComponents();
		cont = this;

		i = getIntent().getExtras();
		token = i.getString("token");
		b = (Button) findViewById(R.id.newGameButton);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cont.startActivity(new Intent(cont, NewGame.class));
			}
		});
	}

	private void setupComponents() {
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
}
