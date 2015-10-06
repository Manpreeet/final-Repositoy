/**
 * 
 */
package com.tarsem.request;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.tarsem.responsecallback.ResponseCallback;
import com.tarsem.responsecallback.SettingResponseCallback;
import com.tarsem.utility.Utility;

/**
 * @author Manpreet
 * 
 */
public class SettingAsyncTask extends AsyncTask<Void, Void, Void> {
	/**
	 * 
	 */

	private String url;
	private String response = null, type;
	SettingResponseCallback responseCallback;
	Context context;

	public SettingAsyncTask(String url, Context context,
			SettingResponseCallback responseCallback) {
		this.url = url;
		this.context = context;
		this.responseCallback = responseCallback;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Void doInBackground(Void... params) {

		try {
			URL url1 = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
			conn.setRequestMethod("GET");

			// read the response
			System.out.println("Response Code: " + conn.getResponseCode());
			InputStream in = new BufferedInputStream(conn.getInputStream());
			response = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
			System.out.println(response);
			// responseObject = new HttpRequest().getJSONResponse(url);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);

		try {
			if (response != null && !response.equals("")) {
				String status = new JSONObject(response).getString("success");
				String warning = new JSONObject(response).getString("warning");
				if (status.equals("1")) {
					new Utility().saveSettings(response, responseCallback,
							context);
				} else {
					responseCallback.onError(warning);

				}
			} else {
				responseCallback
						.onError("Exception arrise while fetching data from server");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
