/**
 * 
 */
package com.task.taskApplication;

import java.util.List;

import com.tarsem.bean.TaskListBean;
import com.tarsem.constant.ApplicationConstant;
import com.tarsem.request.MarkAsyncTask;
import com.tarsem.request.ServerAsyncTask;
import com.tarsem.responsecallback.ResponseCallback;
import com.tarsem.utility.Utility;
import com.taskism.adapter.CustomEditTaskAdapter;
import com.taskism.adapter.CustomTaskAdapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author Manpreet
 * 
 */
public class EditTaskActivity extends ParentActivity {

	ParentActivity parentActivity;
	Context context;
	private ListView userTaskList;
	private TextView currentDateText;
	CustomEditTaskAdapter customTaskAdapter;
	ProgressBar loadingProgress;
	SwipeRefreshLayout swipeRefresh;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.task.taskApplication.ParentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_edit_task);
		findAttributesId();
	}

	/**
	 * developer:Manpreet date:27-Sep-2015 return:void description:
	 */
	private void findAttributesId() {
		parentActivity = this;
		context = this;
		userTaskList = (ListView) findViewById(R.id.userTasksList);
		currentDateText = (TextView) findViewById(R.id.currentDate);
		loadingProgress = (ProgressBar) findViewById(R.id.loadingProgress);
		swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
		swipeRefresh.setColorSchemeColors(Color.RED, Color.BLUE, Color.CYAN,
				Color.BLACK);

		try {
			getSideMenu(EditTaskActivity.this);

		} catch (Exception e) {
			e.printStackTrace();
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
		//setCurrentDate();
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
				+ ApplicationConstant.allUserScheduleTask + "&"
				+ "date=2015-07-10" + "&userid=14", context,
				new ResponseCallback() {

					@Override
					public void onSuccessRecieve(Object object) {
						@SuppressWarnings("unchecked")
						List<TaskListBean> taskListBeans = (List<TaskListBean>) object;
						customTaskAdapter = new CustomEditTaskAdapter(context,
								taskListBeans, EditTaskActivity.this);
						userTaskList.setAdapter(customTaskAdapter);
						customTaskAdapter.notifyDataSetChanged();

						swipeRefresh.setRefreshing(false);
						loadingProgress.setVisibility(View.GONE);

					}

					@Override
					public void onErrorRecieve(Object object) {
						loadingProgress.setVisibility(View.GONE);

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
				"http://taskism.com/webservice001/?action=checkuncheck&date=2015-09-04&scheduleid="
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
