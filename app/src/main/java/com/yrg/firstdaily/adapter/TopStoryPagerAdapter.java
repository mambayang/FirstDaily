package com.yrg.firstdaily.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yrg.firstdaily.R;
import com.yrg.firstdaily.activity.StoryActivity;
import com.yrg.firstdaily.entity.TopStory;
import com.yrg.firstdaily.net.URLConstant;
import com.yrg.firstdaily.util.ScreenUtil;

import java.util.List;

/**
 * Created by yrg on 2016/6/14.
 */
public class TopStoryPagerAdapter extends PagerAdapter {
    private static final String TAG = "TopStoryPagerAdapter";
    private Context context;
    private List<TopStory> topStoryList;

    public TopStoryPagerAdapter(Context context, List<TopStory> topStoryList) {
        this.context = context;
        this.topStoryList = topStoryList;
    }

    @Override
    public int getCount() {
        return topStoryList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final String storyId = topStoryList.get(position).getId();
        String storyTitle = topStoryList.get(position).getTitle();
        String storyImage = topStoryList.get(position).getImage();
        //创建view
        View view = LayoutInflater.from(context).inflate(R.layout.item_top_story, container, false);
        ImageView ivTopStory = (ImageView) view.findViewById(R.id.iv_top_story);
        TextView tvTopStory = (TextView) view.findViewById(R.id.tv_top_story);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StoryActivity.class);
                intent.putExtra("story_url", URLConstant.URL_STORY_DETAIL + storyId);
                intent.putExtra("story_extra_url", URLConstant.URL_STORY_EXTRA + storyId);
                context.startActivity(intent);
            }
        });
        //向view设置图片与标题
        Picasso.with(context).load(storyImage).resize(ScreenUtil.getScreenWidth(context), 240).centerCrop().into(ivTopStory);
        tvTopStory.setText(storyTitle);
        //保存到容器与list中
        container.addView(view);
        return view;
    }
}
