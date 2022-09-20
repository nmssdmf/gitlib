package com.nmssdmf.gitlib.view.util

import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance

/**
 * @author mahuafeng
 * @date 2022/9/14
 */
abstract class SingletonUtil<T> {
    @Volatile
    private var instance: T = create()
    protected abstract fun create(): T
    fun get(): T {
        synchronized(this) {
            if (instance == null) {
                instance = create()
            }
            return instance
        }
    }
}