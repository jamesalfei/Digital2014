package utilities;

import java.util.Collection;
import java.util.Iterator;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.radiusnetworks.ibeacon.IBeacon;
import com.radiusnetworks.ibeacon.IBeaconConsumer;
import com.radiusnetworks.ibeacon.IBeaconManager;
import com.radiusnetworks.ibeacon.RangeNotifier;
import com.radiusnetworks.ibeacon.Region;

/**
 * This class will likely be integrated into our main "game" class
 */

public class Scanner extends Activity implements IBeaconConsumer {

	protected static final String TAG = "iBeaconDemo";
	private final IBeaconManager iBeaconManager = IBeaconManager
			.getInstanceForApplication(this);
	private DeviceListAdapter foundDevices;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Log.e(TAG, "iBeacon Loading!");

		super.onCreate(savedInstanceState);

		foundDevices = new DeviceListAdapter(this);
		iBeaconManager.bind(this);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		iBeaconManager.unBind(this);
	}

	@Override
	public void onIBeaconServiceConnect() {
		iBeaconManager.setRangeNotifier(new RangeNotifier() {

			@Override
			public void didRangeBeaconsInRegion(Collection<IBeacon> arg0,
					Region arg1) {

				foundDevices.clear();

				if (arg0.size() > 0) {
					Iterator<IBeacon> itt = arg0.iterator();
					while (itt.hasNext()) {
						foundDevices.addDevice(itt.next());
					}
				}
			}
		});

		try {
			iBeaconManager.startRangingBeaconsInRegion(new Region(
					"myRangingUniqueId", null, null, null));
		} catch (RemoteException e) {

			Log.e(TAG, "Unable to start ranging service.");
		}
	}
}
