package com.pro.app.data.network

import com.pro.app.data.requests.LoginRequest
import com.pro.app.data.responses.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface APIService {

    @POST(EndPoints.LOGIN)
    fun login(@Body body: LoginRequest): Call<LoginResponse>

}
