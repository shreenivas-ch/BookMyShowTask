package com.pro.app

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.provider.Settings.Secure.ANDROID_ID
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.appizona.yehiahd.fastsave.FastSave

/**
 * Created by codename07 on 08/05/18.
 */


class MainApplication : MultiDexApplication() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    val deviceID: String
        get() = Settings.Secure.getString(contentResolver, ANDROID_ID)

    override fun onCreate() {
        super.onCreate()

        instance = this
        FastSave.init(applicationContext)
    }

    companion object {

        lateinit var instance: MainApplication
            private set
    }


    fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }
}
