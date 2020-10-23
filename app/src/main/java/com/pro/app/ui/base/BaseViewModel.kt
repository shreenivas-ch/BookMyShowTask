package com.pro.app.ui.base

import androidx.lifecycle.ViewModel
import com.pro.app.data.network.AppInteractor

open class BaseViewModel() : ViewModel() {
    var appInteractor: AppInteractor=AppInteractor()
}