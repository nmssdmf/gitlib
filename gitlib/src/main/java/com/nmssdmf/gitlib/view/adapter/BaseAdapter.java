package com.nmssdmf.gitlib.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.List;

/**
 * @author mahuafeng
 * 仅适用于单一布局的adapter继承
 * @date 2022/9/20
 */
public abstract class BaseAdapter<T extends Object,  VH extends BaseAdapter.BaseVH> extends RecyclerView.Adapter<VH>{
    protected OnAdapterItemClickListener listener;
    protected Context context;
    protected List<T> list;

    public BaseAdapter(Context context, List<T> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, @SuppressLint("RecyclerView") final int position) {
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClickListener(v, list.get(position), position);
                }
            }
        });
    }

    public void setListener(OnAdapterItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnAdapterItemClickListener<T> {
        void onItemClickListener(View view, T obj, int position);
    }

    public static class BaseVH<V extends ViewBinding> extends RecyclerView.ViewHolder{
        public V binding;
        public BaseVH(V binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
