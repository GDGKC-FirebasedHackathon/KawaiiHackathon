package com.corikachu.yourname;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Default File Header.
 * Need a comments!
 */

public class AuthActivity extends AppCompatActivity {

    private static final String TAG = AuthActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);

    }

}
