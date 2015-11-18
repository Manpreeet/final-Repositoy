/**
 * 
 */
package com.taskism.taskApplication;

import com.task.taskApplication.R;
import com.taskism.bean.UserProfileBean;
import com.taskism.constant.ApplicationConstant;
import com.taskism.constant.Constant;
import com.taskism.request.ProfileAsyncTask;
import com.taskism.responsecallback.ResponseCallback;
import com.taskism.utility.Utility;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;

/**
 * @author Manpreet
 * 
 */
public class MyProfileActivity extends ParentActivity {
	ParentActivity parentActivity;
	Context context;
	private EditText firstName, lastName, email, contactNumber, password,
			retypePassword;
	private CheckBox getEmailStatus;

	ProgressBar progressBar2;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.task.taskApplication.ParentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_my_profile);
		findAttributesId();
		if (isConnectedToInternet()) {
			getProfileInfo();

		} else {
			
			new Utility().showCustomDialog("Ok","Internet Connection",
					"no internet connection", false, MyProfileActivity.this,
					null, null);

			showToastMessage("no internet connection");
			Utility.hideKeyBoard(MyProfileActivity.this);
		}
	}

	/**
	 * 
	 * developer:Manpreet date:27-Sep-2015 return:void description:
	 */
	private void findAttributesId() {
		parentActivity = this;
		context = this;
		progressBar2 = (ProgressBar) findViewById(R.id.loadingProgress);
		firstName = (EditText) findViewById(R.id.firstNameInput);
		lastName = (EditText) findViewById(R.id.lastNameInput);
		contactNumber = (EditText) findViewById(R.id.cellNameInput);
		email = (EditText) findViewById(R.id.emailInput);
		getEmailStatus = (CheckBox) findViewById(R.id.getEmailCheck);
		password = (EditText) findViewById(R.id.passwordInput);
		retypePassword = (EditText) findViewById(R.id.retypePasswordInput);
		getSideMenu(MyProfileActivity.this);

	}

	/**
	 * developer:Manpreet date:01-Oct-2015 return:void description: method for
	 * get profile information from server
	 */
	private void getProfileInfo() {

		progressBar2.setVisibility(View.VISIBLE);
		new ProfileAsyncTask(ApplicationConstant.appurl
				+ ApplicationConstant.editProfileRequestType + "&"
				+ Constant.userid + "=" + Constant.getLoggedUserId(this),
				context, new ResponseCallback() {

					@Override
					public void onSuccessRecieve(Object object) {
						UserProfileBean userProfileBean = (UserProfileBean) object;
						updateUi(userProfileBean);

					}

					@Override
					public void onErrorRecieve(Object object) {
						showToastMessage((String) object);
						Utility.hideKeyBoard(MyProfileActivity.this);

						progressBar2.setVisibility(View.GONE);
					}
				}, null).execute();
	}

	/**
	 * developer:Manpreet date:02-Oct-2015 return:void description:
	 * 
	 * @param userProfileBean
	 */
	protected void updateUi(UserProfileBean userProfileBean) {
		firstName.setText(userProfileBean.firstName);
		lastName.setText(userProfileBean.lastName);
		email.setText(userProfileBean.email);
		if (userProfileBean.cellphone.equals("null")) {
			contactNumber.setText("");

		} else {
			contactNumber.setText(userProfileBean.cellphone);

		}

		getEmailStatus.setChecked(userProfileBean.getEmailStatus);
		progressBar2.setVisibility(View.GONE);
		Utility.hideKeyBoard(MyProfileActivity.this);

	}

	public void openLeftPanel(View view) {
		showMenu();

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
