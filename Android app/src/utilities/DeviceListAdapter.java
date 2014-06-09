package utilities;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;

import com.radiusnetworks.ibeacon.IBeacon;

/**
 * Device List Adapter is used to hold a collection of Bluetooth LE devices as
 * they are found, and to present them using the "device_list_item" layout
 * style.
 * 
 * @author Michael Clarke <mfc1@aber.ac.uk>
 * @version 0.1
 * @date Mon 7th Apr. 2014
 */
public class DeviceListAdapter {

	private final ArrayList<IBeacon> devices;
	private final LayoutInflater inflater;

	public DeviceListAdapter(Context context) {

		super();
		devices = new ArrayList<IBeacon>();
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public void addDevice(IBeacon device) {

		/*
		 * We might often get the same device found over and over again. We
		 * don't want to keep adding the device to the list, so, if it's already
		 * in the list, we won't add it again.
		 */
		if (!devices.contains(device)) {
			devices.add(device);
		}

	}

	public void removeDevice(IBeacon device) {
		devices.remove(device);
	}

	public int getCount() {
		return devices.size();
	}

	public Object getItem(int position) {
		return devices.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	/*
	 * @Override public View getView(int position, View view, ViewGroup
	 * viewGroup) { TextView deviceUUID; TextView majorValue; TextView
	 * minorValue; TextView distanceValue; IBeacon device;
	 * 
	 * view = inflater.inflate(R.layout.device_list_item, null);
	 * 
	 * deviceUUID = (TextView) view.findViewById(R.id.deviceUUID); majorValue =
	 * (TextView) view.findViewById(R.id.majorValue); minorValue = (TextView)
	 * view.findViewById(R.id.minorValue); distanceValue = (TextView)
	 * view.findViewById(R.id.distanceValue);
	 * 
	 * device = devices.get(position);
	 * 
	 * deviceUUID.setText(device.getProximityUuid());
	 * majorValue.setText(Integer.toString(device.getMajor()));
	 * minorValue.setText(Integer.toString(device.getMinor()));
	 * 
	 * DecimalFormat df = new DecimalFormat("~ #.## m");
	 * distanceValue.setText(df.format(device.getAccuracy())); return view; }
	 */

	public void clear() {
		devices.clear();

	}

}
