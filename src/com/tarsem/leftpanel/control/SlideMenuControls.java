/**
 * 
 */
package com.tarsem.leftpanel.control;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInstaller.Session;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.tarsem.control.ActivityController;
import com.tarsem.utility.Utility;
import com.task.taskApplication.HomeActivity;
import com.task.taskApplication.LoginActivity;
import com.task.taskApplication.ParentActivity;
import com.task.taskApplication.R;

/**
 * @author vishalj
 * 
 */
public class SlideMenuControls extends ParentActivity {
	long customerId = 0;
	ParentActivity parentActivity;

	public SlideMenuControls(final Activity ctx, final String activityName)

	{
		parentActivity = this;
		LayoutInflater inflater = LayoutInflater.from(ctx);
		View view = inflater.inflate(R.layout.left_panel, null);
		ctx.findViewById(R.id.signInView).setVisibility(View.VISIBLE);
		ctx.findViewById(R.id.rel_share).setVisibility(View.GONE);

		//
		ctx.findViewById(R.id.rel_home).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						clickEvent(ctx, HomeActivity.class, "Home");
						closeMEnu(ctx, activityName);

					}
				});

	}

	/**
	 * 
	 * @user = vishal jangid
	 * @Date = july 8, 2015
	 * @Return Type = void
	 * @Description = close side nevigation menu
	 */

	private void closeMEnu(Activity activity, String activityName) {
		try {
			/*
			 * if (activityName.equals("Home")) {
			 * 
			 * ((HomeActivity) activity).close(); } else if
			 * (activityName.equals("favoriteGas")) { ((FavoriteGasStation)
			 * activity).close(); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * developer:Manpreet date:29-Sep-2015 return:void description: method for
	 * open left panel dialog
	 */
	private void clickEvent(Activity context, Class class1, String activityName) {
		closeMEnu(context, activityName);
		Intent i = new Intent(context.getApplicationContext(), class1);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);

		context.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		context.finish();
	}

}
