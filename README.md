# Description
该库主要工作于去重处理，对于多次点击事件，在 delay 延时期间只触发一次，[应用场景](https://juejin.im/post/5b9a4f37f265da0a8b570e96)


#Simple usage
该库分为四个步骤:
- 注册监听
- 发送延迟消息
- 接收消息
- 反注册

> 初始化
```
WQThrottle.getInstance().register(this);
```
> 发送延迟消息
```
 WQThrottle.getInstance().delay(ZAN_TAG, 1000, null);
```
> 接收消息
``` 
 @Override
 public void throttleResult(int tag, Object obj) {
        switch (tag) {
            case ZAN_TAG:
                Toast.makeText(this, "点赞回应，参数值为" + obj, Toast.LENGTH_SHORT).show();
                break;
            }
 }
```
> 反注册
``` 
WQThrottle.getInstance().unregister(this);
```


# Advanced usage

- 实现 EventBus 效果的事件消息


# Screen

![]("screen.gif")