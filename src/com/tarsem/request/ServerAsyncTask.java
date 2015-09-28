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

import com.tarsem.constant.ApplicationConstant;
import com.tarsem.constant.Constant;
import com.tarsem.parser.CommonParser;
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
	private String response = null, type;
	ResponseCallback responseCallback;
	Context context;

	public ServerAsyncTask(String url, Context context,
			ResponseCallback responseCallback, String type) {
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
			if (response != null) {

				if (!type.equals(ApplicationConstant.getTaskListRequest)) {

					int responseValue = new JSONObject(response)
							.getInt("success");
					if (responseValue == 0) {
						// for parsing class

						if (type.equals(ApplicationConstant.loginrequestType)) {
							responseCallback.onErrorRecieve("User not found");

						} else {
							responseCallback.onErrorRecieve(new JSONObject(
									response).get("warning"));
						}

					} else {
						if (type.equals(ApplicationConstant.loginrequestType)) {
							new CommonParser().loginResponseParser(response,
									responseCallback, context);
						} else if (type
								.equals(ApplicationConstant.registrationRequestType)) {
							responseCallback.onSuccessRecieve("Success");
						} else if (type
								.equals(ApplicationConstant.forgetRequestType)) {
							responseCallback
									.onSuccessRecieve("link has been sent to email address for regenerate password");
						}
					}
				} else {
					new CommonParser().allTaskResponseParser(response,
							responseCallback);

					/*
					 * new CommonParser().loginResponseParser(response,
					 * responseCallback, parentActivity)
					 */
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
