package com.codelang.wqthrottle;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.codelang.throttle.HandlerType;
import com.codelang.throttle.WQThrottle;

public class MainActivity extends AppCompatActivity implements WQThrottle.CallBack {


    public static final int ZAN_TAG = 0x01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //初始化
        WQThrottle.getInstance().init(HandlerType.MAIN_THREAD, this);
    }


    /**
     * 点赞效果处理
     *
     * @param view
     */
    public void zan(View view) {
        WQThrottle.getInstance().delay(ZAN_TAG, 2000, null);
    }


    /**
     * 结果回调
     *
     * @param tag 标识
     * @param obj 参数
     */
    @Override
    public void delayResult(int tag, Object obj) {
        switch (tag) {
            case ZAN_TAG:
                Toast.makeText(this, "点赞回应，参数值为" + obj, Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
