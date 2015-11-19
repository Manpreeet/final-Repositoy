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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.task.taskApplication.R;
import com.taskism.adapter.ScheduleUserListAdapter;
import com.taskism.adapter.ScheuduleRoleAdapter;
import com.taskism.bean.RoleBean;
import com.taskism.bean.UserBean;
import com.taskism.constant.ApplicationConstant;
import com.taskism.constant.Constant;
import com.taskism.request.CommentsAsyncTask;
import com.taskism.request.GetUserListAsyncTask;
import com.taskism.request.RoleListAsyncTask;
import com.taskism.responsecallback.ResponseCallback;
import com.taskism.utility.Utility;

/**
 * @author Manpreet
 * 
 */
public class AddNewScheduleActivity extends ParentActivity {
	private Button roleButton, userButton;
	private TextView noUserFoundText, titleLabel;
	private Context context;
	private ParentActivity parentActivity;
	ProgressBar loadingBar;
	List<UserBean> userList;
	private LinearLayout userListParent;
	ScheduleUserListAdapter userListCustomAdapter;
	private int scheduleId = 0;
	private TextView startInput, stopInput;
	private DatePicker datePicker;
	private Calendar calendar;
	private int hour, minute, day, selectedId;
	private List<RoleBean> roleBeansList = null;
	String roleId, userId;
	private String am_pm;

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
					AddNewScheduleActivity.this, null, null);

		}

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
		startInput = (TextView) findViewById(R.id.startInput);
		stopInput = (TextView) findViewById(R.id.stopInput);

		titleLabel.setText("Add Schedule");
		/*
		 * descriptionInput = (EditText) findViewById(R.id.descriptionInput);
		 */
		loadingBar = (ProgressBar) findViewById(R.id.loadingProgress);
		userButton = (Button) findViewById(R.id.userSpinner);
		roleButton = (Button) findViewById(R.id.roleSpinner);

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

		startInput.setText("Select");

		stopInput.setText("Select");

		startInput.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				selectedId = 1;

				showDialog(1);
			}
		});
		stopInput.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				selectedId = 2;

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

	/***
	 * 
	 * developer:Manpreet date:09-Nov-2015 return:void description: method for
	 * perform event on tap of save
	 */
	public void onClickSave(View view) {

		// http://taskism.com/webservice001/?action=shiftnew&userid=62&roleid=1&start=2015-09-16%2013:00:00&stop=2015-09-16%2019:00:00&user=14

		new CommentsAsyncTask(ApplicationConstant.appurl + "shiftnew"
				+ "&userid=62" + "&roleid=" + roleId + "&start="
				+ startInput.getText().toString().trim() + "&stop="
				+ stopInput.getText().toString().trim() + "&user=" + userId,
				context, new ResponseCallback() {

					@Override
					public void onSuccessRecieve(Object object) {
						showToastMessage("successfully add new schedule");
					}

					@Override
					public void onErrorRecieve(Object object) {
						showToastMessage((String) object);
						// TODO Auto-generated method stub

					}
				}, null).execute();

	}

	/**
	 * developer:Manpreet date:19-Nov-2015 return:void description:
	 */
	protected void setDate(int hounrs, int minute) {
		String AM_PM = null;
		if (hounrs < 12) {
			AM_PM = "AM";
		} else {
			AM_PM = "PM";
		}

		if (selectedId == 1) {
			startInput.setText(new StringBuilder().append(hounrs).append(":")
					.append(minute).append(" " + am_pm));

		} else {

			stopInput.setText(new StringBuilder().append(hounrs).append(":")
					.append(minute).append(" " + am_pm));

		}

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
			titleLabel.setText("Select User");
			scheduleList.setAdapter(new ScheduleUserListAdapter(context,
					userList));
		} else {
			titleLabel.setText("Select Role");
			scheduleList.setAdapter(new ScheuduleRoleAdapter(context,
					roleBeansList));

		}
		dialog.show();
	}
}
