/**
 * 
 */
package com.eyerange.app;

import networkUtilities.NetworkCommsFeedback;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * This will display information on a particular hole its
 * par, wind direction and a general descriptor of the hole
 * eg. hints and tips.
 * 
 * @author Gwion
 */

public class HoleActivity extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.holes);
		setupInterfaceComponents();
		setupActionListeners();
	}

	private void setupActionListeners() {
		// add button action listeners here
	}

	private void setupInterfaceComponents() {
		// setup all interface components here
	}
}
