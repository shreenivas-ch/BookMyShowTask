package com.pro.app.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import com.pro.app.data.Resource
import com.pro.app.data.responses.*
import com.pro.app.ui.base.BaseViewModel

class MainViewModel() : BaseViewModel() {

    var nowPlayingLiveData: MutableLiveData<Resource<NowPlayingResponse>> = MutableLiveData()
    var synopsisLiveData: MutableLiveData<Resource<MovieDetailsResponse>> = MutableLiveData()
    var creditsLiveData: MutableLiveData<Resource<MovieCreditsResponse>> = MutableLiveData()
    var reviewsLiveData: MutableLiveData<Resource<MovieReviewsResponse>> = MutableLiveData()
    var similarMoviesLiveData: MutableLiveData<Resource<SimilarMoviesResponse>> = MutableLiveData()
    var videoMoviesLiveData: MutableLiveData<Resource<MovieVideosResponse>> = MutableLiveData()

    fun getNowPlaying(): MutableLiveData<Resource<NowPlayingResponse>> {
        appInteractor.getNowPlaying(nowPlayingLiveData)
        return nowPlayingLiveData
    }

    fun getSynopsis(movie_id: String): MutableLiveData<Resource<MovieDetailsResponse>> {
        appInteractor.getMovieDetails(movie_id, synopsisLiveData)
        return synopsisLiveData
    }

    fun getCredits(movie_id: String): MutableLiveData<Resource<MovieCreditsResponse>> {
        appInteractor.getCredits(movie_id, creditsLiveData)
        return creditsLiveData
    }

    fun getReviews(movie_id: String): MutableLiveData<Resource<MovieReviewsResponse>> {
        appInteractor.getReviews(movie_id, reviewsLiveData)
        return reviewsLiveData
    }

    fun getSimilarMovies(
        movie_id: String
    ): MutableLiveData<Resource<SimilarMoviesResponse>> {
        appInteractor.getSimilarMovies(movie_id, similarMoviesLiveData)
        return similarMoviesLiveData
    }

    fun getMovieVideos(
        movie_id: String
    ): MutableLiveData<Resource<MovieVideosResponse>> {
        appInteractor.getMovieVideos(movie_id, videoMoviesLiveData)
        return videoMoviesLiveData
    }
}