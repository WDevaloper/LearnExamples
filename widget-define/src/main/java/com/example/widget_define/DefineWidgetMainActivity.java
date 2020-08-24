package com.example.widget_define;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.TraceCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DefineWidgetMainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private StackAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_widget_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StackAdapter(this);
        recyclerView.addItemDecoration(new TimeAxisItemDecoration(this, dynasty));
        recyclerView.setAdapter(adapter);
    }

    private static List<String> dynasty = new ArrayList<>();

    static {
        for (int i = 0; i < 50; i++) {
            dynasty.add("position: " + i);
        }
    }

    static class StackAdapter extends RecyclerView.Adapter<StackViewHolder> {
        private final Context context;

        public StackAdapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public StackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.e("StackLayoutManager", "onCreateViewHolder");
            View view = LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false);
            return new StackViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StackViewHolder holder, int position) {
            holder.textView.setText("TextView " + position);
            holder.itemView.setBackgroundColor(Color.rgb(position * 20 + 50, position * 20 + 60, position * 20 + 75));
        }


        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public int getItemCount() {
            return dynasty.size();
        }
    }

    static class StackViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public StackViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
