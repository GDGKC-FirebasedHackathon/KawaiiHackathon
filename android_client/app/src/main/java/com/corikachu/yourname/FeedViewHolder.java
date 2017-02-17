package com.corikachu.yourname;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by loki on 2017. 2. 17..
 */

public class FeedViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.recyclerview_item_feed_title)
    TextView textViewTitle;
    @Bind(R.id.recyclerview_item_feed_content)
    TextView textViewContent;

    public FeedViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
