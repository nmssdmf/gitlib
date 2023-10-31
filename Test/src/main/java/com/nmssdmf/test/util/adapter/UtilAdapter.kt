package com.nmssdmf.test.util.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.nmssdmf.test.R
import com.nmssdmf.test.databinding.UtilItemBinding

class UtilAdapter: BaseQuickAdapter<String, UtilAdapter.VH>() {
    override fun onBindViewHolder(holder: VH, position: Int, item: String?) {
        holder.binding.tvItem.text = item
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(context).inflate(R.layout.util_item, parent, false))
    }

    class VH(view: View) : QuickViewHolder(view) {
        var binding:UtilItemBinding = UtilItemBinding.bind(view)

    }

}