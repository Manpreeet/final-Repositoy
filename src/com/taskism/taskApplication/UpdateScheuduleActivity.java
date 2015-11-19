/**
 * 
 */
package com.taskism.taskApplication;

import java.util.Calendar;
import java.util.List;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemClickListener;

import com.task.taskApplication.R;
import com.taskism.adapter.ScheduleUserListAdapter;
import com.taskism.adapter.ScheuduleRoleAdapter;
import com.taskism.adapter.UserListCustomAdapter;
import com.taskism.bean.RoleBean;
import com.taskism.bean.UserBean;
import com.taskism.constant.ApplicationConstant;
import com.taskism.constant.Constant;
import com.taskism.request.GetUserListAsyncTask;
import com.taskism.request.RoleListAsyncTask;
import com.taskism.responsecallback.ResponseCallback;
import com.taskism.utility.Utility;

/**
 * @author Manpreet
 * 
 */
public class UpdateScheuduleActivity extends ParentActivity {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.task.taskApplication.ParentActivity#onCreate(android.os.Bundle)
	 */

	private Button roleButton, userButton;

	private TextView noUserFoundText, titleLabel;
	private Context context;
	private ParentActivity parentActivity;
	ProgressBar loadingBar;
	List<UserBean> taskListBeans;
	private LinearLayout userListParent;
	UserListCustomAdapter userListCustomAdapter;
	private int scheduleId = 0;
	private String userName, taskName, roleId, userId, startTime, endTime;
	private DatePicker datePicker;
	private Calendar calendar;
	private int hour, minute, day, selectedId;
	private List<RoleBean> roleBeansList = null;
	List<UserBean> userList;
	private TextView startInput, stopInput;
	private String am_pm;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		setContentView(R.layout.activity_update_schedule);
		findAttributesId();
		getIntentData(bundle);
		implementDateSelectionListner();

