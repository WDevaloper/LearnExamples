package com.gavin.asmdemo.cart.holder;

import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gavin.asmdemo.cart.callback.OnCheckChangeListener;
import com.gavin.asmdemo.cart.data.ICartItem;

import java.util.List;

import static com.gavin.asmdemo.cart.AbsCartAdapter.PAYLOAD_CHECKBOX;


public abstract class CartViewHolder extends RecyclerView.ViewHolder {
    //checkbox回调
    public OnCheckChangeListener onCheckChangeListener;
    public CheckBox checkBox;

    public CartViewHolder(@NonNull View itemView, @IdRes int checkboxId) {
        super(itemView);
        if (checkboxId != -1) {
            checkBox = itemView.findViewById(checkboxId);
        }
    }

    @CallSuper
    public void bindData(ICartItem cartItem, int position) {
        if (checkBox != null) {
            if (checkBox.isChecked() != cartItem.isChecked()) {
                checkBox.setChecked(cartItem.isChecked());
                checkBox.setOnClickListener(v -> {
                    if (onCheckChangeListener != null) {
                        onCheckChangeListener.onCheckedChanged(
                                position, ((CheckBox) v).isChecked(), cartItem.getItemType());
                    }
                });
            }
        }
    }

    @CallSuper
    public void bindData(ICartItem cartItem, int position, @NonNull List<Object> payloads) {
        if (checkBox != null &&
                payloads.contains(PAYLOAD_CHECKBOX) &&
                (checkBox.isChecked() != cartItem.isChecked())) {
            checkBox.setChecked(cartItem.isChecked());
        }
    }
}
