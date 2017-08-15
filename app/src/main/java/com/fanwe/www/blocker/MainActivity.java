package com.fanwe.www.blocker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickDuration(View v)
    {
        startActivity(new Intent(this, DurationBlockerActivity.class));
    }

    public void onClickBlocker(View v)
    {
        startActivity(new Intent(this, OnClickBlockerActivity.class));
    }

    public void onClickRunnable(View v)
    {
        startActivity(new Intent(this, RunnableBlockerActivity.class));
    }

}
