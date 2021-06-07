package com.example.githubuserlist

import android.app.Application
import android.util.Log
import com.example.githubuserlist.dagger.ApplicationComponent
import com.example.githubuserlist.dagger.DaggerApplicationComponent
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

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(applicationContext)
    }
}
