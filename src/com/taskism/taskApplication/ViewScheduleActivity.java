/**
 * 
 */
package com.taskism.taskApplication;

import java.util.List;

import com.task.taskApplication.R;
import com.taskism.bean.UserShiftBean;
import com.taskism.constant.ApplicationConstant;
import com.taskism.constant.Constant;
import com.taskism.request.ViewScheduleAsyncTask;
import com.taskism.responsecallback.ResponseCallback;
import com.taskism.utility.Utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author Manpreet
 * 
 */
public class ViewScheduleActivity extends ParentActivity {
	ParentActivity parentActivity;
	Context context;
	private TextView noRecordFound, scheduleWeekPlan;
	private ProgressBar loadingProgress;
	private HorizontalScrollView horizontalScrollView;
	private LinearLayout horrizontalList;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.task.taskApplication.ParentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_view_schedule);
		findAttributesId();
	}

	/**
	 * developer:Manpreet date:03-Oct-2015 return:void description: method for
	 * find attributes id's
	 */
	private void findAttributesId() {
		parentActivity = this;
		context = this;
		try {
			getSideMenu(ViewScheduleActivity.this);

		} catch (Exception e) {
			// TODO: handle exception
		}
		scheduleWeekPlan = (TextView) findViewById(R.id.scheduleWeekPlan);
		noRecordFound = (TextView) findViewById(R.id.noRecordFoundText);
		horizontalScrollView = (HorizontalScrollView) findViewById(R.id.scheduleUserParent);
		loadingProgress = (ProgressBar) findViewById(R.id.loadingProgress);
		horrizontalList = (LinearLayout) findViewById(R.id.horrizontalList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		if (isConnectedToInternet()) {
			loadingProgress.setVisibility(View.VISIBLE);
			fetchScheduleList();

		} else {
			new Utility().showCustomDialog(
					Constant.internetConnectionPopupButtonText,
					Constant.internetConnectionTitle,
					Constant.internetConnectionMessage, false,
					ViewScheduleActivity.this, null, null);

		}

	}

	/**
	 * developer:Manpreet date:04-Oct-2015 return:void description: method for
	 * fetch schedule list from server
	 */
	private void fetchScheduleList() {
		// http://taskism.com/webservice_001/?action=shiftbyemployee&date=2015-07-11&userid=14
		new ViewScheduleAsyncTask(ApplicationConstant.appurl
				+ "shiftbyemployee" + "&date=2015-07-11" + "&userid=14",
				context, new ResponseCallback() {

					@Override
					public void onSuccessRecieve(Object object) {
						List<UserShiftBean> list = (List<UserShiftBean>) object;
						scheduleWeekPlan.setText(list.get(0).fromTo);
						bindScheduleInformation(list);
						loadingProgress.setVisibility(View.GONE);
					}

					@Override
					public void onErrorRecieve(Object object) {
						loadingProgress.setVisibility(View.GONE);
						noRecordFound.setVisibility(View.VISIBLE);
						showToastMessage((String) object);
					}
				}).execute();
	}

	/**
	 * developer:Manpreet date:19-Nov-2015 return:void description: method for
	 * bind schedule information
	 */
	@SuppressLint("CutPasteId")
	protected void bindScheduleInformation(List<UserShiftBean> taskList) {

		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View convertView = layoutInflater.inflate(
				R.layout.custom_view_schedule, null);

		horrizontalList.addView(convertView);

		for (int i = 0; i < taskList.size(); i++) {
			LayoutInflater layoutInflater1 = LayoutInflater.from(context);
			View convertView1 = layoutInflater1.inflate(
					R.layout.custom_view_schedule_information, null);

			TextView userNameText = (TextView) convertView1
					.findViewById(R.id.userNameText);
			TextView sundayWork, mondayWork, tuesdayWork, wednesdayWork, thrusdayWork, fridayWork, saturdayWork;
			sundayWork = (TextView) convertView1
					.findViewById(R.id.sundaySchedule);
			mondayWork = (TextView) convertView1
					.findViewById(R.id.mondayScheduleText);
			tuesdayWork = (TextView) convertView1
					.findViewById(R.id.tuesdayScheduleText);
			wednesdayWork = (TextView) convertView1
					.findViewById(R.id.wednesdayScheduleText);
			thrusdayWork = (TextView) convertView1
					.findViewById(R.id.thrusdayScheduleText);
			fridayWork = (TextView) convertView1
					.findViewById(R.id.fridayScheduleText);
			saturdayWork = (TextView) convertView1
					.findViewById(R.id.saturdayScheduleText);
			userNameText.setText(taskList.get(i).employeeName);
			if (!taskList.get(i).mondayShift.equals("")) {
				mondayWork.setText(taskList.get(i).mondayShift);
			} else {
				mondayWork.setText("");
			}
			if (!taskList.get(i).tuesdayShift.equals("")) {
				tuesdayWork.setText(taskList.get(i).tuesdayShift);
			} else {
				tuesdayWork.setText("");

			}
			/*
			 * if (!taskList.get(i).wednestdayShift.equals("")) {
			 * wednesdayWork.setText(taskList.get(i).wednestdayShift); }
			 */
			if (!taskList.get(i).saturdayShift.equals("")) {
				saturdayWork.setText(taskList.get(i).saturdayShift);
			} else {
				saturdayWork.setText("");
			}
			if (!taskList.get(i).firdayShift.equals("")) {
				fridayWork.setText(taskList.get(i).firdayShift);
			} else {
				fridayWork.setText("");
			}
			if (!taskList.get(i).thrusdayShift.equals("")) {
				thrusdayWork.setText(taskList.get(i).thrusdayShift);
			} else {
				thrusdayWork.setText("");
			}
			if (!taskList.get(i).sundayShift.equals("")) {
				sundayWork.setText(taskList.get(i).sundayShift);
			} else {
				sundayWork.setText("");
			}

			horrizontalList.addView(convertView1);
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
