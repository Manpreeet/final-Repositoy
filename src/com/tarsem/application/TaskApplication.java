/**
 * 
 */
package com.tarsem.application;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * @author Manpreet
 * 
 */
public class TaskApplication extends Application {
	public static final String TAG = TaskApplication.class.getSimpleName();
	private RequestQueue requestQueue;
	private static TaskApplication taskApplicationInstance;

	public static synchronized TaskApplication getInstance() {
		return taskApplicationInstance;
	}

	public RequestQueue getRequestQueue() {
		if (requestQueue == null) {
			requestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return requestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (requestQueue != null) {
			requestQueue.cancelAll(tag);
		}
	}
}
