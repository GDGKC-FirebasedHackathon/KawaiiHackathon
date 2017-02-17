package com.corikachu.yourname;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by loki on 2017. 2. 17..
 */

public class SuggestionViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.recyclerview_item_suggestion_name)
    TextView textViewSuggestionName;

    @Bind(R.id.recyclerview_item_button_suggestion_like)
    Button buttonSuggestionLike;

    public SuggestionViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public Button getButtonSuggestionLike() {
        return buttonSuggestionLike;
    }
}
