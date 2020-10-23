package com.pro.app.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelCredit(
        var cast_id: String = "",
        var character: String = "",
        var name: String = "",
        var profile_path: String = ""
):Parcelable