package com.codelang.wqthrottle;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codelang.throttle.HandlerType;
import com.codelang.throttle.WQThrottle;

public class MainActivity extends AppCompatActivity implements WQThrottle.CallBack {


    public static final int ZAN_TAG = 0x01;
    public static final int SEARCH_TAG = 0x02;
    public static final int EVENT_TAG = 0x03;

    TextView txEventMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txEventMsg = findViewById(R.id.main_event_msg);

        AppCompatEditText editText = findViewById(R.id.main_edit);

        registerTextWatcher(editText);
        //初始化
        WQThrottle.getInstance().register(this);
    }


    /**
     * 点赞效果处理
     *
     * @param view
     */
    public void zan(View view) {
        //延时 1s
        WQThrottle.getInstance().delay(ZAN_TAG, 1000, null);

    }

    /**
     * recyclerView 示例
     *
     * @param view
     */
    public void openRecycleView(View view) {
        startActivity(new Intent(this, RecyclerActivity.class));
    }

    public void eventClick(View view){
        startActivity(new Intent(this, EventActivity.class));
    }



    /**
     * editText 搜索场景
     *
     * @param editText
     */
    public void registerTextWatcher(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                WQThrottle.getInstance().delay(SEARCH_TAG, 1000, s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 结果回调
     *
     * @param tag 标识
     * @param obj 参数
     */
    @Override
    public void throttleResult(int tag, Object obj) {
        Log.e("TAG", "current thread : " + Looper.myLooper());
        switch (tag) {
            case ZAN_TAG:
                Toast.makeText(this, "点赞回应，参数值为" + obj, Toast.LENGTH_SHORT).show();
                break;
            case SEARCH_TAG:
                CharSequence s = (CharSequence) obj;
                Toast.makeText(this, "搜索回应，参数值为" + (s.toString()), Toast.LENGTH_SHORT).show();
                break;
            case EVENT_TAG:
                txEventMsg.setText((String) obj);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        WQThrottle.getInstance().unregister(this);
    }
}
