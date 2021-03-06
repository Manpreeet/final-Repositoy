/**
 * 
 */
package com.taskism.taskApplication;

import com.task.taskApplication.R;
import com.taskism.constant.ApplicationConstant;
import com.taskism.constant.Constant;
import com.taskism.request.ServerAsyncTask;
import com.taskism.responsecallback.ResponseCallback;
import com.taskism.utility.Utility;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

/**
 * @author Manpreet
 * 
 */
public class ForgetPasswordActivity extends ParentActivity {

	ParentActivity parentActivity;
	EditText forgetEmailAddress;
	Context context;
	private ProgressBar loadingProgress;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.task.taskApplication.ParentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
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
		loadingProgress = (ProgressBar) findViewById(R.id.loadingProgress);
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
				loadingProgress.setVisibility(View.VISIBLE);
				submitForgetRequestToServer();

			} else {
				new Utility().showCustomDialog(
						Constant.internetConnectionPopupButtonText,
						Constant.internetConnectionTitle,
						Constant.internetConnectionMessage, false,
						ForgetPasswordActivity.this, null, null);
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

						loadingProgress.setVisibility(View.GONE);
						showToastMessage((String) object);
						finish();
					}

					@Override
					public void onErrorRecieve(Object object) {
						loadingProgress.setVisibility(View.GONE);
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

}
