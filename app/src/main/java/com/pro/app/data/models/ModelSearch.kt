package com.pro.app.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelSearch(
    var searchword: String = "",
    var itemindex: Int = 0
) : Parcelable