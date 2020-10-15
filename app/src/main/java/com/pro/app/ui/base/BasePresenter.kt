package com.pro.app.ui.base

open class BasePresenter<V : BaseMvpView> : MvpPresenter<V> {

    lateinit var mvpView: V
        private set

    override fun onAttach(mvpView: V) {

        this.mvpView = mvpView
    }

    override fun onDetach() {

    }

}
