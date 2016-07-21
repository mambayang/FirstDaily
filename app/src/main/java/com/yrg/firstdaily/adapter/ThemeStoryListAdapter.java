package com.yrg.firstdaily.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yrg.firstdaily.R;
import com.yrg.firstdaily.entity.Story;

import java.util.List;

/**
 * Created by yrg on 2016/6/20.
 */
public class ThemeStoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "ThemeStoryListAdapter";
    private static final int ITEM_NORMAL = 0;
    private static final int ITEM_IMAGE = 1;
    private Context context;
    private List<Story> storyList;
    private LayoutInflater layoutInflater;

    public ThemeStoryListAdapter(Context context, List<Story> storyList) {
        this.context = context;
        this.storyList = storyList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_NORMAL) {
            return new ThemeStoryNormalHolder(layoutInflater.inflate(R.layout.item_theme_story_normal, parent, false));
        } else {
            return new ThemeStoryImageHolder(layoutInflater.inflate(R.layout.item_theme_story_image, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Story story = storyList.get(position);
        if (story == null) {
            return;
        }
        if (holder instanceof ThemeStoryNormalHolder) {
            bindNormalHolder(story, (ThemeStoryNormalHolder) holder);
        } else {
            bindImageHolder(story, (ThemeStoryImageHolder) holder);
        }
    }

    private void bindNormalHolder(Story story, ThemeStoryNormalHolder holder) {
        TextView tvNormal = holder.tvThemeStoryNormal;
        tvNormal.setText(story.getTitle());
    }

    private void bindImageHolder(Story story, ThemeStoryImageHolder holder) {
        TextView tvImage = holder.tvThemeStoryImage;
        ImageView ivImage = holder.ivThemeStoryImage;
        Picasso.with(context).load(story.getImages()[0]).into(ivImage);
        tvImage.setText(story.getTitle());
    }

    @Override
    public int getItemViewType(int position) {
        if (storyList.get(position).getImages().length > 0) {
            return ITEM_IMAGE;
        } else {
            return ITEM_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(storyList.get(position).getId());
    }

    public class ThemeStoryNormalHolder extends RecyclerView.ViewHolder {
        private TextView tvThemeStoryNormal;

        public ThemeStoryNormalHolder(View itemView) {
            super(itemView);
            tvThemeStoryNormal = (TextView) itemView.findViewById(R.id.tv_theme_story_normal);
        }
    }

    public class ThemeStoryImageHolder extends RecyclerView.ViewHolder {

        TextView tvThemeStoryImage;
        ImageView ivThemeStoryImage;

        public ThemeStoryImageHolder(View itemView) {
            super(itemView);
            tvThemeStoryImage = (TextView) itemView.findViewById(R.id.tv_theme_story_image);
            ivThemeStoryImage = (ImageView) itemView.findViewById(R.id.iv_theme_story_image);
        }
    }

}
