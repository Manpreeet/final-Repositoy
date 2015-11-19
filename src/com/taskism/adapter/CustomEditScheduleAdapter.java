/**
 * 
 */
package com.taskism.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.task.taskApplication.R;
import com.taskism.bean.EditScheduleBean;
import com.taskism.bean.RoleBean;
import com.taskism.bean.ScheduledTaskBean;
import com.taskism.constant.Constant;
import com.taskism.taskApplication.EditRoleActivity;
import com.taskism.taskApplication.EditScheduleActivity;
import com.taskism.taskApplication.UpdateRoleActivity;
import com.taskism.taskApplication.UpdateScheuduleActivity;

/**
 * @author asifa
 * 
 */
public class CustomEditScheduleAdapter extends BaseAdapter {
	private Context context;
	private List<EditScheduleBean> scheduledTaskList;
	private Activity activity;

	/**
	 * 
	 */
	public CustomEditScheduleAdapter(Context context,
			List<EditScheduleBean> scheduledTaskList, Activity activity) {
		this.scheduledTaskList = scheduledTaskList;
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
		return scheduledTaskList.size();
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
			convertView = layoutInflater.inflate(R.layout.custom_edit_schedule,
					null);
			viewHolder = new ViewHolder();
			viewHolder.colorBar = (View) convertView
					.findViewById(R.id.colorBar);
			viewHolder.sceduledTaskName = (TextView) convertView
					.findViewById(R.id.sceduledTaskName);
			viewHolder.userName = (TextView) convertView
					.findViewById(R.id.userName);
			viewHolder.sceduledTaskTime = (TextView) convertView
					.findViewById(R.id.sceduledTaskTime);
			viewHolder.editScheduleImage = (ImageView) convertView
					.findViewById(R.id.editScheduleImage);
			viewHolder.deleteScheduleImage = (ImageView) convertView
					.findViewById(R.id.deleteScheduleImage);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		EditScheduleBean scheduledTaskDetail = scheduledTaskList.get(position);
		String taskDuration = scheduledTaskDetail.scheduleDetail.split("\n")[0];
		String taskName = scheduledTaskDetail.scheduleDetail.split("\n")[1];
		taskName = taskName.split("\\(")[1].split("\\)")[0];
		viewHolder.sceduledTaskName.setText(taskName);
		viewHolder.userName.setText(scheduledTaskDetail.scheduleName);
		viewHolder.sceduledTaskTime.setText(taskDuration);

		viewHolder.editScheduleImage.setTag(position);
		viewHolder.deleteScheduleImage.setTag(position);

		viewHolder.editScheduleImage
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						int pos = (Integer) v.getTag();

						int userId = Integer.parseInt(scheduledTaskList
								.get(pos).shiftId);
						String startTime, endTime;

						startTime = scheduledTaskList.get(pos).scheduleDetail
								.split("\n")[0];
						startTime = startTime.split("-")[0];
						endTime = scheduledTaskList.get(pos).scheduleDetail
								.split("\n")[0].split("-")[1];
						String taskName = scheduledTaskList.get(pos).scheduleDetail
								.split("\n")[1];
						taskName = taskName.split("\\(")[1].split("\\)")[0];
						Intent intent = new Intent(context,
								UpdateScheuduleActivity.class);
						intent.putExtra(Constant.userid, userId);
						intent.putExtra("username",
								scheduledTaskList.get(pos).scheduleName);
						intent.putExtra("rolename", taskName);
						intent.putExtra("starttime", startTime);
						intent.putExtra("endtime", endTime);

						context.startActivity(intent);

					}
				});
		viewHolder.deleteScheduleImage
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						int pos = (Integer) v.getTag();
						EditScheduleBean editScheduleBean = scheduledTaskList
								.get(pos);
						String taskName = editScheduleBean.scheduleDetail
								.split("\n")[1];
						taskName = taskName.split("\\(")[1].split("\\)")[0];

						((EditScheduleActivity) activity)
								.showDeleteConfirmationPopup(
										editScheduleBean.shiftId, taskName, pos);

					}
				});

		return convertView;
	}

	public void updateScheduledTasKList(
			List<EditScheduleBean> newScheduledTaskList) {
		scheduledTaskList.addAll(newScheduledTaskList);
		notifyDataSetChanged();
	}

	class ViewHolder {
		View colorBar;
		TextView sceduledTaskName;
		TextView userName;
		TextView sceduledTaskTime;
		ImageView editScheduleImage, deleteScheduleImage;
	}
}