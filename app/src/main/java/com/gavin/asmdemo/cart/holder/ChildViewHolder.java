package com.gavin.asmdemo.cart.holder;

import android.view.View;

import androidx.annotation.NonNull;

import com.gavin.asmdemo.cart.data.ICartItem;

import java.util.List;

public class ChildViewHolder extends CartViewHolder {

    public ChildViewHolder(@NonNull View itemView, int checkboxId) {
        super(itemView, checkboxId);
    }


    @Override
    public void bindData(ICartItem cartItem, int position) {
        super.bindData(cartItem, position);
    }

    @Override
    public void bindData(ICartItem cartItem, int position, @NonNull List<Object> payloads) {
        super.bindData(cartItem, position, payloads);
    }
}
