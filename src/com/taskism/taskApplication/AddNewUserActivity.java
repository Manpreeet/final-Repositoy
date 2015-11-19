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
import com.taskism.bean.RoleBean;
import com.taskism.constant.ApplicationConstant;
import com.taskism.constant.Constant;
import com.taskism.request.RoleListAsyncTask;
import com.taskism.request.SendCommentAsyncTask;
import com.taskism.responsecallback.CommentsCallback;
import com.taskism.responsecallback.ResponseCallback;
import com.taskism.utility.Utility;

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
	private String email = null, firstName = null, lastName = null,
			cellNumber = null, password = null, confirmPassword = null;
	private List<RoleBean> roleBeansList = null;
	String roleId = "";
	private List<String> roleList = null;

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
		// http://taskism.com/webservice001/?action=usernew&userid=62&email=gairy@123.com&firstname=Tom&lastname=Thumb&cellphone=&password=password&roles=1,2,7,8&access=21,20

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
			new Utility().showCustomDialog(
					Constant.internetConnectionPopupButtonText,
					Constant.internetConnectionTitle,
					Constant.internetConnectionMessage, false,
					AddNewUserActivity.this, null, null);

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
				+ ApplicationConstant.getRoleListRequest + "&userid=62",
				context, new ResponseCallback() {

					@Override
					public void onSuccessRecieve(Object object) {
						if (roleBeansList == null) {
							roleBeansList = (List<RoleBean>) object;
							bindRoleList(roleBeansList);
							loadingBar.setVisibility(View.GONE);
						}
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
			roleStatus
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							if (isChecked) {
								roleList.add(bean.roleId);
								// bean.roleId;
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
	 * 
	 * developer:Manpreet date:08-Oct-2015 return:void description: method for
	 * post data on server on tap save button
	 */
	public void onClickSave(View view) {

		email = emailInput.getText().toString().trim();
		firstName = firstNameInput.getText().toString().trim();
		lastName = lastNameInput.getText().toString().trim();
		password = passwordInput.getText().toString().trim();
		confirmPassword = confirmPasswordInput.getText().toString().trim();
		cellNumber = cellNumberInputs.getText().toString().trim();
		validateInputFields();
		// showToastMessage("webservice require");
	}

	/**
	 * developer:manpreets2 date:Nov 18, 2015 return:void description: method
	 * for validate input fields
	 */
	private void validateInputFields() {
		if (email.length() == 0 && password.length() == 0
				&& firstName.length() == 0 && lastName.length() == 0
				&& cellNumber.length() == 0) {
			showToastMessage(Constant.emptyFieldValidation);
			emailInput.requestFocus();
		} else if (!confirmPassword.equals(password)) {
			confirmPasswordInput.setText("");
			confirmPasswordInput.requestFocus();
			confirmPasswordInput.setError(Constant.confirmPasswordValidation);
		} else {
			postDataServer();
		}
	}

	/**
	 * developer:manpreets2 date:Nov 18, 2015 return:void description: method
	 * for post data on server
	 */
	private void postDataServer() {
		if (isConnectedToInternet()) {
			for (int i = 0; i < roleList.size(); i++) {
				if (i == roleList.size() - 1) {
					roleId = roleId + roleList.get(i);
				} else {
					roleId = roleId + roleList.get(i) + ",";
				}

			}
			// http://taskism.com/webservice001/?action=usernew&userid=62&email=gairy@123.com&firstname=Tom&lastname=Thumb&cellphone=&password=password&roles=1,2,7,8&access=21,20
			loadingBar.setVisibility(View.VISIBLE);
			// role id require and acceess Id
			new SendCommentAsyncTask(ApplicationConstant.appurl + "usernew"
					+ "&userid=62&" + "&email=" + email + "&firstname="
					+ firstName + "&lastname=" + lastName + "&cellphone="
					+ cellNumber + "&password=" + password + "&roles=" + roleId
					+ "&access=1,2", context, new CommentsCallback() {

				@Override
				public void onSuccessRecieve(Object object) {
					loadingBar.setVisibility(View.GONE);
					//showToastMessage((String) object);
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
					AddNewUserActivity.this, null, null);
		}

	}

	public void openLeftPanel(View view) {
		showMenu();

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
