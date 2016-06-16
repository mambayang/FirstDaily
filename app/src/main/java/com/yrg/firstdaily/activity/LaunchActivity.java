package com.yrg.firstdaily.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yrg.firstdaily.R;
import com.yrg.firstdaily.entity.LaunchInfo;
import com.yrg.firstdaily.net.URLConstant;
import com.yrg.firstdaily.util.GsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;

public class LaunchActivity extends BaseActivity {
    private static final String TAG = "LaunchActivity";
    private static final int MESSAGE_GOTOMAIN = 1001;
    private TextView tvLaunch;
    private SimpleDraweeView dvLaunch;

    @Override
    protected void loadData() {
        getLaunchInfo();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_launch);
        Log.i(TAG, "setContentView");
        tvLaunch = (TextView) findViewById(R.id.tv_launch);
        dvLaunch = (SimpleDraweeView) findViewById(R.id.dv_launch);
    }

    @Override
    protected void initVariables() {

    }

    private void getLaunchInfo() {
        OkHttpUtils.get().url(URLConstant.URL_LAUNCH).build()
                .execute(new StringCallback() {
                    public void onResponse(String response, int arg1) {
                        ParseResponse(response);
                    }

                    ;

                    public void onError(Call arg0, Exception arg1,
                                        int arg2) {

                    }

                    ;
                });
    }

    private void ParseResponse(String response) {
        try {
            Log.i(TAG, response);
            JSONObject jsonObject = new JSONObject(response);
            ArrayList<LaunchInfo> infoList = GsonUtil.fromJsonList(
                    jsonObject.getString("creatives"), LaunchInfo.class);
            if (infoList.size() > 0) {
                LaunchInfo launchInfo = infoList.get(0);
                Log.i(TAG, launchInfo.getText());
                Log.i(TAG, launchInfo.getUrl());
                dvLaunch.setImageURI(Uri.parse(launchInfo.getUrl()));
                tvLaunch.setText(launchInfo.getText());
                handler.sendEmptyMessageDelayed(MESSAGE_GOTOMAIN, 2500);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_GOTOMAIN:
                    goToMainActivity();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void goToMainActivity() {
        Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
