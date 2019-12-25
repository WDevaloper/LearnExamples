package com.gavin.asmdemo.screen_adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gavin.asmdemo.R;
import com.gavin.asmdemo.screen_adapter.status.StatusUtil;

public class WangYiActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private LinearLayout toolBar2;
    private RecyclerView recycler;
    private NestedScrollView scrollView;
    private AppBarLayout mAppBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wangyi);
        mAppBarLayout = findViewById(R.id.mAppBarLayout);
        mToolBar = findViewById(R.id.mToolbar);
//        toolBar2 = findViewById(R.id.ll);
        recycler = findViewById(R.id.recycler);
        setSupportActionBar(mToolBar);

        StatusUtil.setStatusView(this, mToolBar);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mToolBar.getLayoutParams();

//        toolBar2.setPadding(0, layoutParams.height + layoutParams.topMargin, 0, 0);
//        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) toolBar2.getLayoutParams();
//        lp.setMargins(layoutParams.leftMargin,
//                layoutParams.height + toolBar2.getHeight(),
//                layoutParams.rightMargin, layoutParams.bottomMargin);
//        toolBar2.setLayoutParams(lp);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setHasFixedSize(true);
        recycler.setAdapter(new MyAdapter());


        // 可改变toolbar的透明度变化
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
                Log.e("tag", "" + offset);
            }
        });


        //下拉Header拉伸
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setBehavior(new AppBarLayoutOverScrollViewBehavior());
        mAppBarLayout.setLayoutParams(params);
    }


    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(WangYiActivity.this).inflate(R.layout.item_layout, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            myViewHolder.textView.setText("item" + i);
        }

        @Override
        public int getItemCount() {
            return 50;
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }
}
