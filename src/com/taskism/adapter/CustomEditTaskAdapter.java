/**
 * 
 */
package com.taskism.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.task.taskApplication.R;
import com.taskism.bean.TaskListBean;
import com.taskism.constant.Constant;
import com.taskism.control.ActivityController;
import com.taskism.taskApplication.EditTaskActivity;
import com.taskism.taskApplication.EditTaskInfoActivity;
import com.taskism.taskApplication.UpdateRoleActivity;

/**
 * @author Manpreet
 * 
 */
public class CustomEditTaskAdapter extends BaseAdapter {
	Context context;
	List<TaskListBean> taskList;
	EditTaskActivity homeActivity;

	/**
	 * 
	 */
	public CustomEditTaskAdapter(Context context, List<TaskListBean> taskList,
			EditTaskActivity homeActivity) {
		this.taskList = taskList;
		this.context = context;
		this.homeActivity = homeActivity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return taskList.size();
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
		final TaskListBean taskListBean = taskList.get(position);
		final String taskName = taskListBean.taskName;
		final int taskId = Integer.parseInt(taskListBean.taskId);
		viewHolder.userName.setText(taskName);
		viewHolder.editUser.setTag(position);

		viewHolder.deleteUser.setTag(position);
		viewHolder.deleteUser.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int pos = (Integer) v.getTag();

				TaskListBean userBean = taskList.get(pos);
				// editUserActivity.showDeleteConfirmationPopup(userBean.userId,
				// userBean.userName, pos);

			}
		});
		viewHolder.editUser.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int pos = (Integer) v.getTag();

				TaskListBean userBean = taskList.get(pos);
				int taskId = Integer.parseInt(userBean.taskId);
				Intent intent = new Intent(context, EditTaskInfoActivity.class);
				intent.putExtra(Constant.userid, taskId);
				context.startActivity(intent);
			}
		});

		return convertView;
	}

	class ViewHolder {
		ImageView editUser, deleteUser;
		TextView userName;
	}
}
