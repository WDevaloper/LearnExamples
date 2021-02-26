package com.gavin.asmdemo.cart;

import android.content.Context;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;

import com.gavin.asmdemo.cart.data.ICartItem;
import com.gavin.asmdemo.cart.holder.CartViewHolder;
import com.gavin.asmdemo.cart.holder.ChildViewHolder;
import com.gavin.asmdemo.cart.holder.GroupViewHolder;

import java.util.List;

public class CartAdapter extends AbsCartAdapter<CartViewHolder> {

    public CartAdapter(Context context, List<ICartItem> dataSets) {
        super(context, dataSets);
    }

    @Override
    public CartViewHolder getNormalViewHolder(View itemView) {
        return null;
    }

    @Override
    public CartViewHolder getGroupViewHolder(View itemView) {
        return new GroupViewHolder(itemView, -1);
    }

    @Override
    public CartViewHolder getChildViewHolder(View itemView) {
        return new ChildViewHolder(itemView, -1);
    }

    @Override
    public void registerItemLayoutId(SparseIntArray itemLayoutIdContainer) {
    }
}
