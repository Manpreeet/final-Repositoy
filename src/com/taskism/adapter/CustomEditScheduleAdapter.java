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

import com.tarsem.bean.ScheduledTaskBean;
import com.task.taskApplication.R;

/**
 * @author asifa
 * 
 */
public class CustomEditScheduleAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<ScheduledTaskBean> scheduledTaskList;
	private Activity activity;

	/**
	 * 
	 */
	public CustomEditScheduleAdapter(Context context,
			ArrayList<ScheduledTaskBean> scheduledTaskList, Activity activity) {
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

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		ScheduledTaskBean scheduledTaskDetail = scheduledTaskList.get(position);
		viewHolder.sceduledTaskName.setText(scheduledTaskDetail
				.getScheduledTaskName());
		viewHolder.userName.setText(scheduledTaskDetail.getUserName());
		viewHolder.sceduledTaskTime.setText(scheduledTaskDetail.getDuration());

		return convertView;
	}

	public void updateScheduledTasKList(
			ArrayList<ScheduledTaskBean> newScheduledTaskList) {
		scheduledTaskList.addAll(newScheduledTaskList);
		notifyDataSetChanged();
	}

	class ViewHolder {
		View colorBar;
		TextView sceduledTaskName;
		TextView userName;
		TextView sceduledTaskTime;
	}
}