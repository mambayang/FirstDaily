package com.yrg.firstdaily.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

/**
 * Created by yrg on 2016/6/14.
 */
public class TopStoriesPagerAdapter extends PagerAdapter {
    private List<ImageView> layoutList;

    public TopStoriesPagerAdapter(List<ImageView> layoutList) {
        this.layoutList = layoutList;
    }

    @Override
    public int getCount() {
        return layoutList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView(layoutList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(layoutList.get(position));
        return layoutList.get(position);
    }
}
