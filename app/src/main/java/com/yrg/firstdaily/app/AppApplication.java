package com.yrg.firstdaily.app;

import com.facebook.drawee.backends.pipeline.Fresco;

import android.app.Application;

public class AppApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		Fresco.initialize(this);
	}
}
