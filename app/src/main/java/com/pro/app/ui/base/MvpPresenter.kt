package com.pro.app.ui.base

interface MvpPresenter<V : BaseMvpView> {

    fun onAttach(mvpView: V)

    fun onDetach()

}
