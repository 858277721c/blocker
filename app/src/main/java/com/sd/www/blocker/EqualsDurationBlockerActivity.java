package com.sd.www.blocker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sd.lib.blocker.FDurationBlocker;
import com.sd.lib.blocker.FEqualsBlocker;

public class EqualsDurationBlockerActivity extends AppCompatActivity
{
    private TextView tv_msg;
    private EditText et;
    private Button btn_send_msg;

    private final FDurationBlocker mDurationBlocker = new FDurationBlocker();
    private final FEqualsBlocker mEqualsBlocker = new FEqualsBlocker();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equals_duration_blocker);
        tv_msg = findViewById(R.id.tv_msg);
        et = findViewById(R.id.et);
        btn_send_msg = findViewById(R.id.btn_send_msg);

        btn_send_msg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sendMsg();
            }
        });

        mDurationBlocker.setAutoSaveLastLegalTime(false);
        mEqualsBlocker.setAutoSaveLastLegalObject(false);
    }

    private void sendMsg()
    {
        String msg = et.getText().toString();
        if (TextUtils.isEmpty(msg))
        {
            Toast.makeText(EqualsDurationBlockerActivity.this, "请输入消息", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mDurationBlocker.blockDuration(2000))
        {
            //拦截到间隔2000毫秒内的点击
            Toast.makeText(EqualsDurationBlockerActivity.this, "消息间隔不能小于2秒", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mEqualsBlocker.blockEquals(msg) && mDurationBlocker.blockDuration(5000))
        {
            //拦截到超过最大重复次数，并且间隔5000毫秒内的点击
            Toast.makeText(EqualsDurationBlockerActivity.this, "重复消息间隔不能小于5秒", Toast.LENGTH_SHORT).show();
            return;
        }

        mDurationBlocker.saveLastLegalTime(); //保存通过拦截的合法时间点，下次判断用到
        mEqualsBlocker.saveLastLegalObject(msg); //保存通过拦截的合法对象，下次判断用到

        tv_msg.append("\r\n" + msg);
        et.setText("");
    }
}
