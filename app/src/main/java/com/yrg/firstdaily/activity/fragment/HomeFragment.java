package com.yrg.firstdaily.activity.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yrg.firstdaily.R;
import com.yrg.firstdaily.activity.StoryActivity;
import com.yrg.firstdaily.adapter.StoryListAdapter;
import com.yrg.firstdaily.adapter.TopStoryPagerAdapter;
import com.yrg.firstdaily.entity.BeforeStory;
import com.yrg.firstdaily.entity.DateStory;
import com.yrg.firstdaily.entity.HomeContent;
import com.yrg.firstdaily.entity.TopStory;
import com.yrg.firstdaily.net.URLConstant;
import com.yrg.firstdaily.ui.MyLinearLayoutManager;
import com.yrg.firstdaily.ui.MyScrollView;
import com.yrg.firstdaily.util.GsonUtil;
import com.yrg.firstdaily.util.ScreenUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private static final String TAG = "HomeFragment";
    private List<TopStory> topStoryList;
    private ViewPager vpTopStories;
    private int pagerNumber = 0;
    private int currentPager = 0;
    private static final int DELAY_MILLION_SECONDS = 3000;
    private int lastVisibleItem = 0;
    private Date savedDate;

    private SwipeRefreshLayout swipeRefreshLayout;
    private MyScrollView scrollView;
    private RecyclerView recyclerView;
    private StoryListAdapter adapter;
    private MyLinearLayoutManager layoutManager;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        vpTopStories = (ViewPager) view.findViewById(R.id.vp_top_stories);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_swipe_refresh);
        scrollView = (MyScrollView) view.findViewById(R.id.scroll_view);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRefreshData();
            }
        });
        scrollView.setScrollChangeListener(new MyScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
            }

            @Override
            public void onScrollBottom(boolean isBottom) {
                if (isBottom) {
                    getBeforeData();
                }
            }
        });

        layoutManager = new MyLinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setAutoMeasureEnabled(false);
        layoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(layoutManager);

        topStoryList = new ArrayList<>();
        savedDate = new Date();
        getHomeData();
        return view;
    }

    private void getRefreshData() {
        swipeRefreshLayout.setRefreshing(true);
        OkHttpUtils.get().url(URLConstant.URL_HOME_CONTENT).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                parseRefreshResponse(response);

            }
        });
    }

    private void parseRefreshResponse(String response) {
        dateStoryList.clear();
        HomeContent homeContent = GsonUtil.GsonToEntity(response, HomeContent.class);
        for (int i = 0; i < homeContent.getStories().size(); i++) {
            DateStory dateStory = new DateStory();
            dateStory.setDate(homeContent.getDate());
            dateStory.setId(homeContent.getStories().get(i).getId());
            dateStory.setTitle(homeContent.getStories().get(i).getTitle());
            dateStory.setImage(homeContent.getStories().get(i).getImages()[0]);
            dateStoryList.add(dateStory);
        }
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        savedDate = new Date();
    }

    private void getBeforeData() {
        OkHttpUtils.get().url(URLConstant.URL_BEFORE_CONTENT + new SimpleDateFormat("yyyyMMdd").format(savedDate)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                parseBeforeResponse(response);
            }
        });
    }

    private Date getBeforeDate(Date date) {
        Date beforeDate = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        beforeDate = calendar.getTime();
        return beforeDate;
    }

    private void parseBeforeResponse(String response) {
        BeforeStory beforeStory = GsonUtil.GsonToEntity(response, BeforeStory.class);
        String date = beforeStory.getDate();
        Log.i(TAG, "before date ..." + date);
        for (int i = 0; i < beforeStory.getStories().size(); i++) {
            DateStory dateStory = new DateStory();
            dateStory.setDate(date);
            dateStory.setId(beforeStory.getStories().get(i).getId());
            dateStory.setTitle(beforeStory.getStories().get(i).getTitle());
            dateStory.setImage(beforeStory.getStories().get(i).getImages()[0]);
            dateStoryList.add(dateStory);
        }
        adapter.notifyDataSetChanged();
        savedDate = getBeforeDate(savedDate);
    }

    private void getHomeData() {
        OkHttpUtils.get().url(URLConstant.URL_HOME_CONTENT).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                parseHomeResponse(response);
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

    private void parseHomeResponse(String response) {
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
        Log.i(TAG, "date story list size " + dateStoryList.size());
        adapter = new StoryListAdapter(getActivity(), dateStoryList);
        adapter.setOnItemClickListener(new StoryListAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i(TAG,"position is "+position);
                goToStoryActivity(position);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void goToStoryActivity(int position) {
        String storyId = dateStoryList.get(position).getId();
        Intent intent = new Intent(getActivity(), StoryActivity.class);
        intent.putExtra("story_url", URLConstant.URL_STORY_DETAIL + storyId);
        intent.putExtra("story_extra_url", URLConstant.URL_STORY_EXTRA + storyId);
        startActivity(intent);
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
