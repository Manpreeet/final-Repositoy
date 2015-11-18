/**
 * 
 */
package com.taskism.control;

import com.task.taskApplication.R;
import com.taskism.constant.Constant;
import com.taskism.taskApplication.AddNewRoleActivity;
import com.taskism.taskApplication.AddNewScheduleActivity;
import com.taskism.taskApplication.AddNewUserActivity;
import com.taskism.taskApplication.CommentActivity;
import com.taskism.taskApplication.EditUserActivity;
import com.taskism.taskApplication.ForgetPasswordActivity;
import com.taskism.taskApplication.LoginActivity;
import com.taskism.taskApplication.RegistrationActivity;
import com.taskism.taskApplication.UsersScheduleTaskActivity;

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
			intent = new Intent(activity, UsersScheduleTaskActivity.class);
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
		case 5:
			intent = new Intent(activity, EditUserActivity.class);
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

		case 6:
			intent = new Intent(activity, LoginActivity.class);
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
		case 7:
			intent = new Intent(activity, AddNewUserActivity.class);
			if (bundle != null) {
				intent.putExtra(String.valueOf(Constant.AddUserActivity),
						bundle);

			}
			activity.startActivity(intent);
			activity.overridePendingTransition(R.anim.slide_in,
					R.anim.slide_out);

			if (flag) {
				activity.finish();
			}
			break;
		case 10:
			intent = new Intent(activity, AddNewRoleActivity.class);
			if (bundle != null) {
				intent.putExtra(String.valueOf(Constant.AddNewRoleActivity),
						bundle);

			}
			activity.startActivity(intent);
			activity.overridePendingTransition(R.anim.slide_in,
					R.anim.slide_out);

			if (flag) {
				activity.finish();
			}
			break;
		case 11:
			intent = new Intent(activity, AddNewScheduleActivity.class);
			if (bundle != null) {
				intent.putExtra(
						String.valueOf(Constant.AddNewScheduleActivity), bundle);

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
