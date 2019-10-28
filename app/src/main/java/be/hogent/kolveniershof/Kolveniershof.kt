package be.hogent.kolveniershof

import android.app.Application
import net.danlew.android.joda.JodaTimeAndroid

class Kolveniershof : Application() {
    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
    }
}