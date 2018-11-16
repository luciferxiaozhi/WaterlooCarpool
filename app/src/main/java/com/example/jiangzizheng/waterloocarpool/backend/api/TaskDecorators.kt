package com.example.jiangzizheng.waterloocarpool.backend.api

import android.util.Log
import com.example.jiangzizheng.waterloocarpool.BuildConfig
import com.google.android.gms.tasks.Task

object TaskDecorators {
    fun<T> Task<T>.withFailureLog(tag: String): Task<T> {
        return this.addOnFailureListener {
            if (BuildConfig.DEBUG) {
                Log.w(tag, it)
            }
        }
    }
}