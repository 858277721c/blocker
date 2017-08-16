## SDDurationBlocker
应用场景：限制频繁触发的任务，比如点击拦截<br>
效果图：<br>
![](http://thumbsnap.com/i/deq8GrUH.gif?0815)<br>
```java
final SDDurationBlocker mDurationBlocker = new SDDurationBlocker(1000); //设置拦截间隔为1000
btn_click.setOnClickListener(new View.OnClickListener()
{
    @Override
    public void onClick(View v)
    {
        if (mDurationBlocker.block())
        {
            //拦截掉
            return;
        }

        mClickCount++;
        btn_click.setText(String.valueOf(mClickCount)); // 更新点击次数
    }
});
```
## SDOnClickBlocker
内部基于SDDurationBlocker实现的点击拦截，可以设置全局拦截间隔和单独对某个view设置拦截间隔<br>
效果图：<br>
![](http://thumbsnap.com/i/Sz7tFHWT.gif?0815)<br>
```java
//未拦截Button
findViewById(R.id.btn_normal).setOnClickListener(new View.OnClickListener()
{
    @Override
    public void onClick(View v)
    {
        mClickCount++;
        tv_click_count.setText(String.valueOf(mClickCount)); //更新点击次数
    }
});

//全局拦截Button，拦截间隔为1000毫秒
SDOnClickBlocker.setGlobalBlockDuration(1000); //设置全局拦截间隔
SDOnClickBlocker.setOnClickListener(findViewById(R.id.btn_global), new View.OnClickListener()
{
    @Override
    public void onClick(View v)
    {
        mClickCount++;
        tv_click_count.setText(String.valueOf(mClickCount)); //更新点击次数
    }
});

//单独拦截Button，拦截间隔为2000毫秒
SDOnClickBlocker.setOnClickListener(findViewById(R.id.btn_private), 2000, new View.OnClickListener()
{
    @Override
    public void onClick(View v)
    {
        mClickCount++;
        tv_click_count.setText(String.valueOf(mClickCount)); //更新点击次数
    }
});
```
## SDEqualsDurationBlocker
继承自SDDurationBlocker，增加可以拦截重复对象的功能<br>
应用场景：直播间聊天公屏要求用户发消息限制为最快只能2秒发一次，并且如果有重复的消息的话必须间隔5秒才能发<br>
效果图：<br>
![](http://thumbsnap.com/i/KXXZyARA.gif?0815)<br>
```java
final SDEqualsDurationBlocker blocker = new SDEqualsDurationBlocker();
blocker.setBlockDuration(2000); //设置拦截间隔，既不管是否重复，最快只能2000毫秒触发一次
blocker.setMaxEqualsCount(0); //设置允许最大重复的次数0，既一重复就判断和上一次重复之间的时长
blocker.setBlockEqualsDuration(5000); //拦截重复的时长，既5000毫秒内不允许有重复的

btn_send_msg.setOnClickListener(new View.OnClickListener()
{
    @Override
    public void onClick(View v)
    {
        String msg = et.getText().toString();
        if (TextUtils.isEmpty(msg))
        {
            Toast.makeText(EqualsDurationBlockerActivity.this, "请输入消息", 0).show();
            return;
        }
        if (blocker.block())
        {
            Toast.makeText(EqualsDurationBlockerActivity.this, "消息间隔不能小于2秒", 0).show();
            return;
        }
        if (blocker.blockEquals(msg))
        {
            Toast.makeText(EqualsDurationBlockerActivity.this, "重复消息间隔不能小于5秒", 0).show();
            return;
        }
        blocker.saveLastLegalTime(); //保存通过拦截的合法时间点，下次判断用到
        blocker.saveLastLegalObject(msg); //保存通过拦截的合法对象，下次判断用到

        tv_msg.append("\r\n" + msg);
    }
});
```
