/**
 * 
 */
package com.taskism.leftpanel.control;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.task.taskApplication.R;
import com.taskism.constant.Constant;
import com.taskism.taskApplication.EditRoleActivity;
import com.taskism.taskApplication.EditScheduleActivity;
import com.taskism.taskApplication.EditTaskActivity;
import com.taskism.taskApplication.LoginActivity;
import com.taskism.taskApplication.MyProfileActivity;
import com.taskism.taskApplication.MyScheduleTaskActivity;
import com.taskism.taskApplication.ParentActivity;
import com.taskism.taskApplication.TutorialActivity;
import com.taskism.taskApplication.UserListActivity;
import com.taskism.taskApplication.UsersScheduleTaskActivity;
import com.taskism.taskApplication.ViewScheduleActivity;
import com.taskism.utility.Utility;

/**
 * @author Manpreet
 * 
 */
public class SlideMenuControls extends ParentActivity {
	long customerId = 0;
	ParentActivity parentActivity;

	public SlideMenuControls(final Activity ctx)

	{
		parentActivity = this;

		//
		ctx.findViewById(R.id.myProfileParent).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						clickEvent(ctx, MyProfileActivity.class);
						closeMEnu(ctx);

					}
				});

		ctx.findViewById(R.id.editUserParent).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						clickEvent(ctx, UserListActivity.class);
						closeMEnu(ctx);

						if (new Utility().getUserAdminStatus(ctx)) {

							clickEvent(ctx, UserListActivity.class);
							closeMEnu(ctx);
						} else {

							Toast.makeText(ctx,
									"you dont have permission for Edit User",
									Toast.LENGTH_LONG).show();
							// showToastMessage("you dont have permission");

						}

					}
				});

		ctx.findViewById(R.id.roleParent).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						clickEvent(ctx, EditRoleActivity.class);
						closeMEnu(ctx);

						if (new Utility().getRoleAdminStatus(ctx)) {

							clickEvent(ctx, EditRoleActivity.class);
							closeMEnu(ctx);
						} else {
							Toast.makeText(ctx,
									"you dont have permission for Edit Role",
									Toast.LENGTH_LONG).show();
							// showToastMessage("you dont have permission");
						}

					}
				});
		ctx.findViewById(R.id.editTaskParent).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						clickEvent(ctx, EditTaskActivity.class);
						closeMEnu(ctx);

/*						if (new Utility().getTaskAdminStatus(ctx)) {

							clickEvent(ctx, EditTaskActivity.class);
							closeMEnu(ctx);
						} else {
							Toast.makeText(ctx,
									"you dont have permission for Edit Task",
									Toast.LENGTH_LONG).show(); //
							// showToastMessage("you dont have permission");
						}
*/
					}
				});
		ctx.findViewById(R.id.editScheduleParent).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						clickEvent(ctx, EditScheduleActivity.class);
						closeMEnu(ctx);
						if (new Utility().getSheduleAdminStatus(ctx)) {

							clickEvent(ctx, EditTaskActivity.class);
							closeMEnu(ctx);
						} else {
							Toast.makeText(
									ctx,
									"you dont have permission for Edit Schedule",
									Toast.LENGTH_LONG).show(); //
							// showToastMessage("you dont have permission");
						}

					}
				});

		ctx.findViewById(R.id.allTaskParent).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						clickEvent(ctx, UsersScheduleTaskActivity.class);
						closeMEnu(ctx);

					}
				});
		ctx.findViewById(R.id.myScheduleTaskParent).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						clickEvent(ctx, MyScheduleTaskActivity.class);
						closeMEnu(ctx);

					}
				});
		ctx.findViewById(R.id.viewScheduleParent).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						clickEvent(ctx, ViewScheduleActivity.class);
						closeMEnu(ctx);

					}
				});
		ctx.findViewById(R.id.tutorialParent).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						clickEvent(ctx, TutorialActivity.class);
						closeMEnu(ctx);

					}
				});
		String userName = ((TextView) ctx.findViewById(R.id.signOutText))
				.getText().toString();
		((TextView) ctx.findViewById(R.id.signOutText)).setText(userName + " ("
				+ Constant.getLoggedUserName(ctx) + ")");
		ctx.findViewById(R.id.signOutParent).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						logoutUser(ctx);
						closeMEnu(ctx);

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

	private void closeMEnu(Activity activity) {
		try {
			((UsersScheduleTaskActivity) activity).close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * developer:Manpreet date:29-Sep-2015 return:void description: method for
	 * open left panel dialog
	 */
	private void clickEvent(Activity context, Class class1) {

		try {
			//closeMEnu(context);
			Intent i = new Intent(context.getApplicationContext(), class1);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
			context.finish();
			overridePendingTransition(R.anim.slide_back_in,
					R.anim.slide_back_out);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * developer:Manpreet date:03-Oct-2015 return:void description: method for
	 * logout user from app
	 */
	private void logoutUser(Activity activity) {

		SharedPreferences sharedPreferences = activity.getSharedPreferences(
				Constant.userInfo, 1);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.commit();
		new Utility().showCustomDialog("Ok", "Logout",
				"Are you sure you want to exit from taskism ?", true, activity,
				LoginActivity.class, null);
		/*
		 * Intent i = new Intent(activity.getApplicationContext(),
		 * LoginActivity.class); i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		 * i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); activity.startActivity(i);
		 * 
		 * activity.overridePendingTransition(R.anim.slide_in,
		 * R.anim.slide_out); activity.finish();
		 */
	}
}
