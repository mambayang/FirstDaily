package com.yrg.firstdaily.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yrg.firstdaily.R;
import com.yrg.firstdaily.entity.TopStoryDetail;
import com.yrg.firstdaily.util.GsonUtil;
import com.yrg.firstdaily.util.ScreenUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import okhttp3.Call;

public class StoryActivity extends BaseActivity {

    private static final String TAG = "StoryActivity";
    private Toolbar toolbar;
    private ImageView ivStoryDetail;
    private TextView tvStoryDetail;
    private WebView webView;
    private String storyUrl;
    private String storyExtraUrl;

    @Override
    protected void loadData() {
        getStoryData();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_story);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ivStoryDetail = (ImageView) findViewById(R.id.iv_story_detail);
        tvStoryDetail = (TextView) findViewById(R.id.tv_story_detail);
        webView = (WebView) findViewById(R.id.web_view);
    }

    @Override
    protected void initVariables() {
        storyUrl = getIntent().getStringExtra("story_url");
        storyExtraUrl = getIntent().getStringExtra("story_extra_url");
    }

    private void getStoryData() {
        OkHttpUtils.get().url(storyUrl).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                parseResponse(response);
            }
        });
    }

    private void parseResponse(String response) {
        TopStoryDetail storyDetail = GsonUtil.GsonToEntity(response, TopStoryDetail.class);
        Picasso.with(this).load(storyDetail.getImage()).resize(ScreenUtil.getScreenWidth(this), 220).centerCrop().into(ivStoryDetail);
        tvStoryDetail.setText(storyDetail.getTitle());
//        Log.i(TAG, storyDetail.getCss()[0].toString());
        String body = "<link rel = 'stylesheet' href='" + storyDetail.getCss()[0] + "' type='text/css' >" + storyDetail.getBody();
        Log.i(TAG, body);
        webView.loadDataWithBaseURL(null, body, "text/html", "utf-8", null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_story_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_comment) {
            Toast.makeText(this, "comment", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.action_thumb_up) {
            Toast.makeText(this, "thumb up", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
