package com.taskism.constant;

import android.content.Context;
import android.content.SharedPreferences;

import com.taskism.responsecallback.ResponseCallback;

public class Constant {
	/**
	 * activity control declaration
	 */
	public static int signUpActivityController = 1;
	public static int forgetPasswrodActivityController = 2;
	public static int homeActivity = 3;
	public static int commentsActivity = 4;
	public static int EditUserActivity = 5;
	public static int AddUserActivity = 7;
	public static int AddNewTaskActivity = 8;
	public static int AddNewImageActivity = 9;
	public static int AddNewRoleActivity = 10;
	public static int AddNewScheduleActivity = 11;
	public static final String emptyFieldValidationMsg = "Enter value first";
	public static final String emailFieldValidation = "Enter email first";
	public static final String passwordFieldValidation = "Enter password first";
	public static final String emailFormatValidation = "Email format is invalid";
	public static final String addNewTaskDialogTitle = "Add Image";
	public static final String addNewTaskDialogDescription = "Successfully add Task do you want to add image with task?";
	
	public static final String addNewTaskDialogErrorMessage = "Add Task Information first";
	
	
	
	public static final String successKey = "success";
	public static int responseTypeSuccess = 0;
	public static int responseTypeFailure = 0;
	public static final String firstName = "firstname";
	public static final String email = "email";
	public static final String lastName = "lastname";
	public static final String password = "password";
	public static final String taskId = "taskId";
	public static final String scheduleId = "scheduleId";
	public static final String taskName = "taskName";
	public static final String userid = "userid";
	public static final String updateUserId = "updateid";
	/**
	 * SharedPrefrence object declaration
	 */
	public static final String userInfo = "userInfo";
	static SharedPreferences sharedPreferences;

	
	/**
	 *  internet connection messages
	 *  
	 */
	public static final String internetConnectionTitle = "Internet Connection";
	public static final String internetConnectionMessage = "no internet access";
	public static final String internetConnectionPopupButtonText = "Ok";
	
	/**
	 * 
	 * developer:Manpreet date:27-Sep-2015 return:void description: method for
	 * saved logged in user information
	 */
	public static void saveLoggedUserInfo(Context context, String userName,
			int userId, ResponseCallback responseCallback) {
		sharedPreferences = context.getSharedPreferences(Constant.userInfo, 1);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("userName", userName);
		editor.putInt("userId", userId);
		editor.commit();
		responseCallback.onSuccessRecieve(true);

	}

	/**
	 * 
	 * developer:Manpreet date:27-Sep-2015 return:int description: method for
	 * getLoggedUserId
	 */
	public static int getLoggedUserId(Context context) {
		sharedPreferences = context.getSharedPreferences(Constant.userInfo, 1);

		return sharedPreferences.getInt("userId", 0);
	}

	/**
	 * 
	 * developer:Manpreet date:04-Oct-2015 return:String description: method for
	 * get logged user name
	 */
	public static String getLoggedUserName(Context context) {
		sharedPreferences = context.getSharedPreferences(Constant.userInfo, 1);

		return sharedPreferences.getString("userName", null);

	}

}
