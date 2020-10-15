package com.pro.app.ui.base

interface BaseMvpView {

    fun showLoading()

    fun hideLoading()

    fun onError(msg: String)

}
