package com.pro.app.data.responses

import android.os.Parcelable
import com.pro.app.data.models.ModelGenre
import kotlinx.android.parcel.Parcelize

@Parcelize
class MovieDetailsResponse(
    val genres: ArrayList<ModelGenre> = ArrayList(),
    val id: String = "",
    val imdb_id: String = "",
    val release_date: String = "",
    val runtime: String = "",
    val status: String = ""
) : Parcelable
