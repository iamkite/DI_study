package com.example.githubuserlist

import android.app.Application
import android.util.Log
import io.reactivex.plugins.RxJavaPlugins

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initRxJava()
    }

    private fun initRxJava() {
        RxJavaPlugins.setErrorHandler {
            Log.w("RxJavaPlugin", "error")
            return@setErrorHandler
        }
    }
}
