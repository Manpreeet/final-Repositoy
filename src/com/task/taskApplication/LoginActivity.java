package com.task.taskApplication;

import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tarsem.application.TaskApplication;
import com.tarsem.constant.ApplicationConstant;
import com.tarsem.constant.Constant;
import com.tarsem.control.ActivityController;
import com.tarsem.request.ServerAsyncTask;
import com.tarsem.responsecallback.ResponseCallback;
import com.tarsem.utility.Utility;

public class LoginActivity extends ParentActivity {

	private TextView signUpText, forgetPasswordText;
	ParentActivity parentActivity;
	Context context;
	private EditText emailField, passwordField;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
		findAttributesId();
	}

	/**
	 * developer:Manpreet date:26-Sep-2015 return:void description: method for
	 * find attibutes id's
	 */
	private void findAttributesId() {
		parentActivity = this;
		context = this;
		signUpText = (TextView) findViewById(R.id.signUpText);
		emailField = (EditText) findViewById(R.id.emailField);
		passwordField = (EditText) findViewById(R.id.passwordField);

	}

	/**
	 * 
	 * developer:Manpreet date:26-Sep-2015 return:void description: method for
	 * perfrom login event
	 */
	public void onClickLogin(View view) {
		loginValidationMethod(emailField.getText().toString().trim(),
				passwordField.getText().toString().trim());
	}

	/**
	 * developer:Manpreet date:26-Sep-2015 return:void description: method for
	 * check login validation
	 * 
	 * @param password
	 * @param email
	 */
	private void loginValidationMethod(String email, String password) {
		Utility.hideKeyBoard(this);
		if (email.length() == 0 && password.length() == 0) {
			showToastMessage(Constant.emptyFieldValidationMsg);
		} else if (email.length() == 0) {
			emailField.setError(Constant.emailFieldValidation);
			emailField.setText("");
			emailField.requestFocus();
		} else if (password.length() == 0) {
			passwordField.setError(Constant.passwordFieldValidation);
			passwordField.setText("");
			passwordField.requestFocus();
		} else if (!Utility.validateEmail(email)) {
			emailField.setError(Constant.emailFormatValidation);
			emailField.setText("");
			emailField.requestFocus();

		} else {
			try {
				if (isConnectedToInternet()) {
					showProgressBar();
					
					 loginRequestToServer(email, password);
					 
					//loginRequest(email, password);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	
	/**
	 * developer:Manpreet date:26-Sep-2015 return:void description: method for
	 * send login request to server
	 */
	private void loginRequestToServer(String email, String password) {
		// taskism.com/webservice001/index.php?action=login&email=gairy@1234.com&password=1234pass
		new ServerAsyncTask(ApplicationConstant.appurl
				+ ApplicationConstant.loginrequestType + "&email" + email
				+ "&password" + password, context, new ResponseCallback() {

			@Override
			public void onSuccessRecieve(Object object) {
				dismissProgressBar();
			}

			@Override
			public void onErrorRecieve(Object object) {
				dismissProgressBar();

				showToastMessage((String) object);
				emailField.setText("");
				passwordField.setText("");
				emailField.requestFocus();
			}
		}).execute();
	}

	/**
	 * 
	 * developer:Manpreet date:26-Sep-2015 return:void description: method for
	 * perfrom signup event
	 */
	public void onClickSignUp(View view) {
		ActivityController.startActivityController(
				Constant.signUpActivityController, null, this, false);
	}

	/**
	 * 
	 * developer:Manpreet date:26-Sep-2015 return:void description: method for
	 * perform forget password event
	 */
	public void onClickForgetPassword(View view) {

	}

}
