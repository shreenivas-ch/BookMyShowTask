package com.pro.app.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelReview(
        var author: String = "",
        var content: String = ""
):Parcelable