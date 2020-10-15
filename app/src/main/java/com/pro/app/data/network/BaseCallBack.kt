package com.pro.app.data.network

import retrofit2.Response


abstract class BaseCallBack<T> {

    abstract fun onSuccess(response: Response<T>)
    abstract fun onFailure(message:String)
    abstract fun onNoInternetConnection(message:String)
}
