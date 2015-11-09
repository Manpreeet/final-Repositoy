/**
 * 
 */
package com.task.taskApplication;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chiralcode.colorpicker.ColorPickerDialog;
import com.chiralcode.colorpicker.ColorPickerDialog.OnColorSelectedListener;
import com.tarsem.bean.UserBean;
import com.tarsem.constant.ApplicationConstant;
import com.tarsem.constant.Constant;
import com.tarsem.request.GetUserListAsyncTask;
import com.tarsem.responsecallback.ResponseCallback;
import com.tarsem.utility.Utility;
import com.taskism.adapter.UserListCustomAdapter;

/**
 * @author Manpreet
 * 
 */
public class AddNewRoleActivity extends ParentActivity {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.task.taskApplication.ParentActivity#onCreate(android.os.Bundle)
	 */

	private Button colorInput;
	private TextView noUserFoundText;
	private Context context;
	private ParentActivity parentActivity;
	ProgressBar loadingBar;
	List<UserBean> taskListBeans;
	private LinearLayout userListParent;
	UserListCustomAdapter userListCustomAdapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_add_new_role);
		findAttributesId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		if (isConnectedToInternet()) {
			loadingBar.setVisibility(View.VISIBLE);
			getUserList();
		} else {
			noUserFoundText.setVisibility(View.VISIBLE);
			new Utility().showCustomDialog(
					Constant.internetConnectionPopupButtonText,
					Constant.internetConnectionTitle,
					Constant.internetConnectionMessage, false,
					AddNewRoleActivity.this, null, null);
		}
	}

	/**
	 * developer:Manpreet date:09-Nov-2015 return:void description: method for
	 * find attributes id
	 */
	private void findAttributesId() {
		context = this;
		parentActivity = this;
		colorInput = (Button) findViewById(R.id.colorInput);
		loadingBar = (ProgressBar) findViewById(R.id.loadingProgress);
		userListParent = (LinearLayout) findViewById(R.id.userParent);

	}

	/**
	 * 
	 * developer:Manpreet date:09-Nov-2015 return:void description: method for
	 * open color picker diialog
	 */
	public void openDialog(View view) {
		int initialColor = Color.GREEN;

		ColorPickerDialog colorPickerDialog = new ColorPickerDialog(this,
				initialColor, new OnColorSelectedListener() {

					@Override
					public void onColorSelected(int color) {

						colorInput.setText(convertHexFormat(color));
						colorInput.setBackgroundColor(Color.rgb(
								Color.red(color), Color.green(color),
								Color.blue(color)));
					}

				});
		colorPickerDialog.show();

	}

	/**
	 * developer:Manpreet date:09-Nov-2015 return:void description: method for
	 * get userList
	 */
	private void getUserList() {
		try {
			new GetUserListAsyncTask(ApplicationConstant.appurl
					+ ApplicationConstant.getUserListRequest + "&userid=62",
					context, new ResponseCallback() {

						@Override
						public void onSuccessRecieve(Object object) {
							taskListBeans = (List<UserBean>) object;
							displayUserList(taskListBeans);

							loadingBar.setVisibility(View.GONE);
						}

						@Override
						public void onErrorRecieve(Object object) {
							loadingBar.setVisibility(View.GONE);
							// noUserFoundText.setVisibility(View.VISIBLE);
						}
					}).execute();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * developer:Manpreet date:09-Nov-2015 return:void description: method for
	 * display user list in view
	 */
	protected void displayUserList(List<UserBean> taskListBeans2) {
		for (int i = 0; i < taskListBeans2.size(); i++) {
			UserBean bean = taskListBeans2.get(i);

			LayoutInflater inflater = LayoutInflater.from(context);
			View view = inflater.inflate(R.layout.custom_role_list, null);

			TextView roleTitle = (TextView) view.findViewById(R.id.roleTitle);
			if (i == 0) {
				roleTitle.setText("Users");
				roleTitle.setTextColor(Color.BLACK);
				roleTitle.setVisibility(View.VISIBLE);
			} else {
				roleTitle.setVisibility(View.INVISIBLE);

			}
			TextView roleName = (TextView) view.findViewById(R.id.roleName);
			CheckBox roleStatus = (CheckBox) view.findViewById(R.id.roleStatus);
			roleName.setText(bean.userName);
			userListParent.addView(view);
		}

	}

	
	/***
	 * 
	 * developer:Manpreet
	   date:09-Nov-2015
	   return:void
	   description: method for perform event on tap of save
	 */
	public void onClickSave(View view) {

	}

	/**
	 * 
	 * developer:Manpreet date:09-Nov-2015 return:String description: method for
	 * convert rgb color into hex format
	 */
	private String convertHexFormat(int colorCode) {

		final StringBuilder builder = new StringBuilder();

		builder.append(Integer.toHexString(Color.red(colorCode)));
		builder.append(Integer.toHexString(Color.green(colorCode)));
		builder.append(Integer.toHexString(Color.blue(colorCode)));
		return builder.toString();
	}

}
