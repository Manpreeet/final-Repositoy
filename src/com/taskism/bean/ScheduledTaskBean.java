/**
 * 
 */
package com.taskism.bean;


/**
 * @author asifa
 *
 */
public class ScheduledTaskBean {
	private String scheduledTaskName;
	private String userName;
	private String duration;
	private String color;
	/**
	 * @return the scheduledTaskName
	 */
	public String getScheduledTaskName() {
		return scheduledTaskName;
	}
	/**
	 * @param scheduledTaskName the scheduledTaskName to set
	 */
	public void setScheduledTaskName(String scheduledTaskName) {
		this.scheduledTaskName = scheduledTaskName;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
}