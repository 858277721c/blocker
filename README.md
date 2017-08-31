## Gradle
`compile 'com.fanwe.android:blocker:1.0.1'`

## SDDurationBlocker
应用场景：限制频繁触发的任务，比如点击拦截<br>
效果图：<br>
![](http://thumbsnap.com/i/deq8GrUH.gif?0815)<br>
```java
final SDDurationBlocker blocker = new SDDurationBlocker(1000); //设置默认拦截间隔为1000毫秒
btn_click.setOnClickListener(new View.OnClickListener()
{
    @Override
    public void onClick(View v)
    {
        //if (blocker.block(1000)) //动态指定拦截间隔为1000毫秒
        if (blocker.block())
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
blocker.setMaxEqualsCount(0); //设置允许最大重复的次数0
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
        if (blocker.block(2000))
        {
            //拦截到间隔2000毫秒内的点击
            Toast.makeText(EqualsDurationBlockerActivity.this, "消息间隔不能小于2秒", 0).show();
            return;
        }
        if (blocker.blockEquals(msg) && blocker.block(5000))
        {
            //拦截到超过最大重复次数，并且间隔5000毫秒内的点击
            Toast.makeText(EqualsDurationBlockerActivity.this, "重复消息间隔不能小于5秒", 0).show();
            return;
        }
        blocker.saveLastLegalTime(); //保存通过拦截的合法时间点，下次判断用到
        blocker.saveLastLegalObject(msg); //保存通过拦截的合法对象，下次判断用到

        tv_msg.append("\r\n" + msg);
    }
});
```
## SDRunnableBlocker
当某一种耗性能的相同任务需要频繁被执行的时候，可以用这个类来限制执行的频率<br>
当调用postDelayed方法post一个延迟Runable之后，会有以下3种情况：<br>
1. 如果在延迟间隔内没有再次post，则延迟间隔到后执行该Runnable
2. 如果在延迟间隔内再次post，并且拦截次数小于最大拦截次数，则取消已经post的延迟Runnable，重新post当前延迟Runnable，拦截次数加一
3. 如果在延迟间隔内再次post，并且拦截次数大于最大拦截次数，则立即执行Runnable，重置拦截次数<br>
模拟效果图：<br>
![](http://thumbsnap.com/i/9DphluuT.gif?0815)<br>
```java
public void onClickStart500(View view)
{
    mBlocker.setMaxBlockCount(3); //设置延迟间隔内最大可以拦截3次，超过3次则立即执行
    mLooper.start(500, new Runnable() //模拟每隔500毫秒请求执行一次的场景
    {
        @Override
        public void run()
        {
            mBlocker.postDelayed(mTargetRunnable, 3000); //尝试post一个3000毫秒后执行的Runnable

            mRequestCount++;
            tv_block_msg.setText("请求执行次数：" + mRequestCount);
        }
    });
}

/**
 * 模拟耗性能Runnable
 */
private Runnable mTargetRunnable = new Runnable()
{
    @Override
    public void run()
    {
        mRealCount++;
        tv_msg.setText("实际执行次数：" + String.valueOf(mRealCount));
    }
};
```


