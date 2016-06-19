package com.yrg.firstdaily.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yrg.firstdaily.R;
import com.yrg.firstdaily.entity.DateStory;

import java.util.List;

/**
 * Created by yrg on 2016/6/16.
 */
public class StoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "StoryListAdapter";
    private static final int ITEM_NORMAL = 0;
    private static final int ITEM_DATE = 1;

    private Context context;
    private List<DateStory> dateStoryList;
    private LayoutInflater layoutInflater;

    public StoryListAdapter(Context context, List<DateStory> dateStoryList) {
        this.context = context;
        this.dateStoryList = dateStoryList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_NORMAL) {
            return new CardViewNormalHolder(layoutInflater.inflate(R.layout.item_card_view_normal, parent, false));
        } else {
            return new CardViewDateHolder(layoutInflater.inflate(R.layout.item_card_view_date, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DateStory dateStory = dateStoryList.get(position);
        if (dateStory == null) {
            return;
        }
        if (holder instanceof CardViewDateHolder) {
            bindDateItem(dateStory, (CardViewDateHolder) holder);
        } else {
            CardViewNormalHolder normalHolder = (CardViewNormalHolder) holder;
            bindNormalItem(dateStory, normalHolder.tvCardView, normalHolder.ivCardView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_DATE;
        }
        String currentDate = dateStoryList.get(position).getDate();
        int preItem = position - 1;
        boolean isDiff = !dateStoryList.get(preItem).getDate().equals(currentDate);
        return isDiff ? ITEM_DATE : ITEM_NORMAL;
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(dateStoryList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return dateStoryList.size();
    }

    private void bindDateItem(DateStory dateStory, CardViewDateHolder holder) {
        bindNormalItem(dateStory, holder.tvCardView, holder.ivCardView);
        holder.tvCardViewDate.setText(dateStory.getDate());
    }

    private void bindNormalItem(DateStory dateStory, TextView tvCardView, ImageView ivCardView) {
        if (TextUtils.isEmpty(dateStory.getImage())) {
            if (ivCardView.getVisibility() != View.GONE) {
                ivCardView.setVisibility(View.GONE);
            }
        } else {
            Picasso.with(context).load(dateStory.getImage()).into(ivCardView);
            if (ivCardView.getVisibility() != View.VISIBLE) {
                ivCardView.setVisibility(View.VISIBLE);
            }
        }
        tvCardView.setText(dateStory.getTitle());
    }


    public class CardViewNormalHolder extends RecyclerView.ViewHolder {
        TextView tvCardView;
        ImageView ivCardView;

        public CardViewNormalHolder(View itemView) {
            super(itemView);
            tvCardView = (TextView) itemView.findViewById(R.id.tv_card_view);
            ivCardView = (ImageView) itemView.findViewById(R.id.iv_card_view);
            itemView.findViewById(R.id.layout_card_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, getLayoutPosition() + "");
                }
            });
        }
    }

    public class CardViewDateHolder extends CardViewNormalHolder {
        TextView tvCardViewDate;

        public CardViewDateHolder(View itemView) {
            super(itemView);
            tvCardViewDate = (TextView) itemView.findViewById(R.id.tv_card_view_date);
        }
    }

}
