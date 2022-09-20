package com.nmssdmf.gittest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.nmssdmf.gitlib.view.adapter.BaseAdapter;
import com.nmssdmf.gittest.databinding.ItemMainListBinding;

import java.util.List;

/**
 * @author mahuafeng
 * @date 2022/9/14
 */
public class MainListAdapter extends BaseAdapter<String, BaseAdapter.BaseVH<ItemMainListBinding>> {


    public MainListAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @NonNull
    @Override
    public BaseAdapter.BaseVH<ItemMainListBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMainListBinding binding = ItemMainListBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new BaseAdapter.BaseVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseAdapter.BaseVH<ItemMainListBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        String text = list.get(position);
        holder.binding.tv.setText(text);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
