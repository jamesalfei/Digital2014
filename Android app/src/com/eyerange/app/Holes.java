package com.eyerange.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import networkUtilities.NetworkCommsFeedback;
import utilities.Constants;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.radiusnetworks.ibeacon.IBeacon;
import com.radiusnetworks.ibeacon.IBeaconConsumer;
import com.radiusnetworks.ibeacon.IBeaconManager;
import com.radiusnetworks.ibeacon.RangeNotifier;
import com.radiusnetworks.ibeacon.Region;

public class Holes extends Activity implements IBeaconConsumer,
		NetworkCommsFeedback {
	private final IBeaconManager iBeaconManager = IBeaconManager
			.getInstanceForApplication(this);
	private ArrayList<IBeacon> devices;
	ArrayList<String> deviceNames;
	Context cont;
	ArrayAdapter<String> arrayAdapter;
	Bundle i;
	String holeID, distanceToPin, par = "";
	String token;
	Button weather, map, history;
	TextView holeText, parText, descText, distText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hole);

		setupComponents();
		cont = this;

		i = getIntent().getExtras();
		token = i.getString("token");

		// when start, get hole 1 from server
		// then fill in the details of the text views (etc.)

		iBeaconManager.bind(this);
		devices = new ArrayList<IBeacon>();
		deviceNames = new ArrayList<String>();
	}

	private void setupComponents() {
		holeText = (TextView) findViewById(R.id.textHole);
		parText = (TextView) findViewById(R.id.textPar);
		descText = (TextView) findViewById(R.id.textDescription);
		distText = (TextView) findViewById(R.id.textDistance);

		weather = (Button) findViewById(R.id.weather);
		map = (Button) findViewById(R.id.map);
		history = (Button) findViewById(R.id.history);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void updateList(ArrayList<IBeacon> device) {
		for (IBeacon dev : device) {
			if (dev.getAccuracy() > 0) {
				deviceNames.add("Accuracy: " + dev.getAccuracy() + "m\n"
						+ "UUID: " + dev.getProximityUuid() + "\n" + "Major: "
						+ dev.getMajor() + "\n" + "Minor: " + dev.getMinor());
			}
		}
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

	private void sortList(ArrayList<IBeacon> devices) {
		Collections.sort(devices, new Comparator<IBeacon>() {
			@Override
			public int compare(IBeacon s1, IBeacon s2) {
				return Double.compare(s1.getAccuracy(), s2.getAccuracy());
			}
		});
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

					sortList(devices);

					checkDevices(devices);

					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							updateList(devices);
						}
					});
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
					&& (dev.getMinor() == Constants.FIRST_MINOR)) {
				// switch to holes tab when detected our first beacon and pass
				// token.
				Intent i = new Intent(this, Holes.class);
				Bundle b = new Bundle();
				b.putString("token", token);
				i.putExtras(b);
				startActivity(i);
			}
		}
	}

	@Override
	public void onLoginComplete(boolean success, String token) {
	}

	@Override
	public void onLogoutComplete(boolean success) {
	}

	@Override
	public void onCheckComplete(String status) {
	}

	@Override
	public void onGotHoleDetails(String holeID, String distanceToPin, String par) {
		this.holeID = holeID;
		this.distanceToPin = distanceToPin;
		this.par = par;
		updateText();
	}

	private void updateText() {
		holeText.setText(holeID);
		distText.setText(distanceToPin);
		parText.setText(par);

	}
}