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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.task.taskApplication.R;
import com.taskism.bean.ScheduleDescriptionBean;
import com.taskism.imageLoader.DownloadImage;

/**
 * @author Manpreet
 * 
 */
public class CustomUserScheduleDescriptionAdapter extends BaseAdapter {

	List<ScheduleDescriptionBean> scheduleDescriptionBeansList;
	Context context;

	/**
	 * 
	 */
	public CustomUserScheduleDescriptionAdapter(
			List<ScheduleDescriptionBean> objects, Context context) {
		this.context = context;
		this.scheduleDescriptionBeansList = objects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return scheduleDescriptionBeansList.size();
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
			convertView = layoutInflater.inflate(
					R.layout.custom_schedule_description, null);
			viewHolder = new ViewHolder();
			viewHolder.taskName = (TextView) convertView
					.findViewById(R.id.scheduleNameText);
			viewHolder.taskDescription = (TextView) convertView
					.findViewById(R.id.scheduleDescriptionText);
			viewHolder.taskImage = (ImageView) convertView
					.findViewById(R.id.scheduleImage);
			viewHolder.taskStepProgress = (ProgressBar) convertView
					.findViewById(R.id.loadingProgress);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();

		}
		ScheduleDescriptionBean scheduleDescriptionBean = scheduleDescriptionBeansList
				.get(position);
		viewHolder.taskName.setText(scheduleDescriptionBean.taskStepName);
		viewHolder.taskDescription
				.setText(scheduleDescriptionBean.taskStepDescription);
		String url = scheduleDescriptionBean.taskStepImage;
		if (url.contains("\\")) {
			url = url.replace("\\", "/");
		}

		new DownloadImage().downloadImage("http://www.taskism.com/" + url,
				viewHolder.taskImage, viewHolder.taskStepProgress);
		return convertView;
	}

	class ViewHolder {
		TextView taskName, taskDescription;
		ImageView taskImage;
		ProgressBar taskStepProgress;

	}
}
