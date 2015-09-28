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

/**
 * @author Manpreet
 * 
 */
public class ForgetPasswordActivity extends ParentActivity {

	ParentActivity parentActivity;
	EditText forgetEmailAddress;
	Context context;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.task.taskApplication.ParentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_forget_password);
		findAttributesId();
	}

	/**
	 * developer:Manpreet date:27-Sep-2015 return:void description:
	 */
	private void findAttributesId() {
		parentActivity = this;
		context = this;
		forgetEmailAddress = (EditText) findViewById(R.id.forgetEmailAddress);
	}

	/**
	 * 
	 * developer:Manpreet date:27-Sep-2015 return:void description: method for
	 * perform event on tap of reset button
	 */
	public void onClickForgetPassword(View view) {
		Utility.hideKeyBoard(this);
		if (forgetEmailAddress.getText().toString().length() == 0) {
			forgetEmailAddress.setError(Constant.emailFieldValidation);

		} else if (!Utility.validateEmail(forgetEmailAddress.getText()
				.toString().trim())) {
			forgetEmailAddress.setError(Constant.emailFormatValidation);
			forgetEmailAddress.setText("");
			forgetEmailAddress.requestFocus();
		} else {
			if (isConnectedToInternet()) {
				showProgressBar();
				submitForgetRequestToServer();

			} else {
				showToastMessage("no internet connection");
			}
		}
	}

	/**
	 * developer:Manpreet date:27-Sep-2015 return:void description: method for
	 * submit forget request to server
	 */
	private void submitForgetRequestToServer() {
		new ServerAsyncTask(ApplicationConstant.appurl
				+ ApplicationConstant.forgetRequestType + "&" + Constant.email
				+ "=" + forgetEmailAddress.getText().toString().trim(),
				context, new ResponseCallback() {

					@Override
					public void onSuccessRecieve(Object object) {
						dismissProgressBar();
						showToastMessage((String) object);
						finish();
					}

					@Override
					public void onErrorRecieve(Object object) {
						dismissProgressBar();
						showToastMessage((String) object);
					}
				}, ApplicationConstant.forgetRequestType).execute();
	}

	/**
	 * 
	 * developer:Manpreet date:27-Sep-2015 return:void description: method for
	 * perform event on tap of back button
	 */
	public void onClickBack(View view) {
		finish();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}
