package com.corikachu.yourname;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.corikachu.yourname.models.DTOFeed;
import com.corikachu.yourname.models.DTOSuggestion;

import java.util.List;

/**
 * Created by loki on 2017. 2. 17..
 */

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionViewHolder> {

    private Context context;
    private List<DTOSuggestion> items;

    private int lastPosition = -1;

    public SuggestionAdapter(List<DTOSuggestion> modelData, Context context) {
        if (modelData == null) {
            throw new IllegalArgumentException(
                    "modelData must not be null");
        }

        this.context = context;
        this.items = modelData;
    }

    @Override
    public SuggestionViewHolder onCreateViewHolder(
            ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_item_suggestion, viewGroup, false);
        return new SuggestionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SuggestionViewHolder viewHolder, int position) {
        DTOSuggestion model = items.get(position);
        viewHolder.textViewSuggestionName.setText(model.getContent());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(DTOSuggestion item) {
        items.add(item);
    }

}
