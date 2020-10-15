package com.pro.app.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import com.appizona.yehiahd.fastsave.FastSave
import com.mindorks.nybus.NYBus
import com.pro.app.BuildConfig
import com.pro.app.MainApplication.Companion.instance
import com.pro.app.data.events.NoInternetEvent
import com.pro.app.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

abstract class NetworkConnectionInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (!instance.isInternetAvailable()) {
                throw NoConnectivityException()
            }
        } else {
            val connectivityManager =
                instance.applicationContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(
                builder.build(),
                object : NetworkCallback() {
                    override fun onAvailable(network: Network) {}
                    override fun onLost(network: Network) {
                        NYBus.get().post(NoInternetEvent())
                    }
                }
            )
        }
        val original = chain.request()
        val android_id = instance.deviceID
        var token =
            FastSave.getInstance().getString(Constants.SP_USER_TOKEN, "")
        token = token ?: ""
        val request = original.newBuilder()
            .header("token", token)
            .header("os", "android")
            .header("duid", android_id)
            .header("version", "1")
            .header("app_version", BuildConfig.VERSION_NAME)
            .header("app_version_code", BuildConfig.VERSION_CODE.toString())
            .method(original.method(), original.body())
            .build()
        return chain.proceed(request)
    }

    internal inner class NoConnectivityException : IOException() {
        override val message: String
            get() = "Network Connection exception"
    }
}