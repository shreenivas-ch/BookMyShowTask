package com.pro.app.data.responses

import android.os.Parcelable
import com.pro.app.data.models.ModelCredit
import kotlinx.android.parcel.Parcelize

@Parcelize
class MovieCreditsResponse(
    val cast: ArrayList<ModelCredit> = ArrayList()
) : Parcelable
