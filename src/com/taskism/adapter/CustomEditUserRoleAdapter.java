/**
 * 
 */
package com.taskism.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tarsem.bean.RoleBean;
import com.tarsem.constant.Constant;
import com.task.taskApplication.EditRoleActivity;
import com.task.taskApplication.R;
import com.task.taskApplication.UpdateRoleActivity;

/**
 * @author asifa
 * 
 */
public class CustomEditUserRoleAdapter extends BaseAdapter {
	private Context context;
	private List<RoleBean> userRoleList;
	private Activity activity;

	/**
	 * 
	 */
	public CustomEditUserRoleAdapter(Context context,
			List<RoleBean> userRoleList, Activity activity) {
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
			viewHolder.editUserRoleImage = (ImageView) convertView
					.findViewById(R.id.editUserRoleImage);
			viewHolder.deleteUserRoleImage = (ImageView) convertView
					.findViewById(R.id.deleteUserRoleImage);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		RoleBean userRole = userRoleList.get(position);
		viewHolder.userRoleName.setText(userRole.roleName);
		viewHolder.colorBar.setBackgroundColor(Color.parseColor("#"
				+ userRole.roleColor));
		viewHolder.editUserRoleImage.setTag(position);
		viewHolder.deleteUserRoleImage.setTag(position);
		viewHolder.editUserRoleImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int pos = (Integer) v.getTag();
				int userId = Integer.parseInt(userRoleList.get(pos).roleName);
				Intent intent = new Intent(context, UpdateRoleActivity.class);
				intent.putExtra(Constant.userid, userId);
				context.startActivity(intent);

			}
		});

		viewHolder.deleteUserRoleImage
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						int pos = (Integer) v.getTag();
						RoleBean userBean = userRoleList.get(pos);
						((EditRoleActivity) activity)
								.showDeleteConfirmationPopup(userBean.roleName,
										userBean.roleName, pos);

					}
				});

		return convertView;
	}

	public void updatenewUserRoleList(List<RoleBean> newUserRoleList) {
		userRoleList.addAll(newUserRoleList);
		notifyDataSetChanged();
	}

	class ViewHolder {
		View colorBar;
		TextView userRoleName;
		ImageView editUserRoleImage, deleteUserRoleImage;
	}
}