		if (isConnectedToInternet()) {
			loadingBar.setVisibility(View.VISIBLE);

			fetchRoleList();
			fetchUserList();

		} else {
			new Utility().showCustomDialog(
					Constant.internetConnectionPopupButtonText,
					Constant.internetConnectionTitle,
					Constant.internetConnectionMessage, false,
					UpdateScheuduleActivity.this, null, null);

		}

	}

	/**
	 * developer:Manpreet date:09-Nov-2015 return:void description: method for
	 * get intent data using bundle class
	 */
	private void getIntentData(Bundle bundle) {
		// TODO Auto-generated method stub

		// http://taskism.com/webservice001/?action=roleinfo&userid=62&roleid=14
		bundle = getIntent().getExtras();
		if (bundle != null) {
			scheduleId = bundle.getInt(Constant.userid);
			userButton.setText(bundle.getString("username"));
			roleButton.setText(bundle.getString("rolename"));
			startTime = bundle.getString("starttime");
			endTime = bundle.getString("endtime");
			startInput.setText(startTime);

			stopInput.setText(endTime);

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
	}

	/**
	 * developer:Manpreet date:09-Nov-2015 return:void description: method for
	 * find attributes id
	 */
	private void findAttributesId() {
		context = this;
		parentActivity = this;
		titleLabel = (TextView) findViewById(R.id.commentsTitle);
		titleLabel.setText("Edit Schedule");
		loadingBar = (ProgressBar) findViewById(R.id.loadingProgress);
		userButton = (Button) findViewById(R.id.userSpinner);
		roleButton = (Button) findViewById(R.id.roleSpinner);
		startInput = (TextView) findViewById(R.id.startInput);
		stopInput = (TextView) findViewById(R.id.stopInput);

	}

	/**
	 * developer:Manpreet date:19-Nov-2015 return:void description: method for
	 * fetch role list from server
	 */
	private void fetchRoleList() {
		/*
		 * REQUEST: http://taskism.com/webservice001/?action=rolelist&userid=62
		 */
		new RoleListAsyncTask(ApplicationConstant.appurl
				+ ApplicationConstant.getRoleListRequest + "&userid=62",
				context, new ResponseCallback() {

					@Override
					public void onSuccessRecieve(Object object) {
						if (roleBeansList == null) {
							roleBeansList = (List<RoleBean>) object;
							loadingBar.setVisibility(View.GONE);
						}
					}

					@Override
					public void onErrorRecieve(Object object) {
						showToastMessage((String) object);
						loadingBar.setVisibility(View.GONE);

					}
				}).execute();

	}

	/**
	 * developer:Manpreet date:19-Nov-2015 return:void description: method for
	 * fetch user list from server
	 */
	private void fetchUserList() {
		new GetUserListAsyncTask(ApplicationConstant.appurl
				+ ApplicationConstant.getUserListRequest + "&userid=14",
				context, new ResponseCallback() {

					@Override
					public void onSuccessRecieve(Object object) {
						userList = (List<UserBean>) object;
						loadingBar.setVisibility(View.GONE);
					}

					@Override
					public void onErrorRecieve(Object object) {
						loadingBar.setVisibility(View.GONE);
						noUserFoundText.setVisibility(View.VISIBLE);
					}
				}).execute();

	}

	/**
	 * developer:Manpreet date:19-Nov-2015 return:void description:
	 */
	private void implementDateSelectionListner() {
		calendar = Calendar.getInstance();
		hour = calendar.get(Calendar.HOUR_OF_DAY);

		minute = calendar.get(Calendar.MINUTE);

		startInput.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				selectedId = 1;
				hour = Integer.parseInt(startTime.split(":")[0]);
				minute = Integer.parseInt(startTime.split(":")[1].substring(0, 2));
				showDialog(1);

			}
		});
		stopInput.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				selectedId = 2;
				hour = Integer.parseInt(endTime.split(":")[0]);
				minute = Integer.parseInt(endTime.split(":")[1].substring(0, 2));

				showDialog(1);

			}
		});

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		if (id == 1) {
			return new TimePickerDialog(this, myDateListener, hour, minute,
					true);
		}
		return null;
	}

	private TimePickerDialog.OnTimeSetListener myDateListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

			Calendar datetime = Calendar.getInstance();
			datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
			datetime.set(Calendar.MINUTE, minute);

			if (datetime.get(Calendar.AM_PM) == Calendar.AM)
				am_pm = "AM";
			else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
				am_pm = "PM";
			setDate(hourOfDay, minute);

		}
	};

	/**
	 * developer:Manpreet date:19-Nov-2015 return:void description:
	 */
	protected void setDate(int year, int month) {
		if (selectedId == 1) {
			startInput.setText(new StringBuilder().append(year).append(":")
					.append(month).append(" " + am_pm));

		} else {

			stopInput.setText(new StringBuilder().append(year).append(":")
					.append(month).append(" " + am_pm));

		}

	}

	/***
	 * 
	 * developer:Manpreet date:09-Nov-2015 return:void description: method for
	 * perform event on tap of save
	 */
	public void onClickSave(View view) {
		showToastMessage("webservice not find in document");
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

	public void onClickUser(View view) {
		showUserList("user");

	}

	public void onClickRole(View view) {
		showUserList("role");

	}

	/**
	 * developer:Manpreet date:19-Nov-2015 return:void description:
	 */
	private void showUserList(final String type) {
		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.custom_schedule_spinner_adapater);
		TextView titleLabel = (TextView) dialog.findViewById(R.id.titleLabel);
		ListView scheduleList = (ListView) dialog
				.findViewById(R.id.selectionList);

		scheduleList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (type.equals("user")) {
					userId = userList.get(position).userId;
					userButton.setText(userList.get(position).userName);

				} else {
					roleId = roleBeansList.get(position).roleId;
					roleButton.setText(roleBeansList.get(position).roleName);
				}
				dialog.dismiss();
			}
		});
		if (type.equals("user")) {
			scheduleList.setAdapter(new ScheduleUserListAdapter(context,
					userList));
		} else {
			scheduleList.setAdapter(new ScheuduleRoleAdapter(context,
					roleBeansList));

		}
		dialog.show();
	}

}
