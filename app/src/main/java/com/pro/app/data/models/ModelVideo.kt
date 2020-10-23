package com.pro.app.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelVideo(
        var key: String = "",
        var type: String = "",
        var site: String = ""
):Parcelable