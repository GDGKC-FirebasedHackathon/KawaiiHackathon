package com.corikachu.yourname;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.corikachu.yourname.models.DTOFeed;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FeedActivity extends AppCompatActivity {

    @Bind(R.id.activity_main_recyclerview_feed_list)
    RecyclerView feedListView;

    @Bind(R.id.activity_main_fab_new_feed)
    FloatingActionButton newFeedButton;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private FeedAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        feedListView.setLayoutManager(layoutManager);

        DatabaseViewModel.getInstance();
        adapter = new FeedAdapter(new ArrayList<DTOFeed>(), this);
        feedListView.setAdapter(adapter);

        adapter.getOnItemClickSubject()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<DTOFeed>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DTOFeed feed) {
                        Intent intent = new Intent(FeedActivity.this, FeedDetailActivity.class);
                        intent.putExtra("feed", feed);
                        FeedActivity.this.startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter.clear();

        databaseReference.child("feeds").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DTOFeed feed = dataSnapshot.getValue(DTOFeed.class);
                adapter.addItem(feed);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                // TODO : implement it
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    @OnClick(R.id.activity_main_fab_new_feed)
    public void onClickNewFeed(View view) {
        Intent intent = new Intent(this, NewFeedActivity.class);
        this.startActivity(intent);
    }

}
