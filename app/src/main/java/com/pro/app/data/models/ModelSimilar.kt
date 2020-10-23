package com.pro.app.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelSimilar(
        var id: String = "",
        var vote_average: String = "",
        var title: String = "",
        var release_date: String = "",
        var poster_path: String = ""
):Parcelable