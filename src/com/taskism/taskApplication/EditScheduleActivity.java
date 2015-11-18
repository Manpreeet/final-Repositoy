/**
 * 
 */
package com.taskism.taskApplication;

import java.util.ArrayList;
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

import com.task.taskApplication.R;
import com.taskism.adapter.CustomEditScheduleAdapter;
import com.taskism.bean.EditScheduleBean;
import com.taskism.constant.ApplicationConstant;
import com.taskism.constant.Constant;
import com.taskism.control.ActivityController;
import com.taskism.request.DeleteAsyncTask;
import com.taskism.request.ScheduleListAsyncTask;
import com.taskism.responsecallback.ResponseCallback;
import com.taskism.utility.Utility;

/**
 * @author Manpreet
 * 
 */
public class EditScheduleActivity extends ParentActivity {
	ParentActivity parentActivity;
	Context context;
	List<EditScheduleBean> editScheduleBeanList;
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

		customEditScheduleAdapter = new CustomEditScheduleAdapter(context,
				new ArrayList<EditScheduleBean>(), parentActivity);
		editScheduleListView.setAdapter(customEditScheduleAdapter);
		try {
			getSideMenu(EditScheduleActivity.this);

		} catch (Exception e) {

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
			fetchScheduleListFromServer();
		} else {
			new Utility().showCustomDialog(
					Constant.internetConnectionPopupButtonText,
					Constant.internetConnectionTitle,
					Constant.internetConnectionMessage, false,
					EditScheduleActivity.this, null, null);

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
							fetchScheduleListFromServer();
						} else {
							swipeRefresh.setRefreshing(false);
							new Utility().showCustomDialog(
									Constant.internetConnectionPopupButtonText,
									Constant.internetConnectionTitle,
									Constant.internetConnectionMessage, false,
									EditScheduleActivity.this, null, null);
						}
					}
				});

			}
		});
	}

	/**
	 * 
	 * developer:Manpreet date:15-Nov-2015 return:void description: method for
	 * fetch schedule list from server
	 */
	private void fetchScheduleListFromServer() {
		new ScheduleListAsyncTask(ApplicationConstant.appurl
				+ "shiftlist&userid=62&date=2015-07-09", context,
				new ResponseCallback() {

					@Override
					public void onSuccessRecieve(Object object) {
						editScheduleBeanList = (List<EditScheduleBean>) object;
						updateEditScheduleList(editScheduleBeanList);
						loadingProgress.setVisibility(View.GONE);
						noRecordFoundText.setVisibility(View.GONE);
					}

					@Override
					public void onErrorRecieve(Object object) {
						loadingProgress.setVisibility(View.GONE);
						noRecordFoundText.setVisibility(View.VISIBLE);
					}
				}).execute();

	}

	/**
	 * developer:Manpreet date:15-Nov-2015 return:void description: method for
	 * bind schedule list in UI
	 * 
	 * @param editScheduleBeanList
	 */
	protected void updateEditScheduleList(
			List<EditScheduleBean> editScheduleBeanList) {
		swipeRefresh.setVisibility(View.VISIBLE);
		loadingProgress.setVisibility(View.GONE);
		customEditScheduleAdapter.updateScheduledTasKList(editScheduleBeanList);

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

	/**
	 * 
	 * developer:Manpreet date:15-Nov-2015 return:void description: method for
	 * add new schedule activity
	 */
	public void onClickAddSchedule(View view) {

		ActivityController.startActivityController(
				Constant.AddNewScheduleActivity, null,
				EditScheduleActivity.this, false);

	}

	/**
	 * 
	 * developer:Manpreet date:03-Oct-2015 return:void description: method for
	 * open delete confirmation popup
	 */
	public void showDeleteConfirmationPopup(final String roleId,
			String userNameValue, final int position) {

		new Utility().showCustomDialog("Delete", "Delete Schedule",
				"Are you sure you want to delete: " + userNameValue, false,
				EditScheduleActivity.this, null, new ResponseCallback() {

					@Override
					public void onSuccessRecieve(Object object) {
						deleteSchedule(roleId, position);
					}

					@Override
					public void onErrorRecieve(Object object) {
						// TODO Auto-generated method stub

					}
				});

	}

	/**
	 * developer:Manpreet date:15-Nov-2015 return:void description: method for
	 * delete
	 */
	protected void deleteSchedule(String roleId, final int position) {
		if (isConnectedToInternet()) {
			loadingProgress.setVisibility(View.VISIBLE);
			new DeleteAsyncTask(ApplicationConstant.appurl + "roledelete"
					+ "&roleid=" + roleId, context, new ResponseCallback() {

				@Override
				public void onSuccessRecieve(Object object) {
					editScheduleBeanList.remove(position);
					customEditScheduleAdapter
							.updateScheduledTasKList(editScheduleBeanList);
					loadingProgress.setVisibility(View.GONE);
				}

				@Override
				public void onErrorRecieve(Object object) {
					loadingProgress.setVisibility(View.GONE);
					showToastMessage((String) object);
				}
			}).execute();

		} else {
			new Utility().showCustomDialog(
					Constant.internetConnectionPopupButtonText,
					Constant.internetConnectionTitle,
					Constant.internetConnectionMessage, false,
					EditScheduleActivity.this, null, null);

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