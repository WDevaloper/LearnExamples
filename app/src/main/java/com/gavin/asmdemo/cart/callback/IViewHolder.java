package com.gavin.asmdemo.cart.callback;

import android.util.SparseIntArray;
import android.view.View;


import com.gavin.asmdemo.cart.holder.CartViewHolder;

public interface IViewHolder<H extends CartViewHolder> {
    H getNormalViewHolder(View itemView);

    H getGroupViewHolder(View itemView);

    H getChildViewHolder(View itemView);

    void registerItemLayoutId(SparseIntArray itemLayoutIdContainer);
}
