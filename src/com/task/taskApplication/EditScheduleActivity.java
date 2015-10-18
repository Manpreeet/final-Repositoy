/**
 * 
 */
package com.task.taskApplication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tarsem.bean.ScheduledTaskBean;
import com.tarsem.bean.TaskListBean;
import com.tarsem.constant.ApplicationConstant;
import com.tarsem.request.MarkAsyncTask;
import com.tarsem.request.ServerAsyncTask;
import com.tarsem.responsecallback.ResponseCallback;
import com.tarsem.utility.Utility;
import com.taskism.adapter.CustomEditScheduleAdapter;
import com.taskism.adapter.CustomMyScheduleAdapter;

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
public class EditScheduleActivity extends ParentActivity {
	ParentActivity parentActivity;
	Context context;
	
	private ListView editScheduleListView;
	private TextView currentDateText;
	private CustomEditScheduleAdapter customEditScheduleAdapter;
	private ProgressBar loadingProgress;
	private SwipeRefreshLayout swipeRefresh;
	private TextView noRecordFoundText;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.task.taskApplication.ParentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_edit_schedule);
		findAttributesId();

	}

	/**
	 * developer:Manpreet date:03-Oct-2015 return:void description: method for
	 * find attrbutes id's
	 */
	private void findAttributesId() {
		parentActivity = this;
		context = this;
		
		noRecordFoundText = (TextView) findViewById(R.id.noRecordFoundText);
		editScheduleListView = (ListView) findViewById(R.id.editScheduleListView);
		currentDateText = (TextView) findViewById(R.id.currentDate);
		loadingProgress = (ProgressBar) findViewById(R.id.loadingProgress);
		swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
		swipeRefresh.setColorSchemeColors(Color.RED, Color.BLUE, Color.CYAN,
				Color.BLACK);
		
		customEditScheduleAdapter = new CustomEditScheduleAdapter(context, new ArrayList<ScheduledTaskBean>(), parentActivity);
		editScheduleListView.setAdapter(customEditScheduleAdapter);
		try {
			getSideMenu(EditScheduleActivity.this);

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
			fetchScheduleList();

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
						if (isConnectedToInternet()) {
							swipeRefresh.setRefreshing(true);
							fetchScheduleList();
						} else {
							swipeRefresh.setRefreshing(false);
							new Utility().showCustomDialog("Ok", "Internet Connection",
									"no internet connection", false, EditScheduleActivity.this,
									null, null);
						}
					}
				});

			}
		});
	}

	/**
	 * developer:Manpreet date:03-Oct-2015 return:void description: method for
	 * fetch schedule from server
	 */
	private void fetchScheduleList() {
		swipeRefresh.setVisibility(View.GONE);
		loadingProgress.setVisibility(View.VISIBLE);
		ArrayList<ScheduledTaskBean> scheduledTaskList = new ArrayList<ScheduledTaskBean>();
		for (int i = 0; i < 10; i++) {
			ScheduledTaskBean scheduledTaskDetail = new ScheduledTaskBean();
			
			scheduledTaskDetail.setColor("");
			scheduledTaskDetail.setScheduledTaskName("ScheduledTask " + i);
			scheduledTaskDetail.setUserName("User " + i);
			scheduledTaskDetail.setDuration("10.00AM - 12.00PM");
			
			scheduledTaskList.add(scheduledTaskDetail);
		}
		
		swipeRefresh.setVisibility(View.VISIBLE);
		loadingProgress.setVisibility(View.GONE);
		customEditScheduleAdapter.updateScheduledTasKList(scheduledTaskList);
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