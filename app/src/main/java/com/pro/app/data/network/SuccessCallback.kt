package com.pro.app.data.network

import com.mindorks.nybus.NYBus
import com.pro.app.data.events.AuthFailEvent
import com.pro.app.extensions.showLog
import com.pro.app.utils.Constants.INTERNAL_SERVER_ERROR
import com.pro.app.utils.Constants.NO_INTERNET_CONNECTION
import com.pro.app.utils.Constants.SOMETHING_WENT_WRONG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.UnknownHostException

abstract class SuccessCallback<T> : BaseCallBack<T>(), Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {

        when {
            response.code() == 200 -> try {
                onSuccess(response)
            } catch (ex: Exception) {
                ex.toString().showLog()
            }
            response.code() == 401 -> NYBus.get().post(AuthFailEvent())
            response.code() == 502 -> onFailure(INTERNAL_SERVER_ERROR)
            else -> onFailure(SOMETHING_WENT_WRONG)
        }

    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        if (t is NetworkConnectionInterceptor.NoConnectivityException) {
            onFailure(NO_INTERNET_CONNECTION)
        } else if (t is UnknownHostException) {
            onFailure(NO_INTERNET_CONNECTION)
        } else {
            onFailure("$SOMETHING_WENT_WRONG: $t")
        }
    }

    override fun onSuccess(response: Response<T>) {

    }

    override fun onFailure(message: String) {

    }

    override fun onNoInternetConnection(message: String) {

    }
}