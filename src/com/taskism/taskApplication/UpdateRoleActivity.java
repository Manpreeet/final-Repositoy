/**
 * 
 */
package com.taskism.taskApplication;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chiralcode.colorpicker.ColorPickerDialog;
import com.chiralcode.colorpicker.ColorPickerDialog.OnColorSelectedListener;
import com.task.taskApplication.R;
import com.taskism.adapter.UserListCustomAdapter;
import com.taskism.bean.EditRoleBean;
import com.taskism.bean.UserBean;
import com.taskism.constant.ApplicationConstant;
import com.taskism.constant.Constant;
import com.taskism.request.EditRoleAsyncTask;
import com.taskism.request.GetUserListAsyncTask;
import com.taskism.responsecallback.ResponseCallback;
import com.taskism.utility.Utility;

/**
 * @author Manpreet
 * 
 */
public class UpdateRoleActivity extends ParentActivity {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.task.taskApplication.ParentActivity#onCreate(android.os.Bundle)
	 */
	private Button colorInput;
	private TextView noUserFoundText, titleLabel;
	private Context context;
	private ParentActivity parentActivity;
	ProgressBar loadingBar;
	List<UserBean> taskListBeans;
	private LinearLayout userListParent;
	UserListCustomAdapter userListCustomAdapter;
	private EditText nameInput, descriptionInput;
	private int roleId = 0;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		setContentView(R.layout.activity_add_new_role);
		findAttributesId();
		getIntentData(bundle);

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
			roleId = bundle.getInt(Constant.userid);
			if (isConnectedToInternet()) {
				loadingBar.setVisibility(View.VISIBLE);
				// getUserList();
				getRoleInformation(roleId);
			} else {
				noUserFoundText.setVisibility(View.VISIBLE);
				new Utility().showCustomDialog(
						Constant.internetConnectionPopupButtonText,
						Constant.internetConnectionTitle,
						Constant.internetConnectionMessage, false,
						UpdateRoleActivity.this, null, null);
			}

		}
	}

	/**
	 * developer:manpreets2 date:Nov 9, 2015 return:void description: method for
	 * fetch role information from server
	 */
	private void getRoleInformation(int roleId2) {
		new EditRoleAsyncTask(ApplicationConstant.appurl
				+ "roleinfo&userid=62&roleid=" + roleId2, context,
				new ResponseCallback() {

					@Override
					public void onSuccessRecieve(Object object) {
						EditRoleBean editRoleBean = (EditRoleBean) object;
						nameInput.setText(editRoleBean.roleName);
						descriptionInput.setText(editRoleBean.roleDescription);
						colorInput.setBackgroundColor(Color
								.parseColor(editRoleBean.colorCode));
						colorInput.setText(editRoleBean.colorCode.split("#")[1]);
						displayUserList(editRoleBean.userBeanList);
						loadingBar.setVisibility(View.GONE);
					}

					@Override
					public void onErrorRecieve(Object object) {
						loadingBar.setVisibility(View.GONE);

					}
				}).execute();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		/*
		 * if (isConnectedToInternet()) {
		 * loadingBar.setVisibility(View.VISIBLE); // getUserList(); } else {
		 * noUserFoundText.setVisibility(View.VISIBLE); new
		 * Utility().showCustomDialog(
		 * Constant.internetConnectionPopupButtonText,
		 * Constant.internetConnectionTitle, Constant.internetConnectionMessage,
		 * false, UpdateRoleActivity.this, null, null); }
		 */
	}

	/**
	 * developer:Manpreet date:09-Nov-2015 return:void description: method for
	 * find attributes id
	 */
	private void findAttributesId() {
		context = this;
		parentActivity = this;
		nameInput = (EditText) findViewById(R.id.nameInput);
		titleLabel = (TextView) findViewById(R.id.commentsTitle);
		titleLabel.setText("Edit Role");
		descriptionInput = (EditText) findViewById(R.id.descriptionInput);
		colorInput = (Button) findViewById(R.id.colorInput);
		loadingBar = (ProgressBar) findViewById(R.id.loadingProgress);
		userListParent = (LinearLayout) findViewById(R.id.userParent);

	}

	/**
	 * 
	 * developer:Manpreet date:09-Nov-2015 return:void description: method for
	 * open color picker diialog
	 */
	public void openDialog(View view) {
		int initialColor = Color.GREEN;

		ColorPickerDialog colorPickerDialog = new ColorPickerDialog(this,
				initialColor, new OnColorSelectedListener() {

					@Override
					public void onColorSelected(int color) {

						colorInput.setText(convertHexFormat(color));
						colorInput.setBackgroundColor(Color.rgb(
								Color.red(color), Color.green(color),
								Color.blue(color)));
					}

				});
		colorPickerDialog.show();

	}

	/**
	 * developer:Manpreet date:09-Nov-2015 return:void description: method for
	 * display user list in view
	 */
	protected void displayUserList(List<UserBean> taskListBeans2) {
		for (int i = 0; i < taskListBeans2.size(); i++) {
			UserBean bean = taskListBeans2.get(i);

			LayoutInflater inflater = LayoutInflater.from(context);
			View view = inflater.inflate(R.layout.custom_role_list, null);

			TextView roleTitle = (TextView) view.findViewById(R.id.roleTitle);
			if (i == 0) {
				roleTitle.setText("Users");
				roleTitle.setTextColor(Color.BLACK);
				roleTitle.setVisibility(View.VISIBLE);
			} else {
				roleTitle.setVisibility(View.INVISIBLE);

			}
			TextView roleName = (TextView) view.findViewById(R.id.roleName);
			CheckBox roleStatus = (CheckBox) view.findViewById(R.id.roleStatus);
			if (bean.checkedStatus) {
				roleStatus.setChecked(true);
			} else {
				roleStatus.setChecked(false);
			}
			roleName.setText(bean.userName);
			userListParent.addView(view);
		}

	}

	/***
	 * 
	 * developer:Manpreet date:09-Nov-2015 return:void description: method for
	 * perform event on tap of save
	 */
	public void onClickSave(View view) {

	}

	/**
	 * 
	 * developer:Manpreet date:09-Nov-2015 return:String description: method for
	 * convert rgb color into hex format
	 */
	private String convertHexFormat(int colorCode) {

		final StringBuilder builder = new StringBuilder();

		builder.append(Integer.toHexString(Color.red(colorCode)));
		builder.append(Integer.toHexString(Color.green(colorCode)));
		builder.append(Integer.toHexString(Color.blue(colorCode)));
		return builder.toString();
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
}
