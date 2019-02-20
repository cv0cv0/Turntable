package me.gr.turntable.app

import android.app.Application

class App : Application() {

    companion object {
        lateinit var context: App
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}