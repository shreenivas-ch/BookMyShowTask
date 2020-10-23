package com.pro.app.data.responses

import android.os.Parcelable
import com.pro.app.data.models.ModelNowPlaying
import kotlinx.android.parcel.Parcelize

@Parcelize
class NowPlayingResponse(
    val results: ArrayList<ModelNowPlaying> = ArrayList()
) : Parcelable
