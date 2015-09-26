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

/**
 * @author Manpreet
 * 
 */
public class ServerAsyncTask extends AsyncTask<Void, Void, Void> {
	/**
		 * 
		 */

	private String url;
	private Context context;
	private String response = null;
	ResponseCallback responseCallback;
	private JSONObject responseObject = null;

	public ServerAsyncTask(String url, Context context,
			ResponseCallback responseCallback) {
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
			System.out.println("Response Code: " +conn.getResponseCode());
			InputStream in = new BufferedInputStream(conn.getInputStream());
			 response = org.apache.commons.io.IOUtils.toString(in,
					"UTF-8");
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
			if (response != null) {
				int responseValue = new JSONObject(response).getInt("success");
				if (responseValue == 0) {
					// for parsing class
					responseCallback.onErrorRecieve("User not found");

				} else {

				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}