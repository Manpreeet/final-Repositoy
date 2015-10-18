/**
 * 
 */
package com.task.taskApplication;

import com.tarsem.constant.ApplicationConstant;
import com.tarsem.constant.Constant;
import com.tarsem.request.ServerAsyncTask;
import com.tarsem.responsecallback.ResponseCallback;
import com.tarsem.utility.Utility;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

/**
 * @author Manpreet
 * 
 */
public class RegistrationActivity extends ParentActivity {
	ParentActivity parentActivity;
	Context context;
	EditText firstName, lastName, emailAddress, password, confirmPassword,
			contactNumber;
	private ProgressBar loadingProgress;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.task.taskApplication.ParentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_registation);
		findAttributesId();
	}

	/**
	 * developer:Manpreet date:27-Sep-2015 return:void description: method for
	 * find attributes id
	 */
	private void findAttributesId() {
		parentActivity = this;
		context = this;
		emailAddress = (EditText) findViewById(R.id.registrationEmailField);
		firstName = (EditText) findViewById(R.id.registrationFirstName);
		lastName = (EditText) findViewById(R.id.registrationLastName);
		password = (EditText) findViewById(R.id.registrationPasswrod);
		confirmPassword = (EditText) findViewById(R.id.registrationConfirmPassword);
		contactNumber = (EditText) findViewById(R.id.registrationCompanyName);
		loadingProgress = (ProgressBar) findViewById(R.id.loadingProgress);
	}

	/**
	 * 
	 * developer:Manpreet date:27-Sep-2015 return:void description:
	 */
	public void onClickSignUp(View view) {
		checkRegistationValidation();
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

	/**
	 * developer:Manpreet date:27-Sep-2015 return:void description: method for
	 * check registation inputValidation
	 */
	private void checkRegistationValidation() {
		Utility.hideKeyBoard(this);
		if (firstName.getText().toString().length() == 0
				&& lastName.getText().toString().length() == 0
				&& emailAddress.getText().toString().length() == 0
				&& password.getText().toString().length() == 0
				&& confirmPassword.getText().toString().length() == 0) {
			showToastMessage("All fields are mendatory");
		} else if (emailAddress.getText().toString().length() == 0) {

			emailAddress.setError(Constant.emailFieldValidation);
			emailAddress.setText("");
			emailAddress.requestFocus();
		} else if (firstName.getText().toString().length() == 0) {
			firstName.setError("Enter first name");
			firstName.setText("");
			firstName.requestFocus();
		} else if (lastName.getText().toString().length() == 0) {
			lastName.setError("Enter first name");
			lastName.setText("");
			lastName.requestFocus();
		} else if (password.getText().toString().length() == 0) {
			password.setError(Constant.passwordFieldValidation);
			;
			password.setText("");
			password.requestFocus();
		} else if (!Utility.validateEmail(emailAddress.getText().toString())) {
			emailAddress.setError(Constant.emailFormatValidation);
			emailAddress.setText("");
			emailAddress.requestFocus();

		} else if (!password.getText().toString()
				.equals(confirmPassword.getText().toString())) {
			confirmPassword.setError("Confirm password not same as password");
			confirmPassword.setText("");
			confirmPassword.requestFocus();
		} else {
			if (isConnectedToInternet()) {
				loadingProgress.setVisibility(View.VISIBLE);
				sendRegistrationRequestToServer();

			} else {
				new Utility().showCustomDialog("Ok", "Internet Connection",
						"no internet access", false, RegistrationActivity.this,
						null, null);

			}
		}
	}

	/**
	 * developer:Manpreet date:27-Sep-2015 return:void description: method for
	 * send reequest to server
	 */
	private void sendRegistrationRequestToServer() {
		new ServerAsyncTask(ApplicationConstant.appurl
				+ ApplicationConstant.registrationRequestType + "&userid=62"
				+ "&email=" + emailAddress.getText().toString().trim()
				+ "&firstname=" + firstName.getText().toString().trim()
				+ "&lastname=" + lastName.getText().toString().trim()
				+ "&password=" + password.getText().toString().trim()
				+ "&roles=1" + "access=20", context, new ResponseCallback() {

			@Override
			public void onSuccessRecieve(Object object) {

				loadingProgress.setVisibility(View.GONE);
				showToastMessage("Successfully register on app");
				finish();
			}

			@Override
			public void onErrorRecieve(Object object) {

				loadingProgress.setVisibility(View.GONE);
				showToastMessage((String) object);
			}
		}, ApplicationConstant.registrationRequestType).execute();

	}
}
