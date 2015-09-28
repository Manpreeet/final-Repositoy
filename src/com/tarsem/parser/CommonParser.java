/**
 * 
 */
package com.tarsem.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.tarsem.bean.CommentsBean;
import com.tarsem.bean.TaskListBean;
import com.tarsem.constant.Constant;
import com.tarsem.responsecallback.ResponseCallback;

/**
 * @author Manpreet
 * 
 */
public class CommonParser {
	JSONObject responseObject = null;
	JSONArray responseArray = null;

	/**
	 * 
	 */
	public CommonParser() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * developer:Manpreet date:27-Sep-2015 return:void description: mthod for
	 * parse login response
	 */
	public void loginResponseParser(String response,
			ResponseCallback responseCallback, Context context) {
		int userId = 0;
		String userName = null;
		try {
			responseObject = new JSONObject(response);
			userId = Integer.parseInt(responseObject.getString("userid"));
			userName = responseObject.getString("username");
			Constant.saveLoggedUserInfo(context, userName, userId,
					responseCallback);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			responseObject = null;
			System.gc();
		}
	}

	/**
	 * 
	 * developer:Manpreet date:27-Sep-2015 return:void description: method for
	 * parse all task response
	 */
	public void allTaskResponseParser(String response,
			ResponseCallback responseCallback) {
		try {
			List<TaskListBean> taskList = null;
			responseObject = new JSONObject(response);
			responseArray = responseObject.getJSONArray("tasks");
			if (responseArray.length() != 0) {
				taskList = new ArrayList<>();
				TaskListBean taskListBean = null;
				for (int i = 0; i < responseArray.length(); i++) {
					taskListBean = new TaskListBean();
					String checkedStatus = responseArray.getJSONObject(i)
							.getString("checked");
					if (checkedStatus.equals("1")) {
						taskListBean.checkedStatus = true;
					} else {
						taskListBean.checkedStatus = false;
					}
					taskListBean.commentCount = responseArray.getJSONObject(i)
							.getString("commentcount");
					taskListBean.taskId = responseArray.getJSONObject(i)
							.getString("taskid");
					taskListBean.taskName = responseArray.getJSONObject(i)
							.getString("taskname");
					taskListBean.scheduleId = responseArray.getJSONObject(i)
							.getString("scheduleid");
					taskList.add(taskListBean);

				}
				responseCallback.onSuccessRecieve(taskList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			responseArray = null;
			responseObject = null;
			System.gc();
		}
	}

	/**
	 * 
	 * developer:Manpreet date:27-Sep-2015 return:void description: method for
	 * parse comments from server
	 */
	public void fetchUserComments(String response,
			ResponseCallback responseCallback) {
		try {
			List<CommentsBean> commentList = null;
			responseObject = new JSONObject(response);
			responseArray = responseObject.getJSONArray("comments");
			if (responseArray.length() != 0) {
				commentList = new ArrayList<>();
				CommentsBean commentBean = null;
				for (int i = 0; i < responseArray.length(); i++) {
					commentBean = new CommentsBean();
					commentBean.userName = responseArray.getJSONObject(i)
							.getString("name");
					commentBean.commentMessage = responseArray.getJSONObject(i)
							.getString("comment");
					commentBean.commentDate = responseArray.getJSONObject(i)
							.getString("date");
					commentList.add(commentBean);
				}
				responseCallback.onSuccessRecieve(commentList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			responseArray = null;
			responseObject = null;
			System.gc();
		}

	}

}
