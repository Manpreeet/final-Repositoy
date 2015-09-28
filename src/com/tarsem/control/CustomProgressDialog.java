package com.tarsem.control;

import android.app.ProgressDialog;
import android.content.Context;

public class CustomProgressDialog extends ProgressDialog {

	public CustomProgressDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 *            Constructor to create instance of this class
	 */
	/*
	 * public CustomProgressDialog(Context context){ // setting custom theme
	 * //super(context,R.style.CustomDialogueTheme); setCancelable(false); }
	 */

	public void setMessage(String message) {
		super.setMessage(message);
	}
}
