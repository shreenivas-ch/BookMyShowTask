package com.pro.app.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelNowPlaying(
        var poster_path: String = "",
        var id: String = "",
        var title: String = "",
        var vote_average: String = "",
        var overview: String = "",
        var release_date: String = ""
):Parcelable