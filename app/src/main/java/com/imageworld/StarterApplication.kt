package com.imageworld

import android.app.Application
import android.util.Log
import com.parse.*

class StarterApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this)

        // Add your initialization code here
        Parse.initialize(Parse.Configuration.Builder(applicationContext)
                .applicationId("f780d6901e1438a7826cf3d71ffa8ca2f3b38db7")
                .clientKey("fcf8beca9bef6c09fdab482db4db6256c6490a0d")
                .server("http://18.191.61.34:80/parse/")
                .build()
        )

//        ParseUser.enableAutomaticUser()
        val defaultACL = ParseACL()
        defaultACL.publicReadAccess = true
        defaultACL.publicWriteAccess = true
        ParseACL.setDefaultACL(defaultACL, true)

    }
}