/**
 * 
 */
package com.taskism.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tarsem.bean.UserBean;
import com.tarsem.constant.Constant;
import com.tarsem.control.ActivityController;
import com.task.taskApplication.R;
import com.task.taskApplication.UserListActivity;

/**
 * @author Manpreet
 * 
 */
public class UserListCustomAdapter extends BaseAdapter {

	private Context context;
	private List<UserBean> userBeansList;
	UserListActivity editUserActivity;

	/**
	 * 
	 */
	public UserListCustomAdapter(Context context, List<UserBean> userBeansList,
			UserListActivity editUserActivity) {
		this.context = context;
		this.userBeansList = userBeansList;
		this.editUserActivity = editUserActivity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return userBeansList.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.custom_user_list,
					null);

			viewHolder = new ViewHolder();
			viewHolder.userName = (TextView) convertView
					.findViewById(R.id.userName);
			viewHolder.deleteUser = (ImageView) convertView
					.findViewById(R.id.deleteUserImage);
			viewHolder.editUser = (ImageView) convertView
					.findViewById(R.id.editUserImage);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();

		}
		UserBean userBean = userBeansList.get(position);
		viewHolder.userName.setText(userBean.userName);
		viewHolder.deleteUser.setTag(position);
		viewHolder.deleteUser.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int pos = (Integer) v.getTag();
				UserBean userBean = userBeansList.get(pos);
				editUserActivity.showDeleteConfirmationPopup(userBean.userId,
						userBean.userName, pos);
			}
		});
		viewHolder.editUser.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ActivityController.startActivityController(
						Constant.EditUserActivity, null, editUserActivity,
						false);
			}
		});
		return convertView;
	}

	class ViewHolder {
		ImageView editUser, deleteUser;
		TextView userName;
	}

}
