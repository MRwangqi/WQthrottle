package com.codelang.wqthrottle;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codelang.throttle.WQThrottle;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity implements WQThrottle.CallBack {
    List<String> list = new ArrayList<>();

    void initData() {
        for (int i = 0; i < 10; i++) {
            list.add("throttle");
        }
    }

    private static final int OFFSET = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        initData();
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerAdapter(list));


        WQThrottle.getInstance().register(this);
    }


    @Override
    public void throttleResult(int tag, Object obj) {
        //拿到 position
        int position = tag - OFFSET;

        if (position < list.size() && position >= 0) {
            //将转拿到对应的参数
            String result = (String) obj;
            Toast.makeText(this, result + " position:" + position, Toast.LENGTH_SHORT).show();
        }
    }


    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
        List<String> list;

        public RecyclerAdapter(List<String> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {

            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_text, viewGroup, false);

            final RecyclerViewHolder holder = new RecyclerViewHolder(v);
            //item 点击事件
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //加 offset 的主要原因是避免唤醒其他页面的通知
                    WQThrottle.getInstance().delay(OFFSET + holder.getLayoutPosition(), 1000, list.get(i));
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
            recyclerViewHolder.setText(list.get(i) + " position:" + i);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }


    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txView;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txView = itemView.findViewById(R.id.item_text);
        }

        public void setText(String text) {
            txView.setText(text);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WQThrottle.getInstance().unregister(this);
    }
}
