package com.yrg.firstdaily.activity.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yrg.firstdaily.R;
import com.yrg.firstdaily.adapter.StoryListAdapter;
import com.yrg.firstdaily.adapter.TopStoryPagerAdapter;
import com.yrg.firstdaily.entity.DateStory;
import com.yrg.firstdaily.entity.HomeContent;
import com.yrg.firstdaily.entity.TopStory;
import com.yrg.firstdaily.net.URLConstant;
import com.yrg.firstdaily.ui.MyLinearLayoutManager;
import com.yrg.firstdaily.util.GsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private static final String TAG = "HomeFragment";
    private List<TopStory> topStoryList;
    private ViewPager vpTopStories;
    private int pagerNumber = 0;
    private int currentPager = 0;
    private static final int DELAY_MILLION_SECONDS = 3000;

    private RecyclerView recyclerView;
    private List<DateStory> dateStoryList;

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

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        MyLinearLayoutManager layoutManager = new MyLinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setAutoMeasureEnabled(false);
        layoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(layoutManager);

        vpTopStories = (ViewPager) view.findViewById(R.id.vp_top_stories);
        topStoryList = new ArrayList<>();
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
        TopStoryPagerAdapter pagerAdapter = new TopStoryPagerAdapter(getActivity(), topStoryList);
        vpTopStories.setAdapter(pagerAdapter);
        vpTopStories.addOnPageChangeListener(this);
    }

    private void ParseResponse(String response) {
        HomeContent homeContent = GsonUtil.GsonToEntity(response, HomeContent.class);
        topStoryList = homeContent.getTop_stories();
        pagerNumber = topStoryList.size();
        //构建带日期的story列表
        dateStoryList = new ArrayList<>();
        for (int i = 0; i < homeContent.getStories().size(); i++) {
            DateStory dateStory = new DateStory();
            dateStory.setDate(homeContent.getDate());
            dateStory.setId(homeContent.getStories().get(i).getId());
            dateStory.setTitle(homeContent.getStories().get(i).getTitle());
            dateStory.setImage(homeContent.getStories().get(i).getImages()[0]);
            dateStoryList.add(dateStory);
        }
        Log.i(TAG,"date story list size "+dateStoryList.size());
        recyclerView.setAdapter(new StoryListAdapter(getActivity(),dateStoryList));
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
