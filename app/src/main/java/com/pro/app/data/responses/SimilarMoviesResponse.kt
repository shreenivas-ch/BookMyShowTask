package com.pro.app.data.responses

import android.os.Parcelable
import com.pro.app.data.models.ModelNowPlaying
import com.pro.app.data.models.ModelSimilar
import kotlinx.android.parcel.Parcelize

@Parcelize
class SimilarMoviesResponse(
    val results: ArrayList<ModelNowPlaying> = ArrayList()
) : Parcelable
