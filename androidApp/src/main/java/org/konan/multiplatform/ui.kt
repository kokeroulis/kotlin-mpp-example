package org.konan.multiplatform

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.app.Application
import android.widget.LinearLayout
import android.widget.TextView
import org.greeting.Factory
import kotlin.properties.Delegates

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        println("Application init")
    }
}

