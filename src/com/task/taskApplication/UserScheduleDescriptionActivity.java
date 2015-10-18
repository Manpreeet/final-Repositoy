/**
 * 
 */
package com.task.taskApplication;

import java.util.ArrayList;
import java.util.List;

import com.tarsem.bean.CommentsBean;
import com.tarsem.bean.ScheduleDescriptionBean;
import com.tarsem.request.CommentsAsyncTask;
import com.tarsem.request.UserScheduleDescriptionAsynctask;
import com.tarsem.responsecallback.ResponseCallback;
import com.taskism.adapter.CommentsAdapter;
import com.taskism.adapter.CustomUserScheduleDescriptionAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author Manpreet
 * 
 */
public class UserScheduleDescriptionActivity extends ParentActivity {

	ParentActivity parentActivity;
	Context context;
	private ListView scheduleListView;
	private int taskId;
	private TextView title;
	private String taskName;
	private CustomUserScheduleDescriptionAdapter descriptionAdapter;
	ProgressBar loadingBar;
	private boolean flag = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.task.taskApplication.ParentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_schedule_description);
		findAttributesId();
		getIntentData(bundle);
		getScheduleDescription(taskId);
		addFooterInformation();

	}

	/**
	 * developer:Manpreet date:11-Oct-2015 return:void description: method for
	 * get data from previous activity
	 */
	private void getIntentData(Bundle bundle) {
		bundle = getIntent().getExtras();
		if (bundle != null) {
			taskId = bundle.getInt("taskId");
			taskName = bundle.getString("taskName");
			title.setText(taskName);
		}
	}

	/**
	 * developer:Manpreet date:11-Oct-2015 return:void description: method for
	 * find attributes id
	 */
	private void findAttributesId() {
		parentActivity = this;
		context = this;
		scheduleListView = (ListView) findViewById(R.id.scheduleList);
		loadingBar = (ProgressBar) findViewById(R.id.loadingProgress);
		title = (TextView) findViewById(R.id.descriptionTitle);

	}

	/**
	 * developer:Manpreet date:11-Oct-2015 return:void description: method for
	 * get schedule description
	 */
	private void getScheduleDescription(int taskId2) {
		loadingBar.setVisibility(View.VISIBLE);

		new UserScheduleDescriptionAsynctask(
				"http://taskism.com/webservice001/?action=tasksteplist&userid=62&taskid=223",
				context, new ResponseCallback() {

					@SuppressWarnings("unchecked")
					@Override
					public void onSuccessRecieve(Object object) {
						try {
							loadingBar.setVisibility(View.GONE);

							List<ScheduleDescriptionBean> commentsBeans = new ArrayList<>();
							commentsBeans = (List<ScheduleDescriptionBean>) object;
							descriptionAdapter = new CustomUserScheduleDescriptionAdapter(
									commentsBeans, context);
							scheduleListView.setAdapter(descriptionAdapter);

						} catch (Exception e) {
							e.printStackTrace();
						}
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
	 * developer:Manpreet date:11-Oct-2015 return:void description: method for
	 * add footer information
	 */
	public void addFooterInformation() {
		LayoutInflater layoutInflater = LayoutInflater
				.from(UserScheduleDescriptionActivity.this);
		View view = layoutInflater.inflate(
				R.layout.schedule_list_description_footer, null);
		View view2 = (View) view.findViewById(R.id.commentsHeaderView);
		TextView commentsTitle = (TextView) view
				.findViewById(R.id.commentsTitle);

		scheduleListView.addFooterView(view);

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
