/**
 * 
 */
package com.taskism.taskApplication;

import java.util.List;

import com.task.taskApplication.R;
import com.taskism.adapter.UserListCustomAdapter;
import com.taskism.bean.UserBean;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * @author Manpreet
 * 
 */
public class AddNewScheduleActivity extends ParentActivity {
	private Spinner roleSpinner, userSpinner;
	private TextView noUserFoundText, titleLabel;
	private Context context;
	private ParentActivity parentActivity;
	ProgressBar loadingBar;
	List<UserBean> taskListBeans;
	private LinearLayout userListParent;
	UserListCustomAdapter userListCustomAdapter;
	private int scheduleId = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taskism.taskApplication.ParentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_update_schedule);
		findAttributesId();
	}

	/**
	 * developer:Manpreet date:15-Nov-2015 return:void description: method for
	 * find attributes id
	 */
	private void findAttributesId() {
		context = this;
		parentActivity = this;
		// nameInput = (EditText) findViewById(R.id.nameInput);
	titleLabel = (TextView) findViewById(R.id.commentsTitle);
		titleLabel.setText("Add Schedule");
		/*
		 * descriptionInput = (EditText) findViewById(R.id.descriptionInput);
		 * colorInput = (Button) findViewById(R.id.colorInput);
		 */
	//	loadingBar = (ProgressBar) findViewById(R.id.loadingProgress);

	}

	/***
	 * 
	 * developer:Manpreet date:09-Nov-2015 return:void description: method for
	 * perform event on tap of save
	 */
	public void onClickSave(View view) {

	}

	public void onClickBack(View view) {
		finish();
		overridePendingTransition(R.anim.slide_back_in, R.anim.slide_back_out);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
		overridePendingTransition(R.anim.slide_back_in, R.anim.slide_back_out);

	}

}
