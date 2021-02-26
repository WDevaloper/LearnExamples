package com.gavin.asmdemo.cart;

import android.content.Context;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gavin.asmdemo.cart.callback.IViewHolder;
import com.gavin.asmdemo.cart.callback.OnCheckChangeListener;
import com.gavin.asmdemo.cart.data.CartType;
import com.gavin.asmdemo.cart.data.ICartItem;
import com.gavin.asmdemo.cart.holder.CartViewHolder;

import java.util.List;

public abstract class AbsCartAdapter<H extends CartViewHolder> extends RecyclerView.Adapter<H> implements IViewHolder<H> {
    public static final int PAYLOAD_CHECKBOX = 0;

    private final SparseIntArray itemLayoutIdContainer;
    private List<ICartItem> dataSets;
    private Context context;
    private LayoutInflater layoutInflater;

    private RecyclerView recyclerView;
    //checkbox回调
    private OnCheckChangeListener onCheckChangeListener;


    public AbsCartAdapter(Context context, List<ICartItem> dataSets) {
        this.dataSets = dataSets;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        registerItemLayoutId(itemLayoutIdContainer = new SparseIntArray());

        SparseArray<Object> sparseArray = new SparseArray<>();
        sparseArray.put(100,333);
    }

    @NonNull
    @Override
    public H onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        H viewHolder;
        View itemView = layoutInflater.inflate(itemLayoutIdContainer.get(viewType), parent, false);
        switch (viewType) {
            case CartType.TYPE_NORMA:
                viewHolder = getNormalViewHolder(itemView);
                break;
            case CartType.TYPE_CHILD:
                viewHolder = getChildViewHolder(itemView);
                break;
            case CartType.TYPE_GROUP:
                viewHolder = getGroupViewHolder(itemView);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + viewType);
        }
        viewHolder.onCheckChangeListener = onCheckChangeListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull H holder, int position) {
        holder.bindData(dataSets.get(position), position);
    }

    // 局部更新
    @Override
    public void onBindViewHolder(@NonNull H holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            this.onBindViewHolder(holder, position);
            return;
        }
        holder.bindData(dataSets.get(position), position, payloads);
    }

    @Override
    public int getItemCount() {
        return dataSets.size();
    }

    @Override
    public int getItemViewType(int position) {
        return dataSets.get(position).getItemType();
    }

    public void setOnCheckChangeListener(OnCheckChangeListener onCheckChangeListener) {
        this.onCheckChangeListener = onCheckChangeListener;
    }
}
