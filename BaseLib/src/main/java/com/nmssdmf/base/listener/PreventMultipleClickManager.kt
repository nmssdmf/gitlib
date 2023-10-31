package com.nmssdmf.base.listener

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import com.nmssdmf.base.Singleton

/**
 * 防多次点击事件管理
 */
class PreventMultipleClickManager {
    companion object {
        private var instance: Singleton<PreventMultipleClickManager> = object:Singleton<PreventMultipleClickManager>(){
            override fun create(): PreventMultipleClickManager {
                return PreventMultipleClickManager()
            }
        }

         /**
          * key: View Id
          * Long: 上次点击的时间
          */
        @JvmStatic
        var clickMap:HashMap<Int, Long> = HashMap()

        @JvmStatic
        fun getInstance():PreventMultipleClickManager{
            return instance.get()
        }

        @JvmStatic
        var clickHandler:Handler = Handler(Looper.getMainLooper()) {
            //view id : it.arg1
             if (getInstance().hasItem(it.arg1)) {
                 clickMap.remove(it.arg1)
             }
            true
        }
     }

    fun removeItem(viewId:Int, time:Long){
        if (hasItem(viewId)) {
            var msg = Message.obtain()
            msg.arg1 = viewId
            clickHandler.sendMessageDelayed(msg, time)
        }
    }

     fun addItem(viewId:Int, time: Long) {
         clickMap[viewId] = time
     }
    fun hasItem(viewId:Int):Boolean {
        return clickMap.contains(viewId)
    }


}