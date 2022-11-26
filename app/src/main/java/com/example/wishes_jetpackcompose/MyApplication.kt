package com.example.wishes_jetpackcompose

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver

import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()

    }

}