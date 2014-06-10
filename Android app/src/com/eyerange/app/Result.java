package com.eyerange.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import utilities.Constants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.radiusnetworks.ibeacon.IBeacon;
import com.radiusnetworks.ibeacon.IBeaconConsumer;
import com.radiusnetworks.ibeacon.IBeaconManager;
import com.radiusnetworks.ibeacon.RangeNotifier;
import com.radiusnetworks.ibeacon.Region;

public class Result extends Activity implements IBeaconConsumer {

	Bundle i;
	String token, major, minor;
	EditText strokes;
	Button submit;
	TextView info;
	private final IBeaconManager iBeaconManager = IBeaconManager
			.getInstanceForApplication(this);
	private ArrayList<IBeacon> devices;
	ArrayList<String> deviceNames;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		i = getIntent().getExtras();
		major = i.getString("major");
		minor = i.getString("minor");

		setupComponents();
		addActionListeners();

		iBeaconManager.bind(this);
		devices = new ArrayList<IBeacon>();
		deviceNames = new ArrayList<String>();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		iBeaconManager.unBind(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		iBeaconManager.unBind(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		iBeaconManager.bind(this);
	}

	private void addActionListeners() {
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				info.setText("Great! Now move onto hole 2! \n THIS IS THE END OF THE DEMO.");
				submit.setVisibility(0);
				strokes.setVisibility(0);
			}
		});

		strokes.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i2,
					int i3) {
				// checks that the string entered for the first name, is not
				// empty, or containing whitespace
				// if this is the case, enable button
				submit.setEnabled(!strokes.getText().toString().trim()
						.isEmpty());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});
	}

	private void setupComponents() {
		strokes = (EditText) findViewById(R.id.score);
		submit = (Button) findViewById(R.id.submitButton);
		info = (TextView) findViewById(R.id.info);
	}

	@Override
	public void onIBeaconServiceConnect() {
		iBeaconManager.setRangeNotifier(new RangeNotifier() {
			@Override
			public void didRangeBeaconsInRegion(Collection<IBeacon> arg0,
					Region arg1) {

				devices.clear();
				deviceNames.clear();

				if (arg0.size() > 0) {
					Iterator<IBeacon> itt = arg0.iterator();
					while (itt.hasNext()) {
						IBeacon device = itt.next();
						if (!devices.contains(device)) {
							devices.add(device);
						}
					}

					checkDevices(devices);
				}
			}
		});

		try {
			iBeaconManager.startRangingBeaconsInRegion(new Region(
					"myRangingUniqueId", null, null, null));
		} catch (RemoteException e) {
			Log.e("iBeacon Service", "Unable to start ranging service.");
		}
	}

	protected void checkDevices(ArrayList<IBeacon> devices) {
		for (IBeacon dev : devices) {
			if ((dev.getMajor() == Constants.FIRST_MAJOR)
					&& ((dev.getMinor() == Constants.FIRST_MINOR) && (dev
							.getAccuracy() < Constants.DISTANCE))) {
				// switch to holes tab when detected our first beacon and pass
				// token.
				Intent i = new Intent(this, Holes.class);
				Bundle b = new Bundle();
				b.putString("major", dev.getMajor() + "");
				b.putString("minor", dev.getMinor() + "");
				i.putExtras(b);
				startActivity(i);
				finish();
			}
		}
	}
}
