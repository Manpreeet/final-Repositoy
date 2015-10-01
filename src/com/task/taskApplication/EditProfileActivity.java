/**
 * 
 */
package com.task.taskApplication;

import com.tarsem.constant.ApplicationConstant;
import com.tarsem.constant.Constant;
import com.tarsem.request.ProfileAsyncTask;
import com.tarsem.responsecallback.ResponseCallback;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

/**
 * @author Manpreet
 * 
 */
public class EditProfileActivity extends ParentActivity {
	ParentActivity parentActivity;
	Context context;
	ProgressBar progressBar2;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.task.taskApplication.ParentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
		findAttributesId();
		getProfileInfo();
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
						showToastMessage("fetch detail successfully");

						progressBar2.setVisibility(View.GONE);
					}

					@Override
					public void onErrorRecieve(Object object) {
						showToastMessage((String) object);

						progressBar2.setVisibility(View.GONE);
					}
				}, null).execute();
	}

	/**
	 * 
	 * developer:Manpreet date:27-Sep-2015 return:void description:
	 */
	private void findAttributesId() {
		parentActivity = this;
		context = this;
		progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);

	}

}
