/**
 * 
 */
package com.taskism.taskApplication;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.task.taskApplication.R;
import com.taskism.bean.AccessBean;
import com.taskism.bean.EditOtherUserProfileBean;
import com.taskism.bean.RoleBean;
import com.taskism.constant.ApplicationConstant;
import com.taskism.constant.Constant;
import com.taskism.request.EditUserAsyncTask;
import com.taskism.responsecallback.ResponseCallback;
import com.taskism.utility.Utility;

/**
 * @author Manpreet
 * 
 */
public class EditUserActivity extends ParentActivity {
	EditText emailInput, firstNameInput, lastNameInput, passwordInput,
			confirmPasswordInput, cellNumberInputs;
	CheckBox getEmailCheck, roleBarBackCheck, roleBarTenderCheck,
			roleBottleServiceCheck, roleCleaningCheck, roleCoatCheck,
			roleDoorStaffCheck, roleMangerCheck, roleMarketingCheck,
			roleTableServiceCheck, permissionDailyTaskCheck,
			permissionEditRoleCheck, permissionEditScheduleCheck,
			permissionEditTaskCheck, permissionEditUserCheck;
	private LinearLayout roleParent;
	ParentActivity parentActivity;
	Context context;
	private ProgressBar loadingBar;
	int userId = 0;

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

	}

	/**
	 * developer:Manpreet date:03-Oct-2015 return:void description:method for
	 * find attributes id
	 */
	private void findAttributesId() {
		parentActivity = this;
		context = this;
		loadingBar = (ProgressBar) findViewById(R.id.loadingProgress);
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
		getSideMenu(EditUserActivity.this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		getIntentData();

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
			roleStatus.setChecked(bean.roleStatus);
			roleParent.addView(view);
		}

	}

	/**
	 * developer:Manpreet date:17-Oct-2015 return:void description: method for
	 * get data using bundle class
	 */
	private void getIntentData() {
		Bundle bundle = new Bundle();
		bundle = getIntent().getExtras();
		if (bundle != null) {
			userId = bundle.getInt(Constant.userid);
			fetchUserInfo(userId);

		}
	}

	/**
	 * developer:Manpreet date:08-Oct-2015 return:void description: method for
	 * fetch user info
	 * 
	 * @param userId
	 */
	private void fetchUserInfo(int userId) {
		loadingBar.setVisibility(View.VISIBLE);
		new EditUserAsyncTask(ApplicationConstant.appurl
				+ ApplicationConstant.editOtherUserProfile + "&"
				+ Constant.userid + "=" + Constant.getLoggedUserId(context)
				+ "&" + Constant.updateUserId + "=" + userId, context,
				new ResponseCallback() {

					@Override
					public void onSuccessRecieve(Object object) {

						EditOtherUserProfileBean otherUserProfileBean = (EditOtherUserProfileBean) object;
						updateUi(otherUserProfileBean);
						loadingBar.setVisibility(View.GONE);

					}

					@Override
					public void onErrorRecieve(Object object) {
						showToastMessage((String) object);
						Utility.hideKeyBoard(EditUserActivity.this);

						loadingBar.setVisibility(View.GONE);
					}
				}, null).execute();

	}

	/**
	 * developer:Manpreet date:17-Oct-2015 return:void description: method for
	 * update Ui
	 */
	protected void updateUi(EditOtherUserProfileBean otherUserProfileBean) {
		try {
			if (!otherUserProfileBean.emailId.equals("null")) {
				emailInput.setText(otherUserProfileBean.emailId);

			}
			firstNameInput.setText(otherUserProfileBean.firstName);
			lastNameInput.setText(otherUserProfileBean.lastName);
			if (!otherUserProfileBean.cellPhone.equals("null")) {
				cellNumberInputs.setText(otherUserProfileBean.cellPhone);

			}
			bindRoleList(otherUserProfileBean.roleBeanList);
			updateAccessStatus(otherUserProfileBean.accessBeanList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * developer:Manpreet date:19-Oct-2015 return:void description:
	 */
	private void updateAccessStatus(List<AccessBean> accessBeanList) {
		if (accessBeanList.size() != 0) {
			for (int i = 0; i < accessBeanList.size(); i++) {
				if (accessBeanList.get(i).accessName
						.equals("Dialy Tasks Completed")) {
					permissionDailyTaskCheck
							.setChecked(accessBeanList.get(i).accessStatus);
				} else if (accessBeanList.get(i).accessName
						.equals("Edit Roles")) {
					permissionEditRoleCheck
							.setChecked(accessBeanList.get(i).accessStatus);
				} else if (accessBeanList.get(i).accessName
						.equals("Edit Schedule")) {
					permissionEditScheduleCheck.setChecked(accessBeanList
							.get(i).accessStatus);
				} else if (accessBeanList.get(i).accessName
						.equals("Edit Tasks")) {
					permissionEditTaskCheck
							.setChecked(accessBeanList.get(i).accessStatus);

				} else if (accessBeanList.get(i).accessName
						.equals("Edit Users")) {
					permissionEditUserCheck
							.setChecked(accessBeanList.get(i).accessStatus);

				}
			}
		}

	}

	public void onClickBack(View view) {
		finish();
		overridePendingTransition(R.anim.slide_back_in, R.anim.slide_back_out);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
		overridePendingTransition(R.anim.slide_back_in, R.anim.slide_back_out);

	}

	public void openLeftPanel(View view) {
		showMenu();

	}

	/**
	 * 
	 * developer:Manpreet date:08-Nov-2015 return:void description: method for
	 * perform action on click save button
	 */
	public void onClickSave(View view) {
		showToastMessage("webservice pending");
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
