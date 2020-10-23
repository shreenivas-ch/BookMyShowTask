package com.pro.app.data.responses

import android.os.Parcelable
import com.pro.app.data.models.ModelReview
import kotlinx.android.parcel.Parcelize

@Parcelize
class MovieReviewsResponse(
    val results: ArrayList<ModelReview> = ArrayList()
) : Parcelable