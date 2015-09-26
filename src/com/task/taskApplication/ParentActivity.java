package com.task.taskApplication;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.tarsem.control.CustomProgressDialog;
import com.tarsem.utility.Utility;

public class ParentActivity extends Activity {
	public static CustomProgressDialog customProgressDialog;
	public static ParentActivity masterActivity;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	/**
	 * @user: manpreet
	 * @date Sep 24, 2015
	 * @return void
	 * @exception
	 * @description method for display progreesBar
	 */
	public void showProgressBar() {
		try {
			Log.e("Inside Progress dialog", "showing");
			if (customProgressDialog == null) {
				Log.i("test", "showing dialog)))))))))))))))");
				customProgressDialog = new CustomProgressDialog(this);
				customProgressDialog.show();
			}
		} catch (Exception e) {
			Log.e("Exception arrise in showProgressBar", e.getMessage());
		}
	}

	/**
	 * @user: manpreet
	 * @date Sep 24, 2015
	 * @return void
	 * @exception
	 * @description method for display progreesBar
	 */
	public void dismissProgressBar() {
		try {
			if (customProgressDialog != null) {
				customProgressDialog.setCancelable(true);
				customProgressDialog.dismiss();
				customProgressDialog = null;
			}
		} catch (Exception e) {
			Log.e("Exception arrise in dismissProgressBar", e.getMessage());
		}
	}

	/**
	 * @user: manpreet
	 * @date Sep 24, 2015
	 * @return void
	 * @exception
	 * @description method for show Toast message
	 */
	public void showToastMessage(String message) {
		Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
		toast.show();
	}
	
	
	/**
	 * @user: manpreet
	 * @date Sep 24, 2015
	 * @return void
	 * @exception
	 * @description method for check internet connection
	 */
		
	public boolean isConnectedToInternet(){
		try {
			if(Utility.isConnectedToInternet(getApplicationContext())){
				return true;
			}
			else{
				dismissProgressBar();
				//CustomDialogView.showDialogMessage(masterActivity, "", ApplicationConstant.INTERNET_NOT_AVAIL);
			}
		} catch (Exception e) {
			showToastMessage("no internet connection");
			//LOGGER.error("Error occured in the isConnectedToInternet method", e);
		}
		return false;
	}
	
	
}
