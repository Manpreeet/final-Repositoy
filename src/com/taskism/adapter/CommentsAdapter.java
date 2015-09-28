/**
 * 
 */
package com.taskism.adapter;

import java.util.List;

import com.tarsem.bean.CommentsBean;
import com.task.taskApplication.R;
import com.taskism.adapter.CustomTaskAdapter.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * @author Manpreet
 * 
 */
public class CommentsAdapter extends BaseAdapter {

	private Context context;
	private List<CommentsBean> commentsBeansList;

	/**
	 * 
	 */
	public CommentsAdapter(Context context, List<CommentsBean> commentsBeans) {
		this.context = context;
		this.commentsBeansList = commentsBeans;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return commentsBeansList.size();
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
			convertView = layoutInflater.inflate(R.layout.custom_comment_list,
					null);
			viewHolder = new ViewHolder();
			viewHolder.commentMessage = (TextView) convertView
					.findViewById(R.id.commentMessage);
			viewHolder.commentTime = (TextView) convertView
					.findViewById(R.id.commentTime);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		CommentsBean commentsBean = commentsBeansList.get(position);

		viewHolder.commentMessage.setText(commentsBean.commentMessage);
		viewHolder.commentTime.setText("(" + commentsBean.userName + " "
				+ commentsBean.commentDate + ")");
		return convertView;
	}

	class ViewHolder {
		TextView commentMessage;
		TextView commentTime;
	}

}
