package com.corikachu.yourname;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.corikachu.yourname.models.DTOFeed;
import com.corikachu.yourname.models.DTOSuggestion;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by loki on 2017. 2. 17..
 */

public class FeedDetailActivity extends AppCompatActivity {

    @Bind(R.id.activity_feed_detail_title)
    TextView textViewTitle;
    @Bind(R.id.activity_feed_detail_content)
    TextView textViewContent;
    @Bind(R.id.activity_feed_detail_edittext_new_suggestion)
    EditText editTextNewSuggestion;
    @Bind(R.id.activity_feed_detail_button_new_suggestion_submit)
    Button buttonNewSuggestionSubmit;

    private DTOFeed feed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_detail);

        ButterKnife.bind(this);

        feed = getIntent().getParcelableExtra("feed");
        textViewTitle.setText(feed.getTitle());
        textViewContent.setText(feed.getContent());
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
