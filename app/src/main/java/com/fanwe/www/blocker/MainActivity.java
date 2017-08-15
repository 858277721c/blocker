package com.fanwe.www.blocker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.fanwe.library.blocker.SDOnClickBlocker;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SDOnClickBlocker.setGlobalBlockDuration(1000);
        SDOnClickBlocker.setOnClickListener(findViewById(R.id.btn), this);
    }

    @Override
    public void onClick(View v)
    {
        Log.i(TAG, "onClick:" + v);
    }

}
