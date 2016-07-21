package com.yrg.firstdaily.activity.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yrg.firstdaily.R;
import com.yrg.firstdaily.adapter.ThemeStoryListAdapter;
import com.yrg.firstdaily.entity.Story;
import com.yrg.firstdaily.entity.ThemePsychology;
import com.yrg.firstdaily.net.URLConstant;
import com.yrg.firstdaily.ui.MyLinearLayoutManager;
import com.yrg.firstdaily.ui.MyScrollView;
import com.yrg.firstdaily.util.GsonUtil;
import com.yrg.firstdaily.util.ScreenUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class PsychologyFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private MyScrollView scrollView;
    private RecyclerView recyclerView;
    private ImageView ivPsychology;
    private TextView tvPsychology;
    private ThemeStoryListAdapter adapter;
    private MyLinearLayoutManager layoutManager;
    private List<Story> storyList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_psychology, container, false);
        initView(view);
        storyList = new ArrayList<>();
        getData();
        return view;
    }

    private void initView(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_swipe_refresh);
        scrollView = (MyScrollView) view.findViewById(R.id.scroll_view);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        ivPsychology = (ImageView) view.findViewById(R.id.iv_psychology);
        tvPsychology = (TextView) view.findViewById(R.id.tv_psychology);
        layoutManager = new MyLinearLayoutManager(getActivity());
        layoutManager.setAutoMeasureEnabled(false);
        recyclerView.setLayoutManager(layoutManager);
        scrollView.setScrollChangeListener(new MyScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {

            }

            @Override
            public void onScrollBottom(boolean isBottom) {
                if (isBottom) {
                    Toast.makeText(getActivity(), "jlkjk", Toast.LENGTH_LONG);
                }
            }
        });
    }

    private void getData() {
        OkHttpUtils.get().url(URLConstant.URL_THEME_PSYCHOLOGY).build().execute(new StringCallback() {
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
        setHeader(GsonUtil.GsonToEntity(response, ThemePsychology.class));
        setBody(GsonUtil.GsonToEntity(response, ThemePsychology.class));
    }

    private void setHeader(ThemePsychology themePsychology) {
        Picasso.with(getActivity()).load(themePsychology.getImage()).resize(ScreenUtil.getScreenWidth(getActivity()), 220).centerCrop().into(ivPsychology);
        tvPsychology.setText(themePsychology.getDescription());
    }

    private void setBody(ThemePsychology themePsychology) {
        storyList = themePsychology.getStories();
        adapter = new ThemeStoryListAdapter(getActivity(), storyList);
        recyclerView.setAdapter(adapter);
    }

}
