package backgroundTasks;

import java.util.ArrayList;
import java.util.List;

import networkUtilities.JSONParser;
import networkUtilities.NetworkCommsFeedback;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import utilities.Constants;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class GetHoleDetails extends AsyncTask<String, String, String> {

	String token, major, minor, beacon, desc = "";
	String holeID, distanceToPin, par = "";
	NetworkCommsFeedback response;
	Context cont;
	JSONObject details;
	ProgressDialog dialog;

	JSONParser jParser = new JSONParser();
	boolean success;

	public GetHoleDetails(String token, String major, String minor,
			NetworkCommsFeedback response, Context cont) {
		this.token = token;
		this.minor = minor;
		this.major = major;
		this.response = response;
		this.cont = cont;
		this.beacon = Constants.BEACON_UUID + "-" + major + "-" + minor;
		this.dialog = new ProgressDialog(cont);
	}

	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		this.dialog.setMessage("Getting hole details...");
		this.dialog.show();
	}

	/**
	 * Deleting product
	 * */
	@Override
	protected String doInBackground(String... args) {

		// Check for success tag
		try {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(Constants.TAG_BEACON_ID, beacon));
			params.add(new BasicNameValuePair(Constants.TAG_AUTH, token));

			// getting product details by making HTTP request
			JSONObject json = jParser.makeHTTPRequest(Constants.URL_BEACON,
					Constants.HTTP_POST, params);

			// check your log for json response
			Log.d("Get hole details", json.toString());

			// json success tag
			success = json.getBoolean(Constants.TAG_SUCCESS);

			if (success) {
				details = json.getJSONObject("Data");
				holeID = details.getString("HoleID");
				distanceToPin = details.getString("DistanceToPin");
				par = details.getString("Par");
				desc = details.getString("Desc");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * After completing background task Dismiss the progress dialog
	 * **/
	@Override
	protected void onPostExecute(String file_url) {
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
		response.onGotHoleDetails(holeID, distanceToPin, par, desc);
	}
}
