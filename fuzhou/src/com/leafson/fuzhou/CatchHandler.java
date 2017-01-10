package com.leafson.fuzhou;

import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.widget.Toast;

public class CatchHandler implements UncaughtExceptionHandler {

	private CatchHandler() {
	}

	public static CatchHandler getInstance() {

		return mCatchHandler;
	}

	private static CatchHandler mCatchHandler = new CatchHandler();

	private Context mContext;

	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (thread.getName().equals("main")) {
			ToastException(thread, ex);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
			}
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		} else {
			handleException(thread, ex);
		}

	}

	public void init(Context context) {
		mContext = context;
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	private void ToastException(final Thread thread, final Throwable ex) {
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				StringBuilder builder = new StringBuilder();
				builder.append("At thread: ").append(thread.getName())
						.append("\n");
				builder.append("Exception is :\n").append(ex.getMessage());

				Toast.makeText(mContext, builder.toString(), Toast.LENGTH_LONG)
						.show();
				Looper.loop();
			}
		}.start();
	}

	private void handleException(final Thread thread, final Throwable ex) {
		Intent intent = new Intent("com.leafson.lifecycle.MainActivity");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(intent);
	}
}
