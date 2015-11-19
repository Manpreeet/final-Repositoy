/**
 * 
 */
package com.taskism.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.task.taskApplication.R;
import com.taskism.bean.UserBean;
import com.taskism.taskApplication.AddNewScheduleActivity;

/**
 * @author Manpreet
 * 
 */
public class ScheduleUserListAdapter extends BaseAdapter {

	private Context context;
	private List<UserBean> userBeansList;
	AddNewScheduleActivity editUserActivity;

	/**
	 * 
	 */
	public ScheduleUserListAdapter(Context context,
			List<UserBean> userBeansList) {
		this.context = context;
		this.userBeansList = userBeansList;
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
		viewHolder.deleteUser.setVisibility(View.GONE);
		viewHolder.editUser.setVisibility(View.GONE);
		viewHolder.userName.setText(userBean.userName);
		return convertView;
	}

	class ViewHolder {
		ImageView editUser, deleteUser;
		TextView userName;
	}

}
