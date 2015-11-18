/**
 * 
 */
package com.taskism.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.task.taskApplication.R;
import com.taskism.bean.TaskListBean;
import com.taskism.constant.Constant;
import com.taskism.control.ActivityController;
import com.taskism.taskApplication.CommentActivity;
import com.taskism.taskApplication.UserScheduleDescriptionActivity;
import com.taskism.taskApplication.UsersScheduleTaskActivity;

/**
 * @author Manpreet
 * 
 */
public class CustomTaskAdapter extends BaseAdapter {
	Context context;
	List<TaskListBean> taskList;
	UsersScheduleTaskActivity homeActivity;

	/**
	 * 
	 */
	public CustomTaskAdapter(Context context, List<TaskListBean> taskList,
			UsersScheduleTaskActivity homeActivity) {
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
			convertView = layoutInflater.inflate(R.layout.custom_user_task,
					null);
			viewHolder = new ViewHolder();
			viewHolder.checkStatus = (CheckBox) convertView
					.findViewById(R.id.checkStatus);
			viewHolder.taskName = (TextView) convertView
					.findViewById(R.id.taskName);
			viewHolder.commentCount = (TextView) convertView
					.findViewById(R.id.commentCount);
			viewHolder.commentImage = (ImageView) convertView
					.findViewById(R.id.commentImage);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.commentImage.setTag(position);
		viewHolder.commentImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int pos = (Integer) v.getTag();
				TaskListBean taskListBean = taskList.get(pos);
				String scheduleId = taskListBean.scheduleId;
				String taskId = taskListBean.taskId;
				String taskName = taskListBean.taskName;
				Intent intent = new Intent(context, CommentActivity.class);
				intent.putExtra(Constant.taskId, Integer.parseInt(taskId));
				intent.putExtra(Constant.scheduleId,
						Integer.parseInt(scheduleId));
				intent.putExtra(Constant.taskName, taskName);
				homeActivity.startActivity(intent);
				homeActivity.overridePendingTransition(R.anim.slide_in,
						R.anim.slide_out);

				/*
				 * Bundle bundle = new Bundle(); bundle.putInt(Constant.taskId,
				 * Integer.parseInt(taskId)); bundle.putInt(Constant.scheduleId,
				 * Integer.parseInt(scheduleId));
				 * bundle.putString(Constant.taskName, taskName);
				 * ActivityController.startActivityController(
				 * Constant.commentsActivity, bundle, homeActivity, false);
				 */
			}
		});
		final TaskListBean taskListBean = taskList.get(position);
		final String taskName = taskListBean.taskName;
		final int taskId = Integer.parseInt(taskListBean.taskId);

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,
						UserScheduleDescriptionActivity.class);
				intent.putExtra("taskName", taskName);
				intent.putExtra("taskId", taskId);
				context.startActivity(intent);

			}
		});

		viewHolder.taskName.setText(taskListBean.taskName);
		if (!(taskListBean.commentCount.equals("null"))) {
			viewHolder.commentCount.setText(taskListBean.commentCount);

		} else {
			viewHolder.commentCount.setText("0");

		}
		viewHolder.checkStatus.setOnCheckedChangeListener(null);
		if (taskListBean.checkedStatus) {
			viewHolder.checkStatus.setChecked(true);
		} else {
			viewHolder.checkStatus.setChecked(false);
		}
		viewHolder.checkStatus.setTag(position);
		viewHolder.checkStatus
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						int pos = (Integer) buttonView.getTag();
						TaskListBean taskListBean = taskList.get(pos);
						if (isChecked) {
							taskListBean.checkedStatus = true;

						} else {
							taskListBean.checkedStatus = false;

						}

						homeActivity.markTaskItem(taskListBean);
					}
				});

		return convertView;
	}

	class ViewHolder {
		CheckBox checkStatus;
		TextView taskName;
		TextView commentCount;
		ImageView commentImage;
	}
}
