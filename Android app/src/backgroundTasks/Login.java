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
import android.os.AsyncTask;
import android.util.Log;

public class Login extends AsyncTask<String, String, String> {

	String username, password, userID = "";
	NetworkCommsFeedback response;

	// JSON node/tag names
	JSONParser jParser = new JSONParser();
	boolean success, honbuUser;

	public Login(String username, String password, NetworkCommsFeedback response) {
		this.username = username;
		this.password = password;
		this.response = response;
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
			params.add(new BasicNameValuePair(Constants.TAG_USERNAME, username));
			params.add(new BasicNameValuePair(Constants.TAG_PASSWORD, password));

			// getting product details by making HTTP request
			JSONObject json = jParser.makeHTTPRequest(Constants.URL_LOGIN,
					Constants.HTTP_POST, params);

			// check your log for json response
			Log.d("Login attempt", json.toString());

			// json success tag
			success = json.getBoolean(Constants.TAG_SUCCESS);

			if (success) {
				// do something here
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
		response.onLoginComplete(success, honbuUser, userID);
	}

}
