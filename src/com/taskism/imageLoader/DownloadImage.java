/**
 * 
 */
package com.taskism.imageLoader;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.task.taskApplication.R;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * @author Manpreet
 * 
 */
public class DownloadImage {

	public void downloadImage(String url, ImageView imageView,
			final ProgressBar progressBar) {
		ImageLoader imageLoader = ImageLoader.getInstance();
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisk(true)
				.resetViewBeforeLoading(true)
				.showImageForEmptyUri(R.drawable.ic_place_holder)
				.showImageOnFail(R.drawable.ic_place_holder)
				.showImageOnLoading(R.drawable.ic_place_holder).build();
		imageLoader.displayImage(url, imageView, options,
				new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String arg0, View arg1) {
						progressBar.setVisibility(View.VISIBLE);
					}

					@Override
					public void onLoadingFailed(String arg0, View arg1,
							FailReason arg2) {
						progressBar.setVisibility(View.GONE);

					}

					@Override
					public void onLoadingComplete(String arg0, View arg1,
							Bitmap arg2) {
						progressBar.setVisibility(View.GONE);

					}

					@Override
					public void onLoadingCancelled(String arg0, View arg1) {
						progressBar.setVisibility(View.GONE);

					}
				});
	}

}
