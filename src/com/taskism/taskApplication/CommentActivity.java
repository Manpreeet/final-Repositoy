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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.task.taskApplication.R;
import com.taskism.adapter.CommentsAdapter;
import com.taskism.bean.CommentsBean;
import com.taskism.constant.ApplicationConstant;
import com.taskism.constant.Constant;
import com.taskism.request.CommentsAsyncTask;
import com.taskism.request.SendCommentAsyncTask;
import com.taskism.responsecallback.CommentsCallback;
import com.taskism.responsecallback.ResponseCallback;
import com.taskism.utility.Utility;

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

		new CommentsAsyncTask(ApplicationConstant.appurl
				+ "commentday&date=2015-11-16" + "&scheduleid=" + scheduleId2
				+ "&taskid=" + taskId2 + "&userid=14", context,
				new ResponseCallback() {

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
						((TextView) findViewById(R.id.noRecordFoundText))
								.setVisibility(View.GONE);

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
		if (commentInput.getText().toString().length() != 0) {

			Utility.hideKeyBoard(CommentActivity.this);
			if (isConnectedToInternet()) {
				// commentInput.setText("");

				loadingBar.setVisibility(View.VISIBLE);
				postComment(commentInput.getText().toString());
			} else {
				new Utility().showCustomDialog("Ok", "Internet Connection",
						"no internet access", false, CommentActivity.this,
						null, null);

			}

		} else {
			new Utility().showCustomDialog("Ok", "Comment",
					"Enter comment first", false, CommentActivity.this, null,
					null);
		}
	}

	/**
	 * developer:Manpreet date:04-Oct-2015 return:void description: method for
	 * post comment on server
	 */
	private void postComment(String string) {
		new SendCommentAsyncTask(ApplicationConstant.appurl
				+ "commentcreate&date=2015-11-16" + "&scheduleid=" + scheduleId
				+ "&taskid=" + taskId + "&userid=14" + "&comment="
				+ commentInput.getText().toString().trim(), context,
				new CommentsCallback() {

					@Override
					public void onSuccessRecieve(Object object) {
						commentInput.setText("");
						getUserComments(taskId, scheduleId);

					}

					@Override
					public void onErrorRecieve(Object object) {
						commentInput.setText("");
						loadingBar.setVisibility(View.GONE);
						new Utility().showCustomDialog("Ok", "Error",
								(String) object, false, CommentActivity.this,
								null, null);
					}
				}, null).execute();
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

}
