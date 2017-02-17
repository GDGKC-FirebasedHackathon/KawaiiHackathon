package com.corikachu.yourname;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.corikachu.yourname.models.DTOFeed;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by loki on 2017. 2. 17..
 */

public class FeedDetailActivity extends AppCompatActivity {

    @Bind(R.id.activity_feed_detail_title)
    TextView textViewTitle;
    @Bind(R.id.activity_feed_detail_content)
    TextView textViewContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_detail);

        ButterKnife.bind(this);

        DTOFeed feed = getIntent().getParcelableExtra("feed");
        textViewTitle.setText(feed.getTitle());
        textViewContent.setText(feed.getContent());
    }

}
