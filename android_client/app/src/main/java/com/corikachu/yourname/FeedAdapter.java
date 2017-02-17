package com.corikachu.yourname;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.corikachu.yourname.models.DTOFeed;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by loki on 2017. 2. 17..
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {

    private Context context;
    private List<DTOFeed> items;

    private final PublishSubject<DTOFeed> onClickSubject = PublishSubject.create();

    public FeedAdapter(List<DTOFeed> modelData, Context context) {
        if (modelData == null) {
            throw new IllegalArgumentException(
                    "modelData must not be null");
        }

        this.context = context;
        this.items = modelData;
    }

    @Override
    public FeedViewHolder onCreateViewHolder(
            ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.recyclerview_item_feed, viewGroup, false);
        return new FeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder viewHolder, int position) {
        DTOFeed model = items.get(position);
        viewHolder.textViewTitle.setText(model.getTitle());
        viewHolder.textViewContent.setText(model.getContent());

        final DTOFeed bindedData = this.items.get(position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(bindedData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(DTOFeed item) {
        items.add(item);
    }

    public void clear() {
        this.items.clear();
    }

    public Observable<DTOFeed> getOnItemClickSubject() {
        return onClickSubject;
    }

}
