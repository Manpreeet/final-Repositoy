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
import com.taskism.adapter.CustomEditUserRoleAdapter;
import com.taskism.bean.RoleBean;
import com.taskism.constant.ApplicationConstant;
import com.taskism.constant.Constant;
import com.taskism.control.ActivityController;
import com.taskism.request.DeleteAsyncTask;
import com.taskism.request.RoleListAsyncTask;
import com.taskism.responsecallback.ResponseCallback;
import com.taskism.utility.Utility;

/**
 * @author Manpreet
 * 
 */
public class EditRoleActivity extends ParentActivity {
	ParentActivity parentActivity;
	Context context;

	private ListView edituserRoleListView;
	private CustomEditUserRoleAdapter customEditUserRoleAdapter;
	private ProgressBar loadingProgress;
	private SwipeRefreshLayout swipeRefresh;
	private TextView noRecordFoundText;
	List<RoleBean> roleBeansList;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.task.taskApplication.ParentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_edit_user_role);
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
		edituserRoleListView = (ListView) findViewById(R.id.editUserRoleListView);
		loadingProgress = (ProgressBar) findViewById(R.id.loadingProgress);
		swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
		swipeRefresh.setColorSchemeColors(Color.RED, Color.BLUE, Color.CYAN,
				Color.BLACK);
		roleBeansList = new ArrayList<RoleBean>();
		customEditUserRoleAdapter = new CustomEditUserRoleAdapter(context,
				roleBeansList, parentActivity);
		edituserRoleListView.setAdapter(customEditUserRoleAdapter);
		try {
			getSideMenu(EditRoleActivity.this);

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
		refreshView();
		if (isConnectedToInternet()) {
			loadingProgress.setVisibility(View.VISIBLE);
			if (roleBeansList.size() != 0) {
				roleBeansList.clear();
			}
			fetchRoleList();

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
							fetchRoleList();
						} else {
							swipeRefresh.setRefreshing(false);
							new Utility().showCustomDialog(
									Constant.internetConnectionPopupButtonText,
									Constant.internetConnectionTitle,
									Constant.internetConnectionMessage, false,
									EditRoleActivity.this, null, null);
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
	private void fetchRoleList() {
		swipeRefresh.setVisibility(View.GONE);
		loadingProgress.setVisibility(View.VISIBLE);

		new RoleListAsyncTask(
				ApplicationConstant.appurl + "rolelist&userid=62", context,
				new ResponseCallback() {

					@Override
					public void onSuccessRecieve(Object object) {
						roleBeansList = (List<RoleBean>) object;
						customEditUserRoleAdapter
								.updatenewUserRoleList(roleBeansList);
						swipeRefresh.setVisibility(View.VISIBLE);

						loadingProgress.setVisibility(View.GONE);

					}

					@Override
					public void onErrorRecieve(Object object) {
						swipeRefresh.setVisibility(View.VISIBLE);
						loadingProgress.setVisibility(View.GONE);

					}
				}).execute();

	}

	/**
	 * 
	 * developer:Manpreet date:03-Oct-2015 return:void description: method for
	 * open delete confirmation popup
	 */
	public void showDeleteConfirmationPopup(final String roleId,
			String userNameValue, final int position) {

		new Utility().showCustomDialog("Delete", "Delete Role",
				"Are you sure you want to delete: " + userNameValue, false,
				EditRoleActivity.this, null, new ResponseCallback() {

					@Override
					public void onSuccessRecieve(Object object) {
						deleteRole(roleId, position);
					}

					@Override
					public void onErrorRecieve(Object object) {
						// TODO Auto-generated method stub

					}
				});

	}

	/**
	 * 
	 * developer:Manpreet date:02-Oct-2015 return:void description: method for
	 * delete userId
	 */
	public void deleteRole(String roleId, final int position) {
		if (isConnectedToInternet()) {
			loadingProgress.setVisibility(View.VISIBLE);
			new DeleteAsyncTask(ApplicationConstant.appurl + "roledelete"
					+ "&roleid=" + roleId, context, new ResponseCallback() {

				@Override
				public void onSuccessRecieve(Object object) {
					roleBeansList.remove(position);
					customEditUserRoleAdapter
							.updatenewUserRoleList(roleBeansList);
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
					EditRoleActivity.this, null, null);

		}

	}

	/**
	 * 
	 * developer:Manpreet date:08-Nov-2015 return:void description: method for
	 * add Role
	 */
	public void onClickAddRule(View view) {
		ActivityController.startActivityController(Constant.AddNewRoleActivity,
				null, EditRoleActivity.this, false);

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