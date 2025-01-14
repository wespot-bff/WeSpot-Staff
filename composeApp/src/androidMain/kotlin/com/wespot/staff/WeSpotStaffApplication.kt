package com.wespot.staff

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.initialize
import com.wespot.staff.di.initKoin
import org.koin.android.ext.koin.androidContext

class WeSpotStaffApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin { androidContext(this@WeSpotStaffApplication) }
        initFirebase()
    }

    private fun initFirebase() {
        Firebase.initialize(this)
    }
}
