/**
 * 
 */
package com.taskism.request;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.taskism.responsecallback.CommentsCallback;

/**
 * @author Manpreet
 * 
 */
public class SendCommentAsyncTask extends AsyncTask<Void, Void, Void> {
	/**
	 * 
	 */

	private String url;
	private String response = null, type;
	CommentsCallback responseCallback;
	Context context;

	public SendCommentAsyncTask(String url, Context context,
			CommentsCallback responseCallback, String type) {
		this.url = url;
		this.context = context;
		this.responseCallback = responseCallback;
		this.type = type;
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
				String success = new JSONObject(response).getString("success");
				if (success.equals("1")) {
					responseCallback.onSuccessRecieve(true);
				} else {
					responseCallback.onErrorRecieve(new JSONObject(response)
							.getString("warning"));
				}
			} else {
				responseCallback.onErrorRecieve("something might be wrong");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
