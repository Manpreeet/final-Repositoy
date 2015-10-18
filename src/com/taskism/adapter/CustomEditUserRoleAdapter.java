/**
 * 
 */
package com.taskism.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tarsem.bean.UserRoleBean;
import com.task.taskApplication.R;

/**
 * @author asifa
 * 
 */
public class CustomEditUserRoleAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<UserRoleBean> userRoleList;
	private Activity activity;

	/**
	 * 
	 */
	public CustomEditUserRoleAdapter(Context context,
			ArrayList<UserRoleBean> userRoleList, Activity activity) {
		this.userRoleList = userRoleList;
		this.context = context;
		this.activity = activity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return userRoleList.size();
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
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = layoutInflater.inflate(
					R.layout.custom_edit_user_role, null);
			viewHolder = new ViewHolder();
			viewHolder.colorBar = (View) convertView
					.findViewById(R.id.colorBar);
			viewHolder.userRoleName = (TextView) convertView
					.findViewById(R.id.userRoleName);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		UserRoleBean userRole = userRoleList.get(position);
		viewHolder.userRoleName.setText(userRole.getRoleName());

		return convertView;
	}

	public void updatenewUserRoleList(ArrayList<UserRoleBean> newUserRoleList) {
		userRoleList.addAll(newUserRoleList);
		notifyDataSetChanged();
	}

	class ViewHolder {
		View colorBar;
		TextView userRoleName;
	}
}