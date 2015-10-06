/**
 * 
 */
package com.task.taskApplication;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tarsem.bean.UserBean;
import com.tarsem.constant.ApplicationConstant;
import com.tarsem.constant.Constant;
import com.tarsem.request.DeleteAsyncTask;
import com.tarsem.request.GetUserListAsyncTask;
import com.tarsem.responsecallback.ResponseCallback;
import com.tarsem.utility.Utility;
import com.taskism.adapter.UserListCustomAdapter;

/**
 * @author Manpreet
 * 
 */
public class UserListActivity extends ParentActivity {

	ProgressBar loadingBar;
	Context context;
	ParentActivity parentActivity;
	private TextView noUserFoundText;
	List<UserBean> taskListBeans;
	private ListView userListView;
	UserListCustomAdapter userListCustomAdapter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.task.taskApplication.ParentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_user_list);
		findAttributesId();
	}

	/**
	 * developer:Manpreet date:02-Oct-2015 return:void description: method for
	 * findAttrbitues Id's
	 */
	private void findAttributesId() {
		parentActivity = this;
		context = this;
		userListView = (ListView) findViewById(R.id.userList);
		loadingBar = (ProgressBar) findViewById(R.id.loadingProgress);
		noUserFoundText = (TextView) findViewById(R.id.noUserFoundText);
		try {
			getSideMenu(UserListActivity.this);
		} catch (Exception e) {
			e.printStackTrace();
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
		if (isConnectedToInternet()) {
			loadingBar.setVisibility(View.VISIBLE);
			getUserList();
		} else {
			new Utility().showCustomDialog("Ok", "Internet Connection",
					"no internet connection", false, UserListActivity.this,
					null, null);
		}

	}

	/**
	 * developer:Manpreet date:02-Oct-2015 return:void description:method for
	 * get user detail from server
	 */
	private void getUserList() {
		new GetUserListAsyncTask(ApplicationConstant.appurl
				+ ApplicationConstant.getUserListRequest + "&userid="
				+ Constant.getLoggedUserId(context), context,
				new ResponseCallback() {

					@Override
					public void onSuccessRecieve(Object object) {
						taskListBeans = (List<UserBean>) object;
						userListCustomAdapter = new UserListCustomAdapter(
								context, taskListBeans, UserListActivity.this);
						userListView.setAdapter(userListCustomAdapter);
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
	 * 
	 * developer:Manpreet date:03-Oct-2015 return:void description: method for
	 * open delete confirmation popup
	 */
	public void showDeleteConfirmationPopup(final String userId,
			String userNameValue, final int position) {

		new Utility().showCustomDialog("Delete", "Delete User",
				"Are you sure you want to delete: " + userNameValue, false,
				UserListActivity.this, null, new ResponseCallback() {

					@Override
					public void onSuccessRecieve(Object object) {
						deleteUser(userId, position);
					}

					@Override
					public void onErrorRecieve(Object object) {
						// TODO Auto-generated method stub

					}
				});

		/*
		 * final Dialog dialog = new Dialog(context);
		 * dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		 * dialog.setContentView(R.layout.custom_dialog);
		 * dialog.setCanceledOnTouchOutside(false); TextView userName =
		 * (TextView) dialog.findViewById(R.id.userName); Button doneButton =
		 * (Button) dialog.findViewById(R.id.deleteButton); Button cancelButton
		 * = (Button) dialog.findViewById(R.id.cancelButton);
		 * userName.setText(userName.getText().toString() + " " +
		 * userNameValue); cancelButton.setOnClickListener(new OnClickListener()
		 * {
		 * 
		 * @Override public void onClick(View v) { dialog.dismiss(); } });
		 * doneButton.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { deleteUser(userId, position);
		 * dialog.dismiss(); } });
		 * 
		 * dialog.show();
		 */

	}

	/**
	 * 
	 * developer:Manpreet date:02-Oct-2015 return:void description: method for
	 * delete userId
	 */
	public void deleteUser(String userId, final int position) {
		if (isConnectedToInternet()) {
			loadingBar.setVisibility(View.VISIBLE);
			new DeleteAsyncTask(ApplicationConstant.appurl
					+ ApplicationConstant.deleteUserRequest + "&userid="
					+ Constant.getLoggedUserId(context), context,
					new ResponseCallback() {

						@Override
						public void onSuccessRecieve(Object object) {
							taskListBeans.remove(position);
							userListCustomAdapter.notifyDataSetChanged();
							loadingBar.setVisibility(View.GONE);
						}

						@Override
						public void onErrorRecieve(Object object) {
							loadingBar.setVisibility(View.GONE);
							showToastMessage((String) object);
						}
					}).execute();

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
