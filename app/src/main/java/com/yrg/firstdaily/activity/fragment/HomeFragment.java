package com.yrg.firstdaily.activity.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yrg.firstdaily.R;
import com.yrg.firstdaily.adapter.TopStoriesPagerAdapter;
import com.yrg.firstdaily.entity.TodayContent;
import com.yrg.firstdaily.entity.TodayTopStory;
import com.yrg.firstdaily.net.URLConstant;
import com.yrg.firstdaily.util.GsonUtil;
import com.yrg.firstdaily.util.ScreenUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private static final String TAG = "HomeFragment";
    private List<View> layoutList;
    private ViewPager vpTopStories;
    private int pagerNumber = 0;
    private int currentPager = 0;
    private static final int DELAY_MILLION_SECONDS = 3000;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (currentPager == pagerNumber - 1) {
                currentPager = -1;
            }
            vpTopStories.setCurrentItem(currentPager + 1);
            handler.sendEmptyMessageDelayed(0, DELAY_MILLION_SECONDS);
        }
    };

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        vpTopStories = (ViewPager) view.findViewById(R.id.vp_top_stories);
        layoutList = new ArrayList<>();
        getHomeData();
        return view;
    }

    private void getHomeData() {
        OkHttpUtils.get().url(URLConstant.URL_HOME_CONTENT).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                ParseResponse(response);
                setAdatper();
                beginLoop();
            }
        });
    }

    private void beginLoop() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessageDelayed(0, DELAY_MILLION_SECONDS);
            }
        }).start();
    }

    private void setAdatper() {
        TopStoriesPagerAdapter pagerAdapter = new TopStoriesPagerAdapter(layoutList);
        vpTopStories.setAdapter(pagerAdapter);
        vpTopStories.addOnPageChangeListener(this);
    }

    private void ParseResponse(String response) {
        TodayContent todayContent = GsonUtil.GsonToEntity(response, TodayContent.class);
        Log.i(TAG, todayContent.getDate());
        ArrayList<TodayTopStory> topStories = todayContent.getTop_stories();
        pagerNumber = topStories.size();
        for (int i = 0; i < pagerNumber; i++) {
            Log.i(TAG, topStories.get(i).getTitle());
            Log.i(TAG, topStories.get(i).getImage());
            //创建view
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_top_story, null);
            ImageView ivTopStoryImage = (ImageView) view.findViewById(R.id.iv_top_story_image);
            TextView tvTopStoryTitle = (TextView) view.findViewById(R.id.tv_top_story_title);
            //向view设置图片与标题
            Picasso.with(getActivity()).load(topStories.get(i).getImage()).resize(ScreenUtil.getScreenWidth(getContext()), 240).centerCrop().into(ivTopStoryImage);
            tvTopStoryTitle.setText(topStories.get(i).getTitle());
            //保存到list中
            layoutList.add(view);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        currentPager = position;
    }

    @Override
    public void onPageSelected(int position) {
        currentPager = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
