package com.eyerange.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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

import com.radiusnetworks.ibeacon.IBeacon;
import com.radiusnetworks.ibeacon.IBeaconConsumer;
import com.radiusnetworks.ibeacon.IBeaconManager;
import com.radiusnetworks.ibeacon.RangeNotifier;
import com.radiusnetworks.ibeacon.Region;

/**
 * Class will be shown after login and will start detecting ibeacons (possibly
 * for the start of a course)
 * 
 * This service will continue until the game is ended
 * 
 * @author James Alfei
 */
public class MainActivity extends Activity implements IBeaconConsumer {

	private final IBeaconManager iBeaconManager = IBeaconManager
			.getInstanceForApplication(this);
	private ArrayList<IBeacon> devices;
	ArrayList<String> deviceNames;
	Context cont;
	ArrayAdapter<String> arrayAdapter;
	Bundle i;
	String token;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setupComponents();
		cont = this;

		i = getIntent().getExtras();
		token = i.getString("token");

		iBeaconManager.bind(this);
		devices = new ArrayList<IBeacon>();
		deviceNames = new ArrayList<String>();
	}

	private void setupComponents() {
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
							.getAccuracy() < 1))) {
				// switch to holes tab when detected our first beacon and pass
				// token.
				Intent i = new Intent(this, Holes.class);
				Bundle b = new Bundle();
				b.putString("token", token);
				b.putString("major", dev.getMajor() + "");
				b.putString("minor", dev.getMinor() + "");
				i.putExtras(b);
				startActivity(i);
				finish();
			}
		}
	}
}
