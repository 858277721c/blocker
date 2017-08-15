## SDDurationBlocker
可以通过此类限制频繁触发的任务，比如点击拦截<br>
效果图:<br>
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
