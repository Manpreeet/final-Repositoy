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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.tarsem.bean.TaskListBean;
import com.tarsem.constant.Constant;
import com.task.taskApplication.CommentActivity;
import com.task.taskApplication.MyScheduleTaskActivity;
import com.task.taskApplication.R;

/**
 * @author Manpreet
 * 
 */
public class CustomMyScheduleAdapter extends BaseAdapter {
	Context context;
	List<TaskListBean> taskList;
	MyScheduleTaskActivity myScheduleTaskActivity;

	/**
	 * 
	 */
	public CustomMyScheduleAdapter(Context context,
			List<TaskListBean> taskList, MyScheduleTaskActivity homeActivity) {
		this.taskList = taskList;
		this.context = context;
		this.myScheduleTaskActivity = homeActivity;
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
				myScheduleTaskActivity.startActivity(intent);
				myScheduleTaskActivity.overridePendingTransition(
						R.anim.slide_in, R.anim.slide_out);

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

						myScheduleTaskActivity.markTaskItem(taskListBean);
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
