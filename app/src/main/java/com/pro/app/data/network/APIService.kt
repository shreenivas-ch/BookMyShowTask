package com.pro.app.data.network

import com.pro.app.data.responses.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET(EndPoints.GET_NOW_PLAYING)
    fun getNowPlaynig( @Query("api_key") api_key: String): Call<NowPlayingResponse>

    @GET(EndPoints.GET_SYNOPSIS)
    fun getSynopsis(
        @Path("movie_id") movie_id: String,
        @Query("api_key") api_key: String
    ): Call<MovieDetailsResponse>

    @GET(EndPoints.GET_CREDITS)
    fun getCredits(
        @Path("movie_id") movie_id: String,
        @Query("api_key") api_key: String
    ): Call<MovieCreditsResponse>

    @GET(EndPoints.GET_REVIEWS)
    fun getReviews(
        @Path("movie_id") movie_id: String,
        @Query("api_key") api_key: String
    ): Call<MovieReviewsResponse>

    @GET(EndPoints.GET_SIMILAR_MOVIES)
    fun getSimilarMovies(
        @Path("movie_id") movie_id: String,
        @Query("api_key") api_key: String
    ): Call<SimilarMoviesResponse>

}
