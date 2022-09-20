package com.nmssdmf.gitlib.view.manager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.nmssdmf.gitlib.view.util.SingletonUtil;

/**
 * 统一UIHandler获取
 * @author mahuafeng
 * @date 2022/9/14
 */
public class UIHandlerManager {

    private static SingletonUtil<UIHandlerManager> handlerManager = new SingletonUtil<UIHandlerManager>() {
        @Override
        protected UIHandlerManager create() {
            return new UIHandlerManager();
        }
    };

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };


    public static Handler getUiHandler() {
        return handlerManager.get().handler;
    }
}
