package com.codelang.wqthrottle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.codelang.throttle.WQThrottle;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
    }

    public void sendMsg(View view) {
        WQThrottle.getInstance().delay(MainActivity.EVENT_TAG, 0, "我是 EventActivity 过来的消息");
        finish();
    }
}
