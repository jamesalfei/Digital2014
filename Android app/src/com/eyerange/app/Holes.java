package com.eyerange.app;

import java.util.ArrayList;
import java.util.Collection;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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
	String holeID, distanceToPin, par, desc = "";
	NetworkCommsFeedback feedback;
	String token, major, minor;
	ImageButton weather, map, history;
	TextView holeText, parText, descText, distText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hole);

		setupComponents();
		cont = this;
		feedback = this;

		i = getIntent().getExtras();
		major = i.getString("major");
		minor = i.getString("minor");

		iBeaconManager.bind(this);
		devices = new ArrayList<IBeacon>();
		deviceNames = new ArrayList<String>();

		addActionListeners();

		// new GetHoleDetails(token, major, minor, feedback, cont).execute();
		addDummyDetails();
	}

	private void addActionListeners() {
		map.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(cont, Map.class));
			}
		});
	}

	private void addDummyDetails() {
		holeText.append("1");
		distText.append("465y");
		parText.append("4");
		descText.append("A long par 4 with a right to left dog-leg, this is a "
				+ "very challenging opening hole. Attempts to squeeze your drive "
				+ "up the left hand side of the fairway to secure the most "
				+ "straightforward approach to the green will be threatened by the "
				+ "presence of a cluster of deep bunkers at the inside elbow of the "
				+ "dog-leg. Aiming for the right of the fairway, however although "
				+ "safer because it avoids the traps, will leave a tougher approach, "
				+ "as the second shot will have to clear further bunkers that guard "
				+ "the right hand side of the green. The putting surface itself is "
				+ "angled to reward those that take the riskier approach off the "
				+ "tree and succeed.");
	}

	private void setupComponents() {
		holeText = (TextView) findViewById(R.id.textHole);
		parText = (TextView) findViewById(R.id.textPar);
		descText = (TextView) findViewById(R.id.textDescription);
		distText = (TextView) findViewById(R.id.textDistance);

		weather = (ImageButton) findViewById(R.id.weather);
		map = (ImageButton) findViewById(R.id.map);
		history = (ImageButton) findViewById(R.id.history);
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
			if ((dev.getMajor() == Constants.SECOND_MAJOR)
					&& ((dev.getMinor() == Constants.SECOND_MINOR) && (dev
							.getAccuracy() < Constants.DISTANCE))) {
				// switch to holes tab when detected our first beacon and pass
				// token.
				Intent i = new Intent(this, Result.class);
				Bundle b = new Bundle();
				b.putString("major", dev.getMajor() + "");
				b.putString("minor", dev.getMinor() + "");
				i.putExtras(b);
				startActivity(i);
				finish();
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
	public void onGotHoleDetails(String holeID, String distanceToPin,
			String par, String desc) {
		this.holeID = holeID;
		this.distanceToPin = distanceToPin;
		this.par = par;
		this.desc = desc;
		updateText();
	}

	private void updateText() {
		holeText.append(holeID);
		distText.append(distanceToPin);
		parText.append(par);
		descText.append(desc);
	}
}