package com.pro.app.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelGenre(
        var id: String = "",
        var name: String = ""
):Parcelable