package com.pro.app.data.network

import androidx.lifecycle.MutableLiveData
import com.pro.app.BuildConfig
import com.pro.app.data.Resource
import com.pro.app.data.responses.*
import retrofit2.Response

open class AppInteractor {

    private val apiService: APIService = AppClient.getClient().create(APIService::class.java)
    private val api_key = BuildConfig.API_KEY

    fun getNowPlaying(
        viewModel: MutableLiveData<Resource<NowPlayingResponse>>
    ) {

        viewModel.postValue(Resource.loading(null))
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
        movie_id:String,
        viewModel: MutableLiveData<Resource<MovieDetailsResponse>>
    ) {

        val call = apiService.getSynopsis(movie_id,api_key)
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

    fun getCredits(
        movie_id:String,
        viewModel: MutableLiveData<Resource<MovieCreditsResponse>>
    ) {

        val call = apiService.getCredits(movie_id,api_key)
        call.enqueue(object : SuccessCallback<MovieCreditsResponse>() {

            override fun onSuccess(response: Response<MovieCreditsResponse>) {
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

    fun getReviews(
        movie_id:String,
        viewModel: MutableLiveData<Resource<MovieReviewsResponse>>
    ) {

        val call = apiService.getReviews(movie_id,api_key)
        call.enqueue(object : SuccessCallback<MovieReviewsResponse>() {

            override fun onSuccess(response: Response<MovieReviewsResponse>) {
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

    fun getSimilarMovies(
        movie_id:String,
        viewModel: MutableLiveData<Resource<SimilarMoviesResponse>>
    ) {

        val call = apiService.getSimilarMovies(movie_id,api_key)
        call.enqueue(object : SuccessCallback<SimilarMoviesResponse>() {

            override fun onSuccess(response: Response<SimilarMoviesResponse>) {
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


