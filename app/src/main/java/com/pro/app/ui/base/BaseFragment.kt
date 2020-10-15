package com.pro.app.ui.base

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.mindorks.nybus.NYBus
import com.pro.app.R

abstract class BaseFragment : Fragment(), BaseMvpView {

    lateinit var root: View;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        root = inflater?.inflate(getLayoutID(), container, false)

        return root
    }

    protected abstract fun initialization()

    protected abstract fun getLayoutID(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialization()
        registerListeners()
    }

    open fun registerListeners() {

    }

    fun requestSpecifiedPermissions(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }


    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onError(msg: String) {

    }

    override fun onStart() {
        super.onStart()
        NYBus.get().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        NYBus.get().unregister(this)
    }
}


