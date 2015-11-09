package com.task.taskApplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tarsem.bean.RoleListBean;
import com.tarsem.constant.ApplicationConstant;
import com.tarsem.constant.Constant;
import com.tarsem.request.RoleListAsyncTask;
import com.tarsem.request.ServerAsyncTask;
import com.tarsem.responsecallback.ResponseCallback;
import com.tarsem.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

public class EditTaskInfoActivity extends ParentActivity {
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
	private int year, month, day;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_task_info);
		context = this;
		findAttributesId();
		initData();
		startRoleListService();

	}

	private void findAttributesId() {

		checkParentLayout = (LinearLayout) findViewById(R.id.linearLayCheckBoxRole);
		onceInput = (TextView) findViewById(R.id.onceInput);
		taskNameInput = (EditText) findViewById(R.id.taskNameInput);
		instructionsInput = (EditText) findViewById(R.id.instructionsInput);
		buttonSave = (Button) findViewById(R.id.buttonSave);
		buttonAddImage = (Button) findViewById(R.id.buttonAddImage);

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

	private void initData() {
		progressDialog = new ProgressDialog(EditTaskInfoActivity.this);
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
			startEditRoleInfoService();

		} else {
			new Utility().showCustomDialog(
					Constant.internetConnectionPopupButtonText,
					Constant.internetConnectionTitle,
					Constant.internetConnectionMessage, false,
					EditTaskInfoActivity.this, null, null);

		}
	}

	private void startTaskInfoServiceRequest() {
		new RoleListAsyncTask(ApplicationConstant.appurl
				+ ApplicationConstant.taskinfoRequestType + "&userid=62"
				+ "&taskid=" + "317", context, new ResponseCallback() {

			@Override
			public void onSuccessRecieve(Object object) {

				progressDialog.dismiss();
				Log.i("New Task", "Edit TaskInfo---" + object.toString());
				fetchTaskInfoData(object);
				showToastMessage("Successfully register on app"
						+ object.toString());

			}

			@Override
			public void onErrorRecieve(Object object) {

				progressDialog.dismiss();
				showToastMessage((String) object);
			}
		}).execute();

	}

	private void startEditRoleInfoService() {
		new ServerAsyncTask(ApplicationConstant.appurl
				+ ApplicationConstant.taskeditRequestType + "&userid=62"
				+ "&name=" + taskNameInput.getText().toString().trim()
				+ "&once=" + onceInput.getText().toString().trim() + "&roles="
				+ getSelectedRolesValue() + "&monthly="
				+ getMonthlySelectedValue() + "&taskid=317", context,
				new ResponseCallback() {

					@Override
					public void onSuccessRecieve(Object object) {

						progressDialog.dismiss();
						Log.i("New Task", "New Task---" + object.toString());
						showToastMessage("Successfully register on app");
						finish();
					}

					@Override
					public void onErrorRecieve(Object object) {

						progressDialog.dismiss();
						showToastMessage((String) object);
					}
				}, ApplicationConstant.taskeditRequestType).execute();

	}

	private void fetchTaskInfoData(Object object) {

		try {
			JSONObject jsonObject = new JSONObject(object.toString());
			String name = jsonObject.optString("name");
			String monthly = jsonObject.optString("monthly");
			String instruct = jsonObject.optString("instruct");
			String roles = jsonObject.optString("roles");
			JSONArray jsonArray = jsonObject.getJSONArray("once");
			// for(int index =0; index <jsonArray.length();index++){
			JSONObject jsOBj = jsonArray.getJSONObject(0);
			String scheduleid = jsOBj.optString("scheduleid");
			String datetext = jsOBj.optString("datetext");

			// }

			taskNameInput.setText(name);
			onceInput.setText(datetext);
			setRolesData(roles);
			setMonthlyData(monthly);

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void setRolesData(String tempData) {
		String roles[];
		if (tempData.contains(",")) {
			roles = tempData.split(",");
			for (int index = 0; index < roles.length; index++)
				setRoleChecked(roles[index].trim().toString());
		} else {
			setRoleChecked(tempData.trim().toString());
		}
	}

	private void setRoleChecked(String temp) {
		for (int index = 0; index < roleIndexValue.length; index++) {
			if (roleIndexValue[index].trim().toString().equalsIgnoreCase(temp)) {
				roles[index].setChecked(true);
			}
		}
	}

	private void setMonthlyData(String tempData) {
		String roles[];
		if (tempData.contains(",")) {
			roles = tempData.split(",");
			for (int index = 0; index < roles.length; index++) {
				setMonthChecked(roles[index].trim().toString());

			}
		} else {
			setMonthChecked(tempData.trim().toString());
		}
	}

	private void setMonthChecked(String temp) {
		for (int index = 0; index < monthIndexValue.length; index++) {
			if (monthIndexValue[index].trim().toString().equalsIgnoreCase(temp)) {
				monthly[index].setChecked(true);
				break;
			}
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
		onceInput.setText(new StringBuilder().append(day).append("/")
				.append(month).append("/").append(year));
	}

	private void startRoleListService() {
		// progressDialog.show();
		new RoleListAsyncTask(ApplicationConstant.appurl
				+ ApplicationConstant.roleListRequestType + "&userid=62",
				context, new ResponseCallback() {

					@Override
					public void onSuccessRecieve(Object object) {
						List<RoleListBean> list = (List<RoleListBean>) object;
						progressDialog.dismiss();
						Log.i("New Task",
								" Task Info data---" + list.toString());
						setRoleListData(list);
						// showToastMessage("Successfully fetch role list data"
						// +list);
						// finish();
					}

					@Override
					public void onErrorRecieve(Object object) {

						progressDialog.dismiss();
						showToastMessage((String) object);
					}
				}).execute();
	}

	private void setRoleListData(List<RoleListBean> roleList) {
		LayoutInflater inflater = LayoutInflater.from(this);
		roleIndexValue = new String[roleList.size()];
		roles = new CheckBox[roleList.size()];
		for (int i = 0; i < roleList.size(); i++) {
			View view = inflater.inflate(
					R.layout.layout_role_list_checkbx_item, null);
			// View hiddenInfo =
			// getLayoutInflater().inflate(R.layout.layout_role_list_checkbx_item,
			// checkParentLayout, false);
			CheckBox check = (CheckBox) view.findViewById(R.id.checkBoxItem);
			check.setId(i);
			roles[i] = check;
			check.setText(roleList.get(i).getName());
			roleIndexValue[i] = roleList.get(i).getRoleId();
			checkParentLayout.addView(view);
		}
		startTaskInfoServiceRequest();
	}

}
