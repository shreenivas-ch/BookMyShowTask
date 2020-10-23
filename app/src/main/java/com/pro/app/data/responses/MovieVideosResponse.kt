package com.pro.app.data.responses

import android.os.Parcelable
import com.pro.app.data.models.ModelCredit
import com.pro.app.data.models.ModelVideo
import kotlinx.android.parcel.Parcelize

@Parcelize
class MovieVideosResponse(
    val results: ArrayList<ModelVideo> = ArrayList()
) : Parcelable
