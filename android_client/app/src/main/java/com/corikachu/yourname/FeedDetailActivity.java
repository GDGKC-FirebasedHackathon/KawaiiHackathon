package com.corikachu.yourname;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.corikachu.yourname.models.DTOFeed;
import com.corikachu.yourname.models.DTOSuggestion;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by loki on 2017. 2. 17..
 */

public class FeedDetailActivity extends AppCompatActivity {

    @Bind(R.id.activity_feed_detail_title)
    TextView textViewTitle;
    @Bind(R.id.activity_feed_detail_content)
    TextView textViewContent;
    @Bind(R.id.activity_feed_recyclerview_suggestion_list)
    RecyclerView suggestionListView;
    @Bind(R.id.activity_feed_detail_edittext_new_suggestion)
    EditText editTextNewSuggestion;
    @Bind(R.id.activity_feed_detail_button_new_suggestion_submit)
    Button buttonNewSuggestionSubmit;


    private DTOFeed feed;
    private SuggestionAdapter adapter;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_detail);

        ButterKnife.bind(this);

        feed = getIntent().getParcelableExtra("feed");
        textViewTitle.setText(feed.getTitle());
        textViewContent.setText(feed.getContent());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        suggestionListView.setLayoutManager(layoutManager);

        adapter = new SuggestionAdapter(new ArrayList<DTOSuggestion>(), this);
        suggestionListView.setAdapter(adapter);

        adapter.getOnItemClickSubject()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<DTOSuggestion>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DTOSuggestion suggestion) {
                        // TODO : voting
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        String endpoint = String.format(
                Locale.KOREA,
                "feeds/%d/suggestions", feed.getId());
        databaseReference.child(endpoint).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DTOSuggestion suggestion = dataSnapshot.getValue(DTOSuggestion.class);
                adapter.addItem(suggestion);
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

    @OnClick(R.id.activity_feed_detail_button_new_suggestion_submit)
    public void onClickNewSuggestionSubmit(View view) {
        String newSugString = editTextNewSuggestion.getText().toString();
        if (newSugString.isEmpty()) {
            Toast.makeText(this, "입력해", Toast.LENGTH_LONG).show();
            return;
        }

        DTOSuggestion newSuggestion = new DTOSuggestion(0, feed.getId(), newSugString, newSugString, 0, 0);
        DatabaseViewModel.getInstance().addSuggestion(feed.getId(), newSuggestion);
    }

}
