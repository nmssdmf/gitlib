package com.nmssdmf.base

/**
 * 单例模式基类
 * 必须继承
 */
abstract class Singleton<T> {
    @Volatile
    private var instance: T? = null
    protected abstract fun create(): T
    fun get(): T {
        synchronized(this) {
            if (instance == null) {
                instance = create()
            }
            return instance!!
        }
    }
}