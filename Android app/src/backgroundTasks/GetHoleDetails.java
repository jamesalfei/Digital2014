package backgroundTasks;

import java.util.ArrayList;
import java.util.List;

import networkUtilities.JSONParser;
import networkUtilities.NetworkCommsFeedback;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utilities.Constants;
import android.os.AsyncTask;
import android.util.Log;

public class GetHoleDetails extends AsyncTask<String, String, String> {

	String token, major, minor, beacon = "";
	String holeID, distanceToPin, par = "";
	NetworkCommsFeedback response;

	// JSON node/tag names
	JSONParser jParser = new JSONParser();
	boolean success;

	public GetHoleDetails(String token, String major, String minor,
			NetworkCommsFeedback response) {
		this.token = token;
		this.minor = minor;
		this.major = major;
		this.response = response;
		this.beacon = Constants.BEACON_UUID + "-" + major + "-" + minor;
	}

	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
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
			params.add(new BasicNameValuePair(Constants.TAG_TOKEN_ID, token));
			params.add(new BasicNameValuePair(Constants.REQUEST_TYPE,
					Constants.REQUEST_BEACON));
			params.add(new BasicNameValuePair(Constants.CONTENT_TYPE,
					Constants.TYPE_JSON));

			// getting product details by making HTTP request
			JSONObject json = jParser.makeHTTPRequest(Constants.URL_API,
					Constants.HTTP_POST, params);

			// check your log for json response
			Log.d("Get hole details", json.toString());

			// json success tag
			success = json.getBoolean(Constants.TAG_SUCCESS);

			if (success) {
				JSONArray details = json.getJSONArray("Data");
				holeID = details.getString(0);
				distanceToPin = details.getString(1);
				par = details.getString(2);
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
		response.onGotHoleDetails(holeID, distanceToPin, par);
	}

}
