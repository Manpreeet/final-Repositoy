/**
 * 
 */
package com.taskism.taskApplication;

import java.util.List;

import com.task.taskApplication.R;
import com.taskism.bean.RoleBean;
import com.taskism.constant.ApplicationConstant;
import com.taskism.constant.Constant;
import com.taskism.request.RoleListAsyncTask;
import com.taskism.responsecallback.ResponseCallback;
import com.taskism.utility.Utility;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author Manpreet
 * 
 */
public class AddNewUserActivity extends ParentActivity {
	EditText emailInput, firstNameInput, lastNameInput, passwordInput,
			confirmPasswordInput, cellNumberInputs;
	CheckBox getEmailCheck, roleBarBackCheck, roleBarTenderCheck,
			roleBottleServiceCheck, roleCleaningCheck, roleCoatCheck,
			roleDoorStaffCheck, roleMangerCheck, roleMarketingCheck,
			roleTableServiceCheck, permissionDailyTaskCheck,
			permissionEditRoleCheck, permissionEditScheduleCheck,
			permissionEditTaskCheck, permissionEditUserCheck;
	private LinearLayout roleParent;
	private ProgressBar loadingBar;

	ParentActivity parentActivity;
	Context context;
	private TextView titleText;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.task.taskApplication.ParentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.acitivty_edit_user);
		findAttributesId();
		getSideMenu(AddNewUserActivity.this);
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

			getRoleList();
		} else {
			loadingBar.setVisibility(View.GONE);

		}

	}

	/**
	 * developer:Manpreet date:19-Oct-2015 return:void description: method for
	 * fetch roleList from server
	 */
	private void getRoleList() {

		/*
		 * REQUEST: http://taskism.com/webservice001/?action=rolelist&userid=62
		 */
		new RoleListAsyncTask(ApplicationConstant.appurl
				+ ApplicationConstant.getRoleListRequest + "&userid="
				+ Constant.getLoggedUserId(context), context,
				new ResponseCallback() {

					@Override
					public void onSuccessRecieve(Object object) {
						List<RoleBean> roleBeansList = (List<RoleBean>) object;
						bindRoleList(roleBeansList);
						loadingBar.setVisibility(View.GONE);

					}

					@Override
					public void onErrorRecieve(Object object) {
						showToastMessage((String) object);
						Utility.hideKeyBoard(AddNewUserActivity.this);

						loadingBar.setVisibility(View.GONE);

					}
				}).execute();
	}

	/**
	 * developer:Manpreet date:08-Oct-2015 return:void description: method for
	 * find attributes id's
	 */
	private void findAttributesId() {
		parentActivity = this;
		context = this;
		titleText = (TextView) findViewById(R.id.titleText);
		titleText.setText("New User");
		emailInput = (EditText) findViewById(R.id.emailInput);
		firstNameInput = (EditText) findViewById(R.id.firstNameInput);
		lastNameInput = (EditText) findViewById(R.id.lastNameInput);
		passwordInput = (EditText) findViewById(R.id.passwordInput);
		confirmPasswordInput = (EditText) findViewById(R.id.retypePasswordInput);
		cellNumberInputs = (EditText) findViewById(R.id.cellNameInput);
		getEmailCheck = (CheckBox) findViewById(R.id.getEmailCheck);
		permissionDailyTaskCheck = (CheckBox) findViewById(R.id.daliyTaskStatus);
		permissionEditRoleCheck = (CheckBox) findViewById(R.id.editRoleTaskStatus);
		permissionEditScheduleCheck = (CheckBox) findViewById(R.id.editScheduleStatus);
		permissionEditTaskCheck = (CheckBox) findViewById(R.id.editTaskStatus);
		permissionEditUserCheck = (CheckBox) findViewById(R.id.editUserStatus);
		roleParent = (LinearLayout) findViewById(R.id.roleParent);
		loadingBar = (ProgressBar) findViewById(R.id.loadingProgress);

	}

	/**
	 * developer:Manpreet date:19-Oct-2015 return:void description: method for
	 * implement role list view
	 * 
	 * @param roleBeansList
	 */
	protected void bindRoleList(List<RoleBean> roleBeansList) {
		for (int i = 0; i < roleBeansList.size(); i++) {
			RoleBean bean = roleBeansList.get(i);

			LayoutInflater inflater = LayoutInflater.from(context);
			View view = inflater.inflate(R.layout.custom_role_list, null);

			TextView roleTitle = (TextView) view.findViewById(R.id.roleTitle);
			if (i == 0) {
				roleTitle.setVisibility(View.VISIBLE);
			} else {
				roleTitle.setVisibility(View.INVISIBLE);

			}
			TextView roleName = (TextView) view.findViewById(R.id.roleName);
			CheckBox roleStatus = (CheckBox) view.findViewById(R.id.roleStatus);
			roleName.setText(bean.roleName);
			roleParent.addView(view);
		}

	}

	/**
	 * 
	 * developer:Manpreet date:08-Oct-2015 return:void description: method for
	 * post data on server on tap save button
	 */
	public void onClickSave(View view) {
		showToastMessage("webservice require");
	}

	public void openLeftPanel(View view) {
		showMenu();

	}

	public void onClickBack(View view) {
		finish();
		overridePendingTransition(R.anim.slide_back_in, R.anim.slide_back_out);

	}
/* (non-Javadoc)
 * @see android.support.v4.app.FragmentActivity#onBackPressed()
 */
@Override
public void onBackPressed() {
	super.onBackPressed();
	finish();
	overridePendingTransition(R.anim.slide_back_in, R.anim.slide_back_out);

}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.task.taskApplication.ParentActivity#close()
	 */
	@Override
	public void close() {
		super.close();

		showContent();

	}

}
