package com.pro.app.data.network

import androidx.lifecycle.MutableLiveData
import com.pro.app.BuildConfig
import com.pro.app.data.Resource
import com.pro.app.data.responses.MovieDetailsResponse
import com.pro.app.data.responses.NowPlayingResponse
import retrofit2.Response

open class AppInteractor {

    private val apiService: APIService = AppClient.getClient().create(APIService::class.java)
    private val api_key = BuildConfig.API_KEY

    fun getNowPlaying(
        viewModel: MutableLiveData<Resource<NowPlayingResponse>>
    ) {

        val call = apiService.getNowPlaynig(api_key)
        call.enqueue(object : SuccessCallback<NowPlayingResponse>() {

            override fun onSuccess(response: Response<NowPlayingResponse>) {
                try {
                    viewModel.postValue(Resource.success(response.body()))
                } catch (ex: Exception) {
                    viewModel.postValue(Resource.error(ex.toString(), null))
                }
            }
            override fun onFailure(message: String) {
                viewModel.postValue(Resource.error(message, null))
            }
        })
    }

    fun getMovieDetails(
        viewModel: MutableLiveData<Resource<MovieDetailsResponse>>
    ) {

        val call = apiService.getSynopsis(api_key)
        call.enqueue(object : SuccessCallback<MovieDetailsResponse>() {

            override fun onSuccess(response: Response<MovieDetailsResponse>) {
                try {
                    viewModel.postValue(Resource.success(response.body()))
                } catch (ex: Exception) {
                    viewModel.postValue(Resource.error(ex.toString(), null))
                }
            }
            override fun onFailure(message: String) {
                viewModel.postValue(Resource.error(message, null))
            }
        })
    }
}


