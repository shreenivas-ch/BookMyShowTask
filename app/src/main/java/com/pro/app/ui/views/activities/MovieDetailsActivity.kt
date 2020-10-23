package com.pro.app.ui.views.activities

import android.view.View
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import com.pro.app.R
import com.pro.app.data.models.ModelNowPlaying
import com.pro.app.ui.base.BaseActivity
import com.pro.app.utils.Constants
import kotlinx.android.synthetic.main.activity_movie_details.*
import java.text.SimpleDateFormat
import java.util.*

class MovieDetailsActivity : BaseActivity() {

    lateinit var modelNowPlaying: ModelNowPlaying

    override val layoutId: Int
        get() = R.layout.activity_movie_details

    override fun initialization() {

        modelNowPlaying = intent.getParcelableExtra<ModelNowPlaying>("movie") as ModelNowPlaying
        if (modelNowPlaying.poster_path != "") {
            Glide.with(this)
                .load(Constants.IMAGE_BASE_URL + modelNowPlaying.poster_path).dontTransform()
                .placeholder(R.drawable.bg_placeholder)
                .into(imgCover)
        }

        txtOverview.text = modelNowPlaying.overview
        txtTitle.text = modelNowPlaying.title
        txtDetails.text = modelNowPlaying.vote_average + " | " + "0 Min"

        val simpleDateFormat = SimpleDateFormat(Constants.DATEFORMAT_1)
        simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val myDate = simpleDateFormat.parse(modelNowPlaying.release_date)
        val c = Calendar.getInstance()
        c.time = myDate

        val formatter = SimpleDateFormat("dd MMM,yyyy")
        txtNextEpisode?.text = "Release Date " + formatter.format(c.time)
        txtNextEpisode?.visibility = View.VISIBLE
        txtNextEpisode?.animation =
            AnimationUtils.loadAnimation(this, R.anim.slide_right_to_left)

    }
}