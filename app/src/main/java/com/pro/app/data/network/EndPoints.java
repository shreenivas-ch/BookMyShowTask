package com.pro.app.data.network;

import com.pro.app.BuildConfig;

public class EndPoints {

    public static final String BASE_URL = BuildConfig.BASE_URL;

    public static final String GET_NOW_PLAYING = "movie/now_playing";
    public static final String GET_SYNOPSIS = "movie/{movie_id}";
    public static final String GET_REVIEWS = "movie/{movie_id}/reviews";
    public static final String GET_CREDITS = "movie/{movie_id}/credits";
    public static final String GET_SIMILAR_MOVIES = "movie/{movie_id}/similar";
    public static final String GET_VIDEOS = "movie/{movie_id}/videos";

}
