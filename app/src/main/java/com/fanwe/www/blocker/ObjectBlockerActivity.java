package com.fanwe.www.blocker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fanwe.library.blocker.SDObjectBlocker;

public class ObjectBlockerActivity extends AppCompatActivity
{

    private TextView tv_msg;
    private EditText et;
    private Button btn_send_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_blocker);
        tv_msg = (TextView) findViewById(R.id.tv_msg);
        et = (EditText) findViewById(R.id.et);
        btn_send_msg = (Button) findViewById(R.id.btn_send_msg);

        final SDObjectBlocker mObjectBlocker = new SDObjectBlocker();
        mObjectBlocker.setBlockDuration(2000); //设置拦截间隔，既不管是否重复，最快只能2000毫秒触发一次
        mObjectBlocker.setMaxEqualsCount(0); //设置允许最大重复的次数0，既一重复就判断和上一次重复之间的时长
        mObjectBlocker.setBlockEqualsDuration(5000); //拦截重复的时长，既5000毫秒内不允许有重复的

        btn_send_msg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String msg = et.getText().toString();
                if (TextUtils.isEmpty(msg))
                {
                    Toast.makeText(ObjectBlockerActivity.this, "请输入消息", 0).show();
                    return;
                }

                if (mObjectBlocker.block())
                {
                    Toast.makeText(ObjectBlockerActivity.this, "消息间隔不能小于2秒", 0).show();
                    return;
                }
                if (mObjectBlocker.blockObject(msg))
                {
                    Toast.makeText(ObjectBlockerActivity.this, "重复消息间隔不能小于5秒", 0).show();
                    return;
                }

                tv_msg.append("\r\n" + msg);
            }
        });
    }

}
