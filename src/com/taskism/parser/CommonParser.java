/**
 * 
 */
package com.taskism.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.taskism.bean.AccessBean;
import com.taskism.bean.CommentsBean;
import com.taskism.bean.EditOtherUserProfileBean;
import com.taskism.bean.EditRoleBean;
import com.taskism.bean.EditScheduleBean;
import com.taskism.bean.EditTaskBean;
import com.taskism.bean.EditTaskScheduleBean;
import com.taskism.bean.RoleBean;
import com.taskism.bean.ScheduleDescriptionBean;
import com.taskism.bean.TaskListBean;
import com.taskism.bean.UserBean;
import com.taskism.bean.UserProfileBean;
import com.taskism.bean.UserShiftBean;
import com.taskism.constant.Constant;
import com.taskism.responsecallback.ResponseCallback;

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
	 * developer:Manpreet date:15-Nov-2015 return:void description: method for
	 * fetch schedule list from server
	 */
	public void fetchScheduleList(String response,
			ResponseCallback responseCallback) {
		try {
			responseObject = new JSONObject(response);
			responseArray = responseObject.getJSONArray("shifts");
			if (responseArray != null && responseArray.length() != 0) {
				EditScheduleBean editScheduleBean = null;
				List<EditScheduleBean> editScheduleList = new ArrayList<EditScheduleBean>();
				for (int i = 0; i < responseArray.length(); i++) {
					editScheduleBean = new EditScheduleBean();
					editScheduleBean.scheduleDetail = responseArray
							.getJSONObject(i).getString("details");
					editScheduleBean.scheduleToThrs = responseArray
							.getJSONObject(i).getString("tothrs");
					editScheduleBean.scheduleName = responseArray
							.getJSONObject(i).getString("name");
					editScheduleBean.shiftId = responseArray.getJSONObject(i)
							.getString("shiftid");
					editScheduleList.add(editScheduleBean);
				}
				responseCallback.onSuccessRecieve(editScheduleList);
			} else {
				responseCallback.onErrorRecieve("no record found");
			}

		} catch (Exception e) {
			e.printStackTrace();
			responseCallback.onErrorRecieve("no record found");

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
		} finally {

			responseObject = null;
			responseArray = null;
			System.gc();
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
			e.printStackTrace();
		} finally {

			responseObject = null;
			responseArray = null;
			System.gc();
		}
	}

	/**
	 * 
	 * developer:manpreets2 date:Nov 9, 2015 return:void description: method for
	 * fetch role information
	 */
	public void fetchRoleInformation(String response,
			ResponseCallback responseCallback) {

		try {
			EditRoleBean editRoleBean = null;
			responseObject = new JSONObject(response);
			if (responseObject != null) {
				editRoleBean = new EditRoleBean();
				editRoleBean.roleId = responseObject.getString("roleid");
				editRoleBean.roleName = responseObject.getString("name");
				editRoleBean.colorCode = "#"
						+ responseObject.getString("color");
				if (responseObject.getString("description").equals("null")) {
					editRoleBean.roleDescription = "";
				} else {
					editRoleBean.roleDescription = responseObject
							.getString("description");
				}

			}

			responseArray = responseObject.getJSONArray("users");
			if (responseArray.length() != 0) {
				editRoleBean.userBeanList = new ArrayList<UserBean>();
				for (int i = 0; i < responseArray.length(); i++) {
					UserBean userBean = new UserBean();
					userBean.userId = responseArray.getJSONObject(i).getString(
							"userid");
					userBean.userName = responseArray.getJSONObject(i)
							.getString("name");
					if (responseArray.getJSONObject(i).getInt("member") == 0) {
						userBean.checkedStatus = false;
					} else {
						userBean.checkedStatus = true;
					}
					editRoleBean.userBeanList.add(userBean);

				}
				responseCallback.onSuccessRecieve(editRoleBean);
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

		} finally {

			responseObject = null;
			responseArray = null;
			System.gc();
		}
	}

	/**
	 * 
	 * developer:manpreets2 date:Nov 5, 2015 return:void description: method for
	 * parse schedule listing
	 */
	public void parseScheduleInformation(String response,
			ResponseCallback responseCallback) {
		try {
			responseObject = new JSONObject(response);
			responseArray = responseObject.getJSONArray("shifts");
			if (responseArray.length() != 0) {
				EditScheduleBean editScheduleBean = null;
				List<EditScheduleBean> editScheduleBeanList = new ArrayList<EditScheduleBean>();
				for (int i = 0; i < responseArray.length(); i++) {
					editScheduleBean = new EditScheduleBean();
					editScheduleBean.scheduleDetail = responseArray
							.getJSONObject(i).getString("details");
					editScheduleBean.scheduleName = responseArray
							.getJSONObject(i).getString("name");
					editScheduleBean.scheduleToThrs = responseArray
							.getJSONObject(i).getString("tothrs");
					editScheduleBean.shiftId = responseArray.getJSONObject(i)
							.getString("shiftid");
					editScheduleBeanList.add(editScheduleBean);
				}
				responseCallback.onSuccessRecieve(editScheduleBeanList);

			}
			responseCallback.onErrorRecieve("No record found");

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
	 * developer:Manpreet date:19-Nov-2015 return:void description: method for
	 * fetch task information
	 */
	public void fetchTaskInformation(String response,
			ResponseCallback responseCallback) {
		try {
			EditTaskBean scheduleBean = new EditTaskBean();

			responseObject = new JSONObject(response);
			//responseArray = responseObject.getJSONArray("once");

			scheduleBean.taskInstruction = responseObject.getString("instruct");
			scheduleBean.taskName = responseObject.getString("name");
			scheduleBean.roleId = responseObject.getString("roles");
			scheduleBean.monthlySchedule = responseObject.getString("monthly");

			/*if (responseArray != null) {
				if (responseArray.length() != 0) {
					scheduleBean.editTaskScheduleBeanList = new ArrayList<EditTaskScheduleBean>();
					EditTaskScheduleBean editTaskScheduleBean = null;
					for (int i = 0; i < responseArray.length(); i++) {
						editTaskScheduleBean = new EditTaskScheduleBean();
						editTaskScheduleBean.dateText = responseArray
								.getJSONObject(i).getString("datetext");
						editTaskScheduleBean.ScheduleId = responseArray
								.getJSONObject(i).getString("scheduleid");
						scheduleBean.editTaskScheduleBeanList
								.add(editTaskScheduleBean);
					}
				}
			}*/
			responseCallback.onSuccessRecieve(scheduleBean);
		} catch (Exception e) {
			e.printStackTrace();
			responseCallback.onErrorRecieve("server down");
		} finally {
			responseObject = null;
			responseArray = null;
			System.gc();

		}

	}
}
