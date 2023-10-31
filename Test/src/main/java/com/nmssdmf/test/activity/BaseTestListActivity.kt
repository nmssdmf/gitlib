package com.nmssdmf.test.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nmssdmf.base.BaseActivity
import com.nmssdmf.test.databinding.ActivityListBinding
import com.nmssdmf.test.util.adapter.UtilAdapter

open abstract class BaseTestListActivity: BaseActivity<ActivityListBinding>() {
    var map = HashMap<String, View.OnClickListener>()
    var adapter: UtilAdapter? = null
    override fun initViewBinding(): ActivityListBinding {
        return ActivityListBinding.inflate(layoutInflater)
    }


    override fun initView() {
        adapter = UtilAdapter()
        adapter?.addAll(map.keys)
        binding.rv.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        binding.rv.adapter = adapter
        adapter?.notifyDataSetChanged()

        adapter?.setOnItemClickListener { adapter, view, position ->
            map[adapter.items[position]]?.onClick(view)
        }
    }
}