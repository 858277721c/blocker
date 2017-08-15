## SDDurationBlocker
应用场景：限制频繁触发的任务，比如点击拦截<br>
效果图：<br>
![](http://thumbsnap.com/i/1wilNgy1.gif?0815)<br>
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
![](http://thumbsnap.com/i/iUZuaXlg.gif?0815)<br>
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
## SDObjectBlocker
继承自SDDurationBlocker，增加可以拦截重复对象的功能<br>
应用场景：直播间聊天公屏要求用户发消息限制为最快只能2秒发一次，并且如果有重复的消息的话必须间隔5秒才能发<br>
效果图：<br>
![](http://thumbsnap.com/i/KXXZyARA.gif?0815)<br>
