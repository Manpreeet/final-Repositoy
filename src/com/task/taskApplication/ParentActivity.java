package com.task.taskApplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Toast;

import com.tarsem.control.CustomProgressDialog;
import com.tarsem.leftpanel.control.SlideMenuControls;
import com.tarsem.leftpanel.control.SlidingActivityBase;
import com.tarsem.leftpanel.control.SlidingActivityHelper;
import com.tarsem.leftpanel.control.SlidingMenu;
import com.tarsem.utility.Utility;

public class ParentActivity extends FragmentActivity implements
		SlidingActivityBase {
	public static CustomProgressDialog customProgressDialog;
	public static ParentActivity masterActivity;
	private SlidingActivityHelper mHelper;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		masterActivity = this;
		mHelper = new SlidingActivityHelper(this);
		try {
			mHelper.onCreate(arg0);
		} catch (Exception e) {
		}
	}

	/**
 * 
 */
	public ParentActivity() {
		masterActivity = this;
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

	public boolean isConnectedToInternet() {
		try {
			if (Utility.isConnectedToInternet(getApplicationContext())) {
				return true;
			} else {
				dismissProgressBar();
				// CustomDialogView.showDialogMessage(masterActivity, "",
				// ApplicationConstant.INTERNET_NOT_AVAIL);
			}
		} catch (Exception e) {
			showToastMessage("no internet connection");
			// LOGGER.error("Error occured in the isConnectedToInternet method",
			// e);
		}
		return false;
	}

	/**
	 * 
	 * @user = manpreets2
	 * @Date = Jul 13, 2015
	 * @Return Type = void
	 * @Description = enable side menu
	 */

	public void getSideMenu(Activity a, String activityName) {
		DisplayMetrics dsplymtrx = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dsplymtrx);
		int width = dsplymtrx.widthPixels / 3;
		try {
			setBehindContentView(R.layout.left_panel);

		} catch (Exception e) {

			e.printStackTrace();
		}

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();

		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);

		sm.setFadeDegree(0.35f);
		sm.setBehindWidth(2 * width);

		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		new SlideMenuControls(a, activityName);
	}

	@Override
	public void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		try {
			mHelper.onPostCreate(savedInstanceState);
		} catch (Exception e) {

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#findViewById(int)
	 */
	@Override
	public View findViewById(int id) {
		View v = super.findViewById(id);
		if (v != null)
			return v;
		return mHelper.findViewById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.FragmentActivity#onSaveInstanceState(android.os
	 * .Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mHelper.onSaveInstanceState(outState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#setContentView(int)
	 */
	@Override
	public void setContentView(int id) {
		setContentView(getLayoutInflater().inflate(id, null));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#setContentView(android.view.View)
	 */
	@Override
	public void setContentView(View v) {
		setContentView(v, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#setContentView(android.view.View,
	 * android.view.ViewGroup.LayoutParams)
	 */
	public void setContentView(View v, LayoutParams params) {
		super.setContentView(v, params);
		mHelper.registerAboveContentView(v, params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#
	 * setBehindContentView(int)
	 */
	public void setBehindContentView(int id) {
		setBehindContentView(getLayoutInflater().inflate(id, null));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#
	 * setBehindContentView(android.view.View)
	 */
	public void setBehindContentView(View v) {
		DisplayMetrics dsplymtrx = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dsplymtrx);

		setBehindContentView(v, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#
	 * setBehindContentView(android.view.View,
	 * android.view.ViewGroup.LayoutParams)
	 */
	@Override
	public void setBehindContentView(View view,
			android.view.ViewGroup.LayoutParams layoutParams) {
		mHelper.setBehindContentView(view, layoutParams);

	}

	/*
	 * public void setBehindContentView(View v, LayoutParams params) {
	 * mHelper.setBehindContentView(v, params); }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#getSlidingMenu
	 * ()
	 */
	public SlidingMenu getSlidingMenu() {
		return mHelper.getSlidingMenu();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#toggle()
	 */
	public void toggle() {
		mHelper.toggle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#showAbove()
	 */
	public void showContent() {
		mHelper.showContent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#showBehind()
	 */
	public void showMenu() {
		mHelper.showMenu();

	}

	public void close() {
		mHelper.showContent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#showSecondaryMenu
	 * ()
	 */
	public void showSecondaryMenu() {
		mHelper.showSecondaryMenu();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#
	 * setSlidingActionBarEnabled(boolean)
	 */
	public void setSlidingActionBarEnabled(boolean b) {
		mHelper.setSlidingActionBarEnabled(b);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyUp(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		boolean b = mHelper.onKeyUp(keyCode, event);
		if (b)
			return b;
		return super.onKeyUp(keyCode, event);
	}

}
