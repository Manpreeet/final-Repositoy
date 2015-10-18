/**
 * 
 */
package com.tarsem.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.tarsem.bean.AccessBean;
import com.tarsem.bean.CommentsBean;
import com.tarsem.bean.EditOtherUserProfileBean;
import com.tarsem.bean.RoleBean;
import com.tarsem.bean.ScheduleDescriptionBean;
import com.tarsem.bean.TaskListBean;
import com.tarsem.bean.UserBean;
import com.tarsem.bean.UserProfileBean;
import com.tarsem.bean.UserShiftBean;
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

	/**
	 * 
	 * developer:Manpreet date:27-Sep-2015 return:void description: method for
	 * parse comments from server
	 */
	public void fetchScheduleDesciption(String response,
			ResponseCallback responseCallback) {
		try {
			List<ScheduleDescriptionBean> commentList = null;
			responseObject = new JSONObject(response);
			responseArray = responseObject.getJSONArray("tasksteps");
			if (responseArray.length() != 0) {
				commentList = new ArrayList<>();
				ScheduleDescriptionBean scheduleDescriptionBean = null;
				for (int i = 0; i < responseArray.length(); i++) {
					scheduleDescriptionBean = new ScheduleDescriptionBean();
					scheduleDescriptionBean.taskStepId = responseArray
							.getJSONObject(i).getString("taskstepid");
					scheduleDescriptionBean.taskStepDescription = responseArray
							.getJSONObject(i).getString("description");
					scheduleDescriptionBean.taskStepImage = responseArray
							.getJSONObject(i).getString("image");
					scheduleDescriptionBean.taskStepName = responseArray
							.getJSONObject(i).getString("taskname");
					scheduleDescriptionBean.taskStepSequence = responseArray
							.getJSONObject(i).getString("seq");

					commentList.add(scheduleDescriptionBean);
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

	/**
	 * 
	 * developer:Manpreet date:02-Oct-2015 return:void description: method for
	 * parse user list response
	 */
	public void fetchUserList(String response, ResponseCallback responseCallback) {
		try {
			List<UserBean> userBeansList = null;
			responseObject = new JSONObject(response);
			responseArray = responseObject.getJSONArray("users");
			if (responseArray.length() != 0) {

				UserBean userBean = null;
				userBeansList = new ArrayList<UserBean>();
				for (int i = 0; i < responseArray.length(); i++) {
					userBean = new UserBean();
					userBean.userId = responseArray.getJSONObject(i).getString(
							"userid");
					userBean.userName = responseArray.getJSONObject(i)
							.getString("name");
					userBean.userEmail = responseArray.getJSONObject(i)
							.getString("email");
					userBean.roles = responseArray.getJSONObject(i).getString(
							"roles");
					userBean.access = responseArray.getJSONObject(i).getString(
							"access");
					userBeansList.add(userBean);
				}
				responseCallback.onSuccessRecieve(userBeansList);
			} else {
				responseCallback.onErrorRecieve(true);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			responseObject = null;
			responseArray = null;
			System.gc();
		}
	}

	/**
	 * 
	 * developer:Manpreet date:02-Oct-2015 return:void description: method for
	 * fetch user profile information
	 */
	public void fetchUserInformation(String response,
			ResponseCallback responseCallback) {
		try {
			UserProfileBean userProfileBean = new UserProfileBean();
			responseObject = new JSONObject(response);
			userProfileBean.firstName = responseObject.getString("firstname");
			userProfileBean.lastName = responseObject.getString("lastname");
			userProfileBean.email = responseObject.getString("email");
			userProfileBean.cellphone = responseObject.getString("cellphone");
			String emailStatus = responseObject.getString("getemails");
			if (emailStatus.equals("1")) {
				userProfileBean.getEmailStatus = true;

			} else {
				userProfileBean.getEmailStatus = false;

			}
			responseCallback.onSuccessRecieve(userProfileBean);
		} catch (Exception e) {
			responseCallback.onErrorRecieve(e);
		} finally {
			responseObject = null;
			System.gc();
		}
	}

	/**
	 * 
	 * developer:Manpreet date:17-Oct-2015 return:void description: method for
	 * fetch other user information
	 */
	public void fetchOtherUserInformation(String response,
			ResponseCallback responseCallback) {
		try {
			responseObject = new JSONObject(response);
			EditOtherUserProfileBean editOtherUserProfileBean = null;
			if (responseObject != null) {
				editOtherUserProfileBean = new EditOtherUserProfileBean();
				editOtherUserProfileBean.userId = responseObject
						.getString("userid");
				editOtherUserProfileBean.firstName = responseObject
						.getString("firstname");
				editOtherUserProfileBean.lastName = responseObject
						.getString("lastname");
				editOtherUserProfileBean.cellPhone = responseObject
						.getString("cellphone");
				editOtherUserProfileBean.emailId = responseObject
						.getString("email");
				if (responseObject.getString("getemails").equals("0")) {
					editOtherUserProfileBean.getEmailStatus = false;

				} else {
					editOtherUserProfileBean.getEmailStatus = true;

				}
				responseArray = responseObject.getJSONArray("roles");

				if (responseArray.length() != 0) {
					RoleBean roleBean = null;
					editOtherUserProfileBean.roleBeanList = new ArrayList<RoleBean>();
					for (int i = 0; i < responseArray.length(); i++) {
						roleBean = new RoleBean();
						roleBean.roleId = responseArray.getJSONObject(i)
								.getString("roleid");
						roleBean.roleName = responseArray.getJSONObject(i)
								.getString("name");
						if (responseArray.getJSONObject(i).getInt("member") == 0) {
							roleBean.roleStatus = false;
						} else {
							roleBean.roleStatus = true;
						}
						editOtherUserProfileBean.roleBeanList.add(roleBean);
					}
				}
				JSONArray roleAccessArray = responseObject
						.getJSONArray("access");
				if (roleAccessArray.length() != 0) {
					AccessBean accessBean = null;
					editOtherUserProfileBean.accessBeanList = new ArrayList<AccessBean>();

					for (int i = 0; i < roleAccessArray.length(); i++) {
						accessBean = new AccessBean();
						accessBean.accessId = roleAccessArray.getJSONObject(i)
								.getString("accessid");
						accessBean.accessName = roleAccessArray
								.getJSONObject(i).getString("name");
						if (roleAccessArray.getJSONObject(i).getInt("member") == 0) {
							accessBean.accessStatus = false;
						} else {
							accessBean.accessStatus = true;
						}
						editOtherUserProfileBean.accessBeanList.add(accessBean);
					}
				}
				responseCallback.onSuccessRecieve(editOtherUserProfileBean);
			} else {
				responseCallback.onErrorRecieve("no record found");
			}
		} catch (Exception e) {
			responseCallback
					.onErrorRecieve("Exception arrise while fetch information"
							+ e.toString());
		}

	}

	/**
	 * 
	 * developer:Manpreet date:18-Oct-2015 return:void description: method for
	 * fetch role list
	 */
	public void fetchRoleList(String response, ResponseCallback responseCallback) {
		try {
			responseObject = new JSONObject(response);
			responseArray = responseObject.getJSONArray("roles");

			if (responseArray.length() != 0) {
				RoleBean roleBean = null;
				List<RoleBean> roleBeansList = new ArrayList<RoleBean>();
				for (int i = 0; i < responseArray.length(); i++) {
					roleBean = new RoleBean();
					roleBean.roleId = responseArray.getJSONObject(i).getString(
							"roleid");
					roleBean.roleName = responseArray.getJSONObject(i)
							.getString("name");
					roleBean.roleColor = responseArray.getJSONObject(i)
							.getString("color");
					roleBean.roleUsers = responseArray.getJSONObject(i)
							.getString("usernames");
					roleBean.roleDescription = responseArray.getJSONObject(i)
							.getString("description");
					roleBeansList.add(roleBean);
				}
				responseCallback.onSuccessRecieve(roleBeansList);
			} else {
				responseCallback.onErrorRecieve("no record found");
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 
	 * developer:Manpreet date:04-Oct-2015 return:void description: method for
	 * fetch schedule list based on employee shift
	 */
	public void fetchShiftByEmployee(String response,
			ResponseCallback responseCallback) {
		try {
			responseObject = new JSONObject(response);
			responseArray = responseObject.getJSONArray("shifts");
			if (responseArray != null) {
				if (responseArray.length() != 0) {
					UserShiftBean userShiftBean;
					List<UserShiftBean> userShiftBeansList = new ArrayList<UserShiftBean>();
					for (int i = 0; i < responseArray.length(); i++) {
						userShiftBean = new UserShiftBean();
						if (i == 0) {
							userShiftBean.fromTo = responseObject
									.getString("fromto");
						}
						userShiftBean.sundayShift = responseArray
								.getJSONObject(i).getString("sun");
						userShiftBean.mondayShift = responseArray
								.getJSONObject(i).getString("mon");
						userShiftBean.tuesdayShift = responseArray
								.getJSONObject(i).getString("tue");
						userShiftBean.thrusdayShift = responseArray
								.getJSONObject(i).getString("thu");
						userShiftBean.firdayShift = responseArray
								.getJSONObject(i).getString("fri");
						userShiftBean.saturdayShift = responseArray
								.getJSONObject(i).getString("sat");
						userShiftBean.employeeName = responseArray
								.getJSONObject(i).getString("name");
						userShiftBean.toThrs = responseArray.getJSONObject(i)
								.getString("tothrs");
						userShiftBeansList.add(userShiftBean);
					}

					responseCallback.onSuccessRecieve(userShiftBeansList);
				} else {
					responseCallback.onErrorRecieve("no record found");

				}
			} else {
				responseCallback.onErrorRecieve("no record found");

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
