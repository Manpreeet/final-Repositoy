/**
 * 
 */
package com.task.taskApplication;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tarsem.bean.TaskListBean;
import com.tarsem.constant.ApplicationConstant;
import com.tarsem.request.MarkAsyncTask;
import com.tarsem.request.ServerAsyncTask;
import com.tarsem.responsecallback.ResponseCallback;
import com.tarsem.utility.Utility;
import com.taskism.adapter.CustomMyScheduleAdapter;

/**
 * @author Manpreet
 * 
 */
public class MyScheduleTaskActivity extends ParentActivity {
	ParentActivity parentActivity;
	Context context;
	private ListView userTaskList;
	private TextView currentDateText;
	CustomMyScheduleAdapter customTaskAdapter;
	ProgressBar loadingProgress;
	SwipeRefreshLayout swipeRefresh;
	private TextView noRecordFoundText;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.task.taskApplication.ParentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_home);
		findAttributesId();
	}

	/**
	 * developer:Manpreet date:27-Sep-2015 return:void description:
	 */
	private void findAttributesId() {
		parentActivity = this;
		context = this;
		noRecordFoundText = (TextView) findViewById(R.id.noRecordFoundText);
		userTaskList = (ListView) findViewById(R.id.userTasksList);
		currentDateText = (TextView) findViewById(R.id.currentDate);
		loadingProgress = (ProgressBar) findViewById(R.id.loadingProgress);
		swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
		swipeRefresh.setColorSchemeColors(Color.RED, Color.BLUE, Color.CYAN,
				Color.BLACK);

		try {
			getSideMenu(MyScheduleTaskActivity.this);

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		setCurrentDate();
		refreshView();
		if (isConnectedToInternet()) {
			loadingProgress.setVisibility(View.VISIBLE);
			getTaskList();

		}
	}

	/**
	 * developer:Manpreet date:27-Sep-2015 return:void description:
	 */
	private void refreshView() {
		swipeRefresh.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				swipeRefresh.post(new Runnable() {
					@Override
					public void run() {
						swipeRefresh.setRefreshing(true);
						getTaskList();
						/*
						 * if (isConnectedToInternet()) {
						 * 
						 * 
						 * } else { swipeRefresh.setRefreshing(false); }
						 */
					}
				});

			}
		});
	}

	/**
	 * developer:Manpreet date:27-Sep-2015 return:void description: method for
	 * getTaskList from server
	 */
	private void getTaskList() {
		// http://taskism.com/webservice001/?action=allusertasks&date=2015-07-10&userid=14
		new ServerAsyncTask(ApplicationConstant.appurl
				+ ApplicationConstant.getMyScheduleListRequest + "&"
				+ "date=2015-07-10" + "&userid=14", context,
				new ResponseCallback() {

					@Override
					public void onSuccessRecieve(Object object) {
						@SuppressWarnings("unchecked")
						List<TaskListBean> taskListBeans = (List<TaskListBean>) object;

						noRecordFoundText.setVisibility(View.GONE);
						customTaskAdapter = new CustomMyScheduleAdapter(
								context, taskListBeans,
								MyScheduleTaskActivity.this);
						userTaskList.setAdapter(customTaskAdapter);
						customTaskAdapter.notifyDataSetChanged();

						swipeRefresh.setRefreshing(false);
						loadingProgress.setVisibility(View.GONE);
					}

					@Override
					public void onErrorRecieve(Object object) {
						loadingProgress.setVisibility(View.GONE);
						noRecordFoundText.setVisibility(View.VISIBLE);
						swipeRefresh.setRefreshing(false);
						showToastMessage((String) object);
					}
				}, ApplicationConstant.getTaskListRequest).execute();
	}

	/**
	 * 
	 * developer:Manpreet date:27-Sep-2015 return:void description:
	 */
	public void markTaskItem(TaskListBean listBean) {
		loadingProgress.setVisibility(View.VISIBLE);
		int scheduleId = Integer.parseInt(listBean.scheduleId);
		int taskId = Integer.parseInt(listBean.taskId);
		boolean b = listBean.checkedStatus;
		int checkId = 0;
		if (b) {
			checkId = 1;
		} else {
			checkId = 0;
		}
		new MarkAsyncTask(
				"http://taskism.com/webservice001/?action=checkuncheck&date=2015-08-18&scheduleid="
						+ scheduleId
						+ "&taskid="
						+ taskId
						+ "&userid=14&checked=" + checkId, context,
				new ResponseCallback() {

					@Override
					public void onSuccessRecieve(Object object) {
						loadingProgress.setVisibility(View.GONE);
					}

					@Override
					public void onErrorRecieve(Object object) {
						loadingProgress.setVisibility(View.GONE);

						showToastMessage((String) object);
					}
				}, null).execute();

	}

	/**
	 * developer:Manpreet date:27-Sep-2015 return:void description: method for
	 * set current date
	 */
	private void setCurrentDate() {
		try {
			currentDateText.setText(Utility.getDate());
		} catch (Exception e) {
			// TODO: handle exception
		}
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
