package com.taskism.taskApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.task.taskApplication.R;
import com.taskism.constant.ApplicationConstant;
import com.taskism.constant.Constant;
import com.taskism.request.ServerAsyncTask;
import com.taskism.responsecallback.ResponseCallback;
import com.taskism.utility.Utility;

/**
 * Created by Rajesh on 11/2/2015.
 */
public class AddImageActivity extends ParentActivity {

	private String mBase64;
	private ImageView mImgCamera;
	private EditText mEdtCaption;
	Context context;
	int taskId;
	byte[] byteArray;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_add_image);
		findAttributesId();
		getIntentData(arg0);
	}

	/**
	 * developer:manpreets2 date:Nov 3, 2015 return:void description: method for
	 * get intent data
	 */
	private void getIntentData(Bundle arg0) {
		arg0 = getIntent().getExtras();
		if (arg0 != null) {
			taskId = arg0.getInt("taskid");
		}
	}

	/**
	 * developer:jitendray2 date:Nov 2, 2015 return:void description: method for
	 * find attributes id
	 */
	private void findAttributesId() {
		context = this;

		mImgCamera = (ImageView) findViewById(R.id.imageViewCamera);
		mEdtCaption = (EditText) findViewById(R.id.editTextCaption);

	}

	/**
	 * 
	 * developer:manpreets2 date:Nov 3, 2015 return:void description: method for
	 * post data on server on click submit
	 */
	public void onCLickSubmit(View view) {
		if (isConnectedToInternet()) {
			startTaskStepNewsrevice();
		} else {
			new Utility().showCustomDialog(
					Constant.internetConnectionPopupButtonText,
					Constant.internetConnectionTitle,
					Constant.internetConnectionMessage, false,
					AddImageActivity.this, null, null);
		}

	}

	/**
	 * 
	 * developer:manpreets2 date:Nov 3, 2015 return:void description: method for
	 * launch popup for image selection
	 */
	public void onClickImageSelection(View view) {
		selectImage();
	}

	/**
	 * 
	 * developer:manpreets2 date:Nov 3, 2015 return:void description: method for
	 * select image from gallery or camera
	 */
	private void selectImage() {

		final CharSequence[] options = { "Take Photo", "Choose from Gallery",
				"Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(
				AddImageActivity.this);
		builder.setTitle("Select Photo");
		builder.setItems(options, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (options[item].equals("Take Photo")) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File f = new File(android.os.Environment
							.getExternalStorageDirectory(), "temp.jpg");
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
					startActivityForResult(intent, 1);
				} else if (options[item].equals("Choose from Gallery")) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intent, 2);

				} else if (options[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 1) {

				File f = new File(Environment.getExternalStorageDirectory()
						.toString());
				for (File temp : f.listFiles()) {
					if (temp.getName().equals("temp.jpg")) {
						f = temp;
						break;
					}
				}

				try {
					Bitmap bitmap = null;
					BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

					bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
							bitmapOptions);

					String path = android.os.Environment
							.getExternalStorageDirectory()
							+ File.separator
							+ "Taskism" + File.separator + "default";
					f.delete();
					OutputStream outFile = null;
					File file = new File(path, String.valueOf(System
							.currentTimeMillis()) + ".jpg");
					try {
						outFile = new FileOutputStream(file);
						bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
						outFile.flush();
						outFile.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					mImgCamera.setImageBitmap(bitmap);
					// byteArray = Utility.encodeToByte(bitmap);
					mBase64 = Utility.encodeTobase64(bitmap);

					if (bitmap != null) {
						try {
							// bitmap.recycle();
							bitmap = null;
						} catch (OutOfMemoryError e) {
							e.printStackTrace();
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (requestCode == 2) {

				Uri selectedImage = data.getData();
				String[] filePath = { MediaStore.Images.Media.DATA };
				Cursor c = getContentResolver().query(selectedImage, filePath,
						null, null, null);
				c.moveToFirst();
				int columnIndex = c.getColumnIndex(filePath[0]);
				String picturePath = c.getString(columnIndex);
				c.close();
				Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
				mImgCamera.setImageBitmap(thumbnail);
				mBase64 = Utility.encodeTobase64(thumbnail);
				if (thumbnail != null) {

					try {
						// thumbnail.recycle();
						thumbnail = null;
					} catch (OutOfMemoryError e) {
						e.printStackTrace();
					}

				}
			}
		}

	}

	/**
	 * 
	 * developer:manpreets2 date:Nov 3, 2015 return:void description: method for
	 * implement task step new service
	 */
	private void startTaskStepNewsrevice() {

		new ServerAsyncTask(ApplicationConstant.appurl
				+ ApplicationConstant.getTaskStepNewRequest + "&userid=62"
				+ "&taskid=" + taskId + "&description="
				+ mEdtCaption.getText().toString(), context,
				new ResponseCallback() {

					@Override
					public void onSuccessRecieve(Object object) {
						System.out.println("onSuccessRecieve" + object);
					}

					@Override
					public void onErrorRecieve(Object object) {
						showToastMessage((String) object);

					}
				}, ApplicationConstant.getTaskStepNewRequest).execute();
	}
}
