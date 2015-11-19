/**
 * 
 */
package com.taskism.taskApplication;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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
import com.taskism.request.SendCommentAsyncTask;
import com.taskism.responsecallback.CommentsCallback;
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
	private LinearLayout roleParent, accessParent;
	ParentActivity parentActivity;
	Context context;
	private ProgressBar loadingBar;
	int updateUserId = 0;
	String email, firstName, lastName, contactNumber, password,
			confirmPassword, roleId = "", accessId = "";
	private List<String> roleList = null, accessList = null;

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
		/*
		 * permissionDailyTaskCheck = (CheckBox)
		 * findViewById(R.id.daliyTaskStatus); permissionEditRoleCheck =
		 * (CheckBox) findViewById(R.id.editRoleTaskStatus);
		 * permissionEditScheduleCheck = (CheckBox)
		 * findViewById(R.id.editScheduleStatus); permissionEditTaskCheck =
		 * (CheckBox) findViewById(R.id.editTaskStatus); permissionEditUserCheck
		 * = (CheckBox) findViewById(R.id.editUserStatus);
		 */
		accessParent = (LinearLayout) findViewById(R.id.accessParent);
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
		roleList = new ArrayList<String>();
		for (int i = 0; i < roleBeansList.size(); i++) {
			final RoleBean bean = roleBeansList.get(i);

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
			roleStatus.setTag(i);
			roleStatus.setChecked(bean.roleStatus);
			if (bean.roleStatus) {
				roleList.add(bean.roleId);
			}
			roleStatus
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							if (isChecked) {
								roleList.add(bean.roleId);
							} else {
								roleList.remove(bean.roleId);
							}
						}
					});

			roleName.setText(bean.roleName);
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
			updateUserId = bundle.getInt(Constant.userid);
			fetchUserInfo(updateUserId);

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
				+ Constant.userid + "=62" + "&" + Constant.updateUserId + "="
				+ userId, context, new ResponseCallback() {

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
			accessList = new ArrayList<String>();

			for (int i = 0; i < accessBeanList.size(); i++) {
				final AccessBean accessBean = accessBeanList.get(i);
				LayoutInflater inflater = LayoutInflater.from(context);
				View view = inflater.inflate(R.layout.custom_role_list, null);

				TextView roleTitle = (TextView) view
						.findViewById(R.id.roleTitle);
				if (i == 0) {
					roleTitle.setVisibility(View.VISIBLE);
				} else {
					roleTitle.setVisibility(View.INVISIBLE);

				}
				TextView roleName = (TextView) view.findViewById(R.id.roleName);
				CheckBox roleStatus = (CheckBox) view
						.findViewById(R.id.roleStatus);
				roleStatus.setTag(i);
				roleStatus.setChecked(accessBean.accessStatus);
				if (accessBean.accessStatus) {
					accessList.add(accessBean.accessId);
				}
				roleStatus
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {
								if (isChecked) {
									accessList.add(accessBean.accessId);
								} else {
									accessList.remove(accessBean.accessId);
								}
							}
						});

				roleName.setText(accessBean.accessName);
				accessParent.addView(view);
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
		validateInputFields();
	}

	/**
	 * developer:Manpreet date:19-Nov-2015 return:void description: method for
	 * validate input fields
	 */
	private void validateInputFields() {
		email = emailInput.getText().toString().trim();
		firstName = firstNameInput.getText().toString().trim();
		lastName = lastNameInput.getText().toString().trim();
		contactNumber = cellNumberInputs.getText().toString().trim();
		password = passwordInput.getText().toString().trim();
		confirmPassword = confirmPasswordInput.getText().toString().trim();
		if (!confirmPassword.equals(password)) {
			confirmPasswordInput.setText("");
			confirmPasswordInput.setError(Constant.confirmPasswordValidation);
			confirmPasswordInput.requestFocus();
		} else {
			sendRequestOnServer();
		}

	}

	/**
	 * developer:Manpreet date:19-Nov-2015 return:void description: method for
	 * send request on server
	 */
	private void sendRequestOnServer() {
		if (isConnectedToInternet()) {
			for (int i = 0; i < roleList.size(); i++) {
				if (i == roleList.size() - 1) {
					roleId = roleId + roleList.get(i);
				} else {
					roleId = roleId + roleList.get(i) + ",";
				}

			}

			for (int i = 0; i < accessList.size(); i++) {
				if (i == accessList.size() - 1) {
					accessId = accessId + accessList.get(i);
				} else {
					accessId = accessId + accessList.get(i) + ",";
				}

			}

			// http://taskism.com/webservice001/?action=useredit&userid=62&email=gairy@123.com&firstname=Jack&lastname=Thumb&cellphone=5108324400&password=&roles=1,2,7,8&access=21,20&updateid=67
			loadingBar.setVisibility(View.VISIBLE);
			// role id require and acceess Id
			new SendCommentAsyncTask(ApplicationConstant.appurl + "useredit"
					+ "&userid=62&" + "&email=" + email + "&firstname="
					+ firstName + "&lastname=" + lastName + "&cellphone="
					+ contactNumber + "&password=" + password + "&roles="
					+ roleId + "&access=" + accessId + "&updateid="
					+ updateUserId, context, new CommentsCallback() {

				@Override
				public void onSuccessRecieve(Object object) {
					loadingBar.setVisibility(View.GONE);
					// showToastMessage((String) object);
					finish();

				}

				@Override
				public void onErrorRecieve(Object object) {
					loadingBar.setVisibility(View.GONE);
					showToastMessage((String) object);

				}
			}, null).execute();
		} else {
			new Utility().showCustomDialog(
					Constant.internetConnectionPopupButtonText,
					Constant.internetConnectionTitle,
					Constant.internetConnectionMessage, false,
					EditUserActivity.this, null, null);
		}

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
