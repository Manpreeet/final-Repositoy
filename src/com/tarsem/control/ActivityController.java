/**
 * 
 */
package com.tarsem.control;

import com.tarsem.constant.Constant;
import com.task.taskApplication.CommentActivity;
import com.task.taskApplication.ForgetPasswordActivity;
import com.task.taskApplication.HomeActivity;
import com.task.taskApplication.R;
import com.task.taskApplication.RegistrationActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author Manpreet
 * 
 */
public class ActivityController {
	/**
	 * 
	 */
	static Intent intent;

	public static void startActivityController(int controllerId, Bundle bundle,
			Activity activity, boolean flag) {

		switch (controllerId) {
		case 1:
			intent = new Intent(activity, RegistrationActivity.class);
			if (bundle != null) {
				intent.putExtra(
						String.valueOf(Constant.signUpActivityController),
						bundle);

			}
			activity.startActivity(intent);
			activity.overridePendingTransition(R.anim.slide_in,
					R.anim.slide_out);

			if (flag) {
				activity.finish();
			}
			break;
		case 2:
			intent = new Intent(activity, ForgetPasswordActivity.class);
			if (bundle != null) {
				intent.putExtra(String
						.valueOf(Constant.forgetPasswrodActivityController),
						bundle);

			}
			activity.startActivity(intent);
			activity.overridePendingTransition(R.anim.slide_in,
					R.anim.slide_out);

			if (flag) {
				activity.finish();
			}
			break;
		case 3:
			intent = new Intent(activity, HomeActivity.class);
			if (bundle != null) {
				intent.putExtra(String.valueOf(Constant.homeActivity), bundle);

			}
			activity.startActivity(intent);
			activity.overridePendingTransition(R.anim.slide_in,
					R.anim.slide_out);

			if (flag) {
				activity.finish();
			}
			break;
		case 4:
			intent = new Intent(activity, CommentActivity.class);
			if (bundle != null) {
				intent.putExtra(String.valueOf(Constant.homeActivity), bundle);

			}
			activity.startActivity(intent);
			activity.overridePendingTransition(R.anim.slide_in,
					R.anim.slide_out);

			if (flag) {
				activity.finish();
			}
			break;

		default:
			break;
		}

	}

}
