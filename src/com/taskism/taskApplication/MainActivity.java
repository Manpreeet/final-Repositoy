package com.taskism.taskApplication;

import com.task.taskApplication.R;

import android.content.Context;
import android.os.Bundle;

public class MainActivity extends ParentActivity {

	ParentActivity parentActivity;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findAttributesId();
	}
	
	/**
	 * method for find attributes id's
	 */
	private void findAttributesId() {
		parentActivity = this;
		context = this;
	}

}
