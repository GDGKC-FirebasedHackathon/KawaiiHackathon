package com.corikachu.yourname;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.corikachu.yourname.models.DTOFeed;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by loki on 2017. 2. 17..
 */

public class NewFeedActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.activity_new_feed_edit_text_title)
    EditText editTextTitle;
    @Bind(R.id.activity_new_feed_edit_text_content)
    EditText editTextContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_feed);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_new_feed_submit) {
            String title = editTextTitle.getText().toString();
            String content = editTextContent.getText().toString();
            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(getApplicationContext(), "다 채워", Toast.LENGTH_LONG).show();
                return false;
            }

            DTOFeed newFeed = new DTOFeed(0, 0, title, content, 0, 0);
            DatabaseViewModel.getInstance().addFeed(newFeed);

            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
