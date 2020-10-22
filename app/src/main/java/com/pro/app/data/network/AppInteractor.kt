package com.pro.app.data.network

import androidx.lifecycle.MutableLiveData
import com.pro.app.data.Resource
import com.pro.app.data.requests.LoginRequest
import com.pro.app.data.responses.LoginResponse
import retrofit2.Response

open class AppInteractor {

    private val apiService: APIService = AppClient.getClient().create(APIService::class.java)

    fun login(
        loginRequest: LoginRequest,
        loginViewModel: MutableLiveData<Resource<LoginResponse>>
    ) {

        val call = apiService.login(loginRequest)
        call.enqueue(object : SuccessCallback<LoginResponse>() {

            override fun onSuccess(response: Response<LoginResponse>) {
                try {
                    if (response.body()!!.status == 1) {

                        loginViewModel.postValue(Resource.success(response.body()))

                    } else {
                        loginViewModel.postValue(Resource.error(response.message(), null))
                    }
                } catch (ex: Exception) {
                    loginViewModel.postValue(Resource.error(ex.toString(), null))
                }
            }

            override fun onFailure(message: String) {
                loginViewModel.postValue(Resource.error(message, null))
            }
        })
    }
}


