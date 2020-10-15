package com.pro.app.data.responses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class LoginResponse(
    val status: Int = 0,
    val msg: String = "",
    val mobile_msg: String = "",
    val data: Userdata = Userdata()
) : Parcelable {
    @Parcelize
    data class Userdata(
        val userid: Int = 0,
        val company_name: String = "",
        val store_name: String = "",
        val email: String = "",
        val mobile: String = "",
        val profile_pic: String = "",
        val token: String = ""
    ) : Parcelable
}
