package com.gavin.asmdemo.widget.recycler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gavin.asmdemo.R;

public class CustomRecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_recycler_view);
        RecyclerView recyclerView = findViewById(R.id.mRv);
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public View onCreateViewHolder(int position, View contentView, ViewGroup parent) {
                contentView = CustomRecyclerViewActivity.this.getLayoutInflater().inflate(R.layout.custom_rv, parent, false);
                TextView textView = contentView.findViewById(R.id.textView);
                textView.setText("第" + position + "行");
                return contentView;
            }

            @Override
            public View onBindViewHolder(int position, View contentView, ViewGroup parent) {
                TextView textView = contentView.findViewById(R.id.textView);
                textView.setText("自定义" + position);
                return contentView;
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }

            @Override
            public int getViewTypeCount(int position) {
                return 1;
            }

            @Override
            public int getCount() {
                return 30;
            }

            @Override
            public int getHeight() {
                return 100;
            }
        });
    }
}
