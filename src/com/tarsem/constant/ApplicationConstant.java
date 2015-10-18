/**
 * 
 */
package com.tarsem.constant;

/**
 * @author Manpreet
 * 
 */
public class ApplicationConstant {

	public static String baseUrl = "http://taskism.com/webservice001/index.php?";
	public static String actiionUrl = "action=";
	public static String loginrequestType = "login";
	public static String registrationRequestType = "usernew";
	public static String getTaskListRequest = "allusertasks";
	public static String getMyScheduleListRequest = "myscheduledtasks";
	public static String getUserListRequest = "userlist";
	public static String deleteUserRequest = "userdelete";
	public static String allUserScheduleTask = "scheduledusertasks";
	public static String getRoleListRequest = "rolelist";

	public static String editProfileRequestType = "getprofileinfo";
	public static String editOtherUserProfile = "userinfo";
	public static final String forgetRequestType = "forgotpassword";

	// forgotpassword&email=gairy@oaklandera.commmj
	public static String appurl = baseUrl + actiionUrl;
}
