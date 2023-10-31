package com.nmssdmf.base.manager.toast

import android.text.TextUtils
import android.widget.Toast
import com.nmssdmf.base.BaseApp
import java.lang.Exception

class ToastUtil {
    companion object {
        @JvmStatic
        private var toast:Toast? = null

        @JvmStatic
        fun showToast(charSequence: CharSequence){
            getToast(charSequence, Toast.LENGTH_SHORT).show()
        }

        @JvmStatic
        fun showToastLong(charSequence: CharSequence){
            getToast(charSequence, Toast.LENGTH_LONG).show()
        }

        @JvmStatic
        private fun getToast(charSequence: CharSequence, duration: Int):Toast{
            if (TextUtils.isEmpty(charSequence)) {
                throw Throwable("toast msg is empty")
            }
            try {
                synchronized(ToastUtil::class.java) {
                    if (toast != null) {
                        toast!!.cancel()
                    }
                    toast = Toast.makeText(BaseApp.getApplication(), "", Toast.LENGTH_SHORT)
                }
            } catch (e:Exception) {
                e.printStackTrace()
            }

            toast!!.setText(charSequence)
            toast!!.duration = duration
            return toast!!
        }
    }

}