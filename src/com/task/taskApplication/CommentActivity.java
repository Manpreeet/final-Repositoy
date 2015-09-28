/**
 * 
 */
package com.task.taskApplication;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tarsem.bean.CommentsBean;
import com.tarsem.constant.ApplicationConstant;
import com.tarsem.constant.Constant;
import com.tarsem.request.CommentsAsyncTask;
import com.tarsem.responsecallback.ResponseCallback;
import com.tarsem.utility.Utility;
import com.taskism.adapter.CommentsAdapter;

/**
 * @author Manpreet
 * 
 */
public class CommentActivity extends ParentActivity {
	ParentActivity parentActivity;
	Context context;
	private ListView commentsList;
	private EditText commentInput;
	private TextView taskNametext, taskDate;
	int scheduleId, taskId;
	String taskName;
	ProgressBar loadingBar;
	CommentsAdapter commentsAdapter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.task.taskApplication.ParentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_comments);
		findAttributesId();
		getIntentData(bundle);
	}

	/**
	 * developer:Manpreet date:27-Sep-2015 return:void description: method for
	 * find attributes id
	 */
	private void findAttributesId() {
		parentActivity = this;
		context = this;
		taskNametext = (TextView) findViewById(R.id.taskTitle);
		taskDate = (TextView) findViewById(R.id.taskDate);
		commentsList = (ListView) findViewById(R.id.commentsList);
		commentInput = (EditText) findViewById(R.id.commentsInput);
		loadingBar = (ProgressBar) findViewById(R.id.loadingProgress);
	}

	/**
	 * developer:Manpreet date:27-Sep-2015 return:void description: method for
	 * get data using bundle class
	 */
	private void getIntentData(Bundle bundle) {
		bundle = (Bundle) getIntent().getExtras();
		if (bundle != null) {
			scheduleId = bundle.getInt(Constant.scheduleId);
			taskId = bundle.getInt(Constant.taskId);
			taskName = bundle.getString(Constant.taskName);
			taskNametext.setText(taskName);
			taskDate.setText(Utility.getDate());
			if (isConnectedToInternet()) {
				loadingBar.setVisibility(View.VISIBLE);
				getUserComments(taskId, scheduleId);

			}
		}
	}

	/**
	 * developer:Manpreet date:27-Sep-2015 return:void description: method for
	 * get user comments
	 */
	private void getUserComments(int taskId2, int scheduleId2) {
		// http://taskism.com/webservice001/?action=commentday&date=2015-08-18&scheduleid=6181&taskid=222&userid=14

		new CommentsAsyncTask(
				"http://taskism.com/webservice001/?action=commentday&date=2015-08-18&scheduleid=6181&taskid=222&userid=14",
				context, new ResponseCallback() {

					@SuppressWarnings("unchecked")
					@Override
					public void onSuccessRecieve(Object object) {
						loadingBar.setVisibility(View.GONE);
						@SuppressWarnings("unchecked")
						List<CommentsBean> commentsBeans = new ArrayList<>();
						commentsBeans = (List<CommentsBean>) object;
						commentsAdapter = new CommentsAdapter(context,
								commentsBeans);
						commentsList.setAdapter(commentsAdapter);
						commentsAdapter.notifyDataSetChanged();
					}

					@Override
					public void onErrorRecieve(Object object) {
						loadingBar.setVisibility(View.GONE);
						((TextView) findViewById(R.id.noRecordFoundText))
								.setVisibility(View.VISIBLE);
					}
				}, null).execute();
	}

	/**
	 * 
	 * developer:Manpreet date:27-Sep-2015 return:void description: method for
	 * onCLick save button
	 */
	public void onClickSave(View view) {

	}
}