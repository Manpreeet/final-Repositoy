package com.taskism.taskApplication;

import java.util.Calendar;
import java.util.List;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.task.taskApplication.R;
import com.taskism.bean.RoleBean;
import com.taskism.constant.ApplicationConstant;
import com.taskism.constant.Constant;
import com.taskism.control.ActivityController;
import com.taskism.request.AddNewTaskAsyncTask;
import com.taskism.request.RoleListAsyncTask;
import com.taskism.request.ServerAsyncTask;
import com.taskism.responsecallback.ResponseCallback;
import com.taskism.utility.Utility;

public class NewTaskActivity extends ParentActivity {
	private EditText taskNameInput;
	private EditText instructionsInput;
	private CheckBox roles[];
	private CheckBox monthly[];
	private CheckBox weekDay[];
	private String monthIndexValue[];
	private String roleIndexValue[];
	private String weekIndexValue[];
	private Button buttonSave;
	private Button buttonAddImage;
	private TextView onceInput;
	private DatePicker datePicker;
	private Calendar calendar;
	private ProgressDialog progressDialog;
	private LinearLayout checkParentLayout;

	Context context;
	private int year, month, day, taskId;
	private boolean addImageStatus = false;
	private ScrollView taskScrollView;
	private ListView imageStepList;
	private Button taskButton, imageButton;
	private boolean imageSelectStatus = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_task);
		context = this;
		findAttributesId();
		initData();
		fetchRoleList();
	}

	private void findAttributesId() {

		checkParentLayout = (LinearLayout) findViewById(R.id.linearLayCheckBoxRole);
		onceInput = (TextView) findViewById(R.id.onceInput);
		taskNameInput = (EditText) findViewById(R.id.taskNameInput);
		instructionsInput = (EditText) findViewById(R.id.instructionsInput);
		buttonSave = (Button) findViewById(R.id.buttonSave);
		buttonAddImage = (Button) findViewById(R.id.buttonAddImage);
		taskScrollView = (ScrollView) findViewById(R.id.taskScrollView);
		taskButton = (Button) findViewById(R.id.taskButton);
		imageButton = (Button) findViewById(R.id.imageButton);
		imageStepList = (ListView) findViewById(R.id.taskStepList);
		/*
		 * roles[0] = (CheckBox) findViewById(R.id.checkBoxBarBack); roles[1] =
		 * (CheckBox) findViewById(R.id.checkBoxBartender); roles[2] =
		 * (CheckBox) findViewById(R.id.checkBoxBottleService); roles[3] =
		 * (CheckBox) findViewById(R.id.checkBoxCleaning); roles[4] = (CheckBox)
		 * findViewById(R.id.checkBoxCoatCheck); roles[5] = (CheckBox)
		 * findViewById(R.id.checkBoxManager); roles[6] = (CheckBox)
		 * findViewById(R.id.checkBoxMarketing);
		 */

		weekDay = new CheckBox[7];
		weekIndexValue = new String[7];
		weekDay[0] = (CheckBox) findViewById(R.id.checkBoxSu);
		weekDay[1] = (CheckBox) findViewById(R.id.checkBoxMo);
		weekDay[2] = (CheckBox) findViewById(R.id.checkBoxTu);
		weekDay[3] = (CheckBox) findViewById(R.id.checkBoxWe);
		weekDay[4] = (CheckBox) findViewById(R.id.checkBoxTh);
		weekDay[5] = (CheckBox) findViewById(R.id.checkBoxFr);
		weekDay[6] = (CheckBox) findViewById(R.id.checkBoxSa);

		monthly = new CheckBox[35];
		monthIndexValue = new String[35];
		monthly[0] = (CheckBox) findViewById(R.id.checkBox0);
		monthly[1] = (CheckBox) findViewById(R.id.checkBox1);
		monthly[2] = (CheckBox) findViewById(R.id.checkBox2);
		monthly[3] = (CheckBox) findViewById(R.id.checkBox3);
		monthly[4] = (CheckBox) findViewById(R.id.checkBox4);
		monthly[5] = (CheckBox) findViewById(R.id.checkBox5);
		monthly[6] = (CheckBox) findViewById(R.id.checkBox6);
		monthly[7] = (CheckBox) findViewById(R.id.checkBox7);
		monthly[8] = (CheckBox) findViewById(R.id.checkBox8);
		monthly[9] = (CheckBox) findViewById(R.id.checkBox9);
		monthly[10] = (CheckBox) findViewById(R.id.checkBox10);
		monthly[11] = (CheckBox) findViewById(R.id.checkBox11);
		monthly[12] = (CheckBox) findViewById(R.id.checkBox12);
		monthly[13] = (CheckBox) findViewById(R.id.checkBox13);
		monthly[14] = (CheckBox) findViewById(R.id.checkBox14);
		monthly[15] = (CheckBox) findViewById(R.id.checkBox15);
		monthly[16] = (CheckBox) findViewById(R.id.checkBox16);
		monthly[17] = (CheckBox) findViewById(R.id.checkBox17);
		monthly[18] = (CheckBox) findViewById(R.id.checkBox18);
		monthly[19] = (CheckBox) findViewById(R.id.checkBox19);
		monthly[20] = (CheckBox) findViewById(R.id.checkBox20);
		monthly[21] = (CheckBox) findViewById(R.id.checkBox21);
		monthly[22] = (CheckBox) findViewById(R.id.checkBox22);
		monthly[23] = (CheckBox) findViewById(R.id.checkBox23);
		monthly[24] = (CheckBox) findViewById(R.id.checkBox24);
		monthly[25] = (CheckBox) findViewById(R.id.checkBox25);
		monthly[26] = (CheckBox) findViewById(R.id.checkBox26);
		monthly[27] = (CheckBox) findViewById(R.id.checkBox27);
		monthly[28] = (CheckBox) findViewById(R.id.checkBox28);
		monthly[29] = (CheckBox) findViewById(R.id.checkBox29);
		monthly[30] = (CheckBox) findViewById(R.id.checkBox30);
		monthly[31] = (CheckBox) findViewById(R.id.checkBox31);
		monthly[32] = (CheckBox) findViewById(R.id.checkBox32);
		monthly[33] = (CheckBox) findViewById(R.id.checkBox33);
		monthly[34] = (CheckBox) findViewById(R.id.checkBox34);

		onceInput.setOnClickListener(new View.OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				showDialog(999);
			}
		});
		buttonSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				validateNewTask();
			}
		});
	}

	private void validateNewTask() {
		if (taskNameInput.getText().toString().length() == 0) {
			showToastMessage("Please enter Task Name!");
			return;
		}
		if (getSelectedRolesValue().length() == 0) {
			showToastMessage("Please select at least one Role!");
			return;
		}
		if (getMonthlySelectedValue().length() == 0) {
			showToastMessage("Please select at least one Date!");
			return;
		}

		if (isConnectedToInternet()) {
			progressDialog.show();
			sendNewTaskRequestToServer();

		} else {
			new Utility().showCustomDialog(
					Constant.internetConnectionPopupButtonText,
					Constant.internetConnectionTitle,
					Constant.internetConnectionMessage, false,
					NewTaskActivity.this, null, null);

		}
	}

	/**
	 * 
	 * developer:manpreets2 date:Nov 3, 2015 return:void description: method foe
	 */
	public void onCLickAddImage(View view) {

		if (addImageStatus) {
			Bundle bundle = new Bundle();
			bundle.putInt(Constant.taskId, taskId);
			ActivityController.startActivityController(
					Constant.AddNewImageActivity, bundle, NewTaskActivity.this,
					false);

		} else {
			new Utility().showCustomDialog(
					Constant.internetConnectionPopupButtonText,
					Constant.addNewTaskDialogTitle,
					Constant.addNewTaskDialogErrorMessage, false,
					NewTaskActivity.this, null, null);
		}

	}

	/**
	 * 
	 * developer:manpreets2 date:Nov 3, 2015 return:void description: method for
	 * set task request on server
	 */
	private void sendNewTaskRequestToServer() {
		// http://taskism.com/webservice001/?action=tasknew&userid=62&name=CallAriel&once=2015-08-27&roles=7,11&monthly=34,11,26,30,41,51

		new AddNewTaskAsyncTask(ApplicationConstant.appurl
				+ ApplicationConstant.newTaskRequestType + "&userid=62"
				+ "&name=" + taskNameInput.getText().toString().trim()
				+ "&once=" + onceInput.getText().toString().trim() + "&roles="
				+ getSelectedRolesValue() + "&monthly="
				+ getMonthlySelectedValue(), context, new ResponseCallback() {

			@Override
			public void onSuccessRecieve(Object object) {

				progressDialog.dismiss();
				taskId = (Integer) object;
				new Utility().showCustomDialog(
						Constant.internetConnectionPopupButtonText,
						Constant.addNewTaskDialogTitle,
						Constant.addNewTaskDialogDescription, false,
						NewTaskActivity.this, null, new ResponseCallback() {

							@Override
							public void onSuccessRecieve(Object object) {

								addImageStatus = true;
								Bundle bundle = new Bundle();
								bundle.putInt(Constant.taskId, taskId);
								ActivityController.startActivityController(
										Constant.AddNewImageActivity, bundle,
										NewTaskActivity.this, false);

							}

							@Override
							public void onErrorRecieve(Object object) {

							}
						});

				/*
				 * Log.i("New Task", "New Task---" + object.toString());
				 * showToastMessage("Successfully register on app");
				 */
				// finish();
			}

			@Override
			public void onErrorRecieve(Object object) {

				progressDialog.dismiss();
				showToastMessage((String) object);
			}
		}, ApplicationConstant.newTaskRequestType).execute();

	}

	/**
	 * 
	 * developer:manpreets2 date:Nov 3, 2015 return:void description: method for
	 * perform event on tap of image
	 */
	public void onClickImage(View view) {
		try {
			taskScrollView.setVisibility(View.GONE);
			imageStepList.setVisibility(View.VISIBLE);
			imageButton.setTextColor(Color.WHITE);
			imageButton.setBackgroundColor(Color.BLACK);
			taskButton.setBackgroundColor(Color.WHITE);
			taskButton.setTextColor(Color.BLACK);

			if (!imageSelectStatus) {
				fetchTaskStepList();
				imageSelectStatus = true;
			}

		} catch (Exception e) {

		}

	}

	/**
	 * developer:manpreets2 date:Nov 3, 2015 return:void description: method for
	 * fetch task step list from server
	 */
	private void fetchTaskStepList() {
		try {

			// http://taskism.com/webservice001/?action=tasksteplist&userid=62&taskid=223
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 
	 * developer:manpreets2 date:Nov 3, 2015 return:void description: method for
	 * perform event on tap of task
	 */
	public void onClickTask(View view) {
		try {
			taskScrollView.setVisibility(View.VISIBLE);
			imageStepList.setVisibility(View.GONE);
			taskButton.setTextColor(Color.WHITE);
			taskButton.setBackgroundColor(Color.BLACK);
			imageButton.setBackgroundColor(Color.WHITE);
			imageButton.setTextColor(Color.BLACK);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		if (id == 999) {
			return new DatePickerDialog(this, myDateListener, year, month, day);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			// arg1 = year
			// arg2 = month
			// arg3 = day
			setOnceDate(arg1, arg2 + 1, arg3);
		}
	};

	private void setOnceDate(int year, int month, int day) {
		onceInput.setText(new StringBuilder().append(year).append("-")
				.append(month).append("-").append(day));
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

	/**
	 * 
	 * developer:manpreets2 date:Nov 3, 2015 return:void description: method for
	 * init data
	 */
	private void initData() {
		progressDialog = new ProgressDialog(NewTaskActivity.this);
		progressDialog.setMessage("Please Wait.......");
		// -----For monthly Data
		int row = 1;
		int column = 0;
		String tag = "";
		for (int index = 0; index < 35; index++) {
			if (index == 7) {
				row = 2;
				column = 0;
			}
			if (index == 14) {
				row = 3;
				column = 0;
			}
			if (index == 21) {
				row = 4;
				column = 0;
			}

			if (index == 28) {
				row = 5;
				column = 0;
			}
			tag = row + "" + column;
			monthIndexValue[index] = tag;
			column++;
			Log.i("Monthly", index + " index--tag--Value:"
					+ monthIndexValue[index]);

			// For Weekly
			weekIndexValue[0] = "Su";
			weekIndexValue[1] = "Mo";
			weekIndexValue[2] = "Tu";
			weekIndexValue[3] = "We";
			weekIndexValue[4] = "Th";
			weekIndexValue[5] = "Fr";
			weekIndexValue[6] = "Sa";

			calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);

			month = calendar.get(Calendar.MONTH);
			day = calendar.get(Calendar.DAY_OF_MONTH);
			setOnceDate(year, month + 1, day);
		}
	}

	private String getMonthlySelectedValue() {
		String checkedValue = "";
		for (int index = 0; index < 35; index++) {
			if (monthly[index].isChecked()) {
				if (!checkedValue.equalsIgnoreCase("")) {
					checkedValue += "," + monthIndexValue[index];
				} else {
					checkedValue += monthIndexValue[index];
				}
			}
		}
		Log.i("Monthly", "Checked--monthly Value:" + checkedValue);
		return checkedValue;
	}

	private String getSelectedRolesValue() {
		String checkedRoles = "";
		for (int index = 0; index < 7; index++) {
			if (roles[index].isChecked()) {
				if (!checkedRoles.equalsIgnoreCase("")) {
					checkedRoles += "," + index;
				} else {
					checkedRoles += "" + index;
				}
			}
		}
		Log.i("Monthly", "Checked--Roles Value:" + checkedRoles);

		return checkedRoles;
	}

	/*
	 * private String getSelectedWeeksValue() { String checkedWeeksValue = "";
	 * for (int index = 0; index < 7; index++) { if (roles[index].isChecked()) {
	 * if (!checkedWeeksValue.equalsIgnoreCase("")) { checkedWeeksValue += "," +
	 * weekDay[index]; } else { checkedWeeksValue += "" + weekDay[index]; } } }
	 * Log.i("Monthly", "Checked--checkedWeeksValue:" + checkedWeeksValue);
	 * 
	 * return checkedWeeksValue; }
	 */

	/**
	 * 
	 * developer:manpreets2 date:Nov 3, 2015 return:void description: method for
	 * fetch role list from server
	 */
	private void fetchRoleList() {
		// progressDialog.show();

		new RoleListAsyncTask(ApplicationConstant.appurl
				+ ApplicationConstant.roleListRequestType + "&userid=62",
				context, new ResponseCallback() {

					@Override
					public void onSuccessRecieve(Object object) {
						List<RoleBean> list = (List<RoleBean>) object;

						Log.i("New Task", "New Task---" + list.toString());
						bindRoleInformation(list);
						progressDialog.dismiss();
					}

					@Override
					public void onErrorRecieve(Object object) {
						progressDialog.dismiss();
						showToastMessage((String) object);

					}
				}).execute();

	}

	/**
	 * 
	 * developer:manpreets2 date:Nov 3, 2015 return:void description: method for
	 * bind role information
	 */
	private void bindRoleInformation(List<RoleBean> roleList) {
		LayoutInflater inflater = LayoutInflater.from(this);
		roleIndexValue = new String[roleList.size()];
		roles = new CheckBox[roleList.size()];
		try {
			for (int i = 0; i < roleList.size(); i++) {
				View view = inflater.inflate(
						R.layout.layout_role_list_checkbx_item, null);
				CheckBox check = (CheckBox) view
						.findViewById(R.id.checkBoxItem);
				check.setId(i);
				roles[i] = check;
				check.setText(roleList.get(i).roleName);
				roleIndexValue[i] = roleList.get(i).roleId;
				checkParentLayout.addView(view);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
