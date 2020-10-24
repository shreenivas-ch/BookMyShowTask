package com.pro.app.ui.views.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.pro.app.R
import com.pro.app.data.Status
import com.pro.app.data.models.ModelCredit
import com.pro.app.data.models.ModelNowPlaying
import com.pro.app.data.models.ModelReview
import com.pro.app.data.models.OnClick
import com.pro.app.extensions.showLog
import com.pro.app.extensions.showMessage
import com.pro.app.extensions.toast
import com.pro.app.ui.adapters.CastAdapter
import com.pro.app.ui.adapters.MoviesAdapter
import com.pro.app.ui.adapters.ReviewsAdapter
import com.pro.app.ui.base.BaseActivity
import com.pro.app.utils.Constants
import kotlinx.android.synthetic.main.activity_movie_details.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MovieDetailsActivity : BaseActivity() {

    var imdb_id = ""
    var trailer = ""

    lateinit var modelNowPlaying: ModelNowPlaying
    var sheetBehavior: BottomSheetBehavior<*>? = null

    lateinit var castAdapter: CastAdapter
    lateinit var listCast: ArrayList<ModelCredit>

    lateinit var listSimilarMovie: ArrayList<ModelNowPlaying>
    lateinit var similarMoviesAdapter: MoviesAdapter

    lateinit var listReviews: ArrayList<ModelReview>
    lateinit var reviewsAdapter: ReviewsAdapter

    override val layoutId: Int
        get() = R.layout.activity_movie_details

    override fun initialization() {

        listCast = ArrayList()
        listSimilarMovie = ArrayList()
        listReviews = ArrayList()

        mRecyclerViewCast.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        castAdapter = CastAdapter(this, listCast, object : OnClick {
            override fun onCreditClicked(modelCredit: ModelCredit) {

            }
        })
        mRecyclerViewCast.setHasFixedSize(true)
        mRecyclerViewCast.adapter = castAdapter

        mRecyclerViewSimilarMovies.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        similarMoviesAdapter = MoviesAdapter(this, listSimilarMovie, object : OnClick {
            override fun onMovieClicked(modelNowPlaying: ModelNowPlaying) {
                var intent = Intent(this@MovieDetailsActivity, MovieDetailsActivity::class.java)
                intent.putExtra("movie", modelNowPlaying)
                startActivity(intent)
            }
        }, "details")
        mRecyclerViewSimilarMovies.setHasFixedSize(true)
        mRecyclerViewSimilarMovies.adapter = similarMoviesAdapter

        mRecyclerViewReviews.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        reviewsAdapter = ReviewsAdapter(listReviews)
        mRecyclerViewReviews.setHasFixedSize(true)
        mRecyclerViewReviews.adapter = reviewsAdapter

        modelNowPlaying = intent.getParcelableExtra<ModelNowPlaying>("movie") as ModelNowPlaying
        if (modelNowPlaying.poster_path != "") {
            Glide.with(this)
                .load(Constants.IMAGE_BASE_URL + modelNowPlaying.poster_path).dontTransform()
                .placeholder(R.drawable.bg_placeholder)
                .into(imgCover)
        }

        txtOverview.text = modelNowPlaying.overview
        txtTitle.text = modelNowPlaying.title
        txtDetails.text = modelNowPlaying.vote_average

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

        sheetBehavior = BottomSheetBehavior.from(rlBottom);
        (sheetBehavior as BottomSheetBehavior<RelativeLayout>?)?.setBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        imgExpand.setImageResource(R.drawable.icon_down)
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        imgExpand.setImageResource(R.drawable.icon_up)
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

                var opa = slideOffset
                if (opa < 0.7)
                    viewLayer.alpha = opa
            }
        })

        mainViewModel.synopsisLiveData.observe(this, Observer {
            "data posted synopsis".showLog()
            when (it?.status) {
                Status.SUCCESS -> {
                    hideLoading()
                    txtDetails.text = txtDetails.text.toString() + " | ${it.data?.runtime}Min"
                    imdb_id = it.data?.imdb_id!!
                }
                Status.LOADING -> {
                    showLoading()
                }
                Status.ERROR -> {
                    hideLoading()
                    it.message?.let { showMessage(it) }
                }
            }
        })

        mainViewModel.creditsLiveData.observe(this, Observer {
            "data posted credits".showLog()
            when (it?.status) {
                Status.SUCCESS -> {
                    hideLoading()

                    listCast.clear()
                    listCast.addAll(it.data?.cast!!)
                    castAdapter.notifyDataSetChanged()
                }
                Status.LOADING -> {
                    showLoading()
                }
                Status.ERROR -> {
                    hideLoading()
                    it.message?.let { showMessage(it) }
                }
            }
        })

        mainViewModel.videoMoviesLiveData.observe(this, Observer {
            "data posted videos".showLog()
            when (it?.status) {
                Status.SUCCESS -> {
                    hideLoading()
                    if (it.data?.results?.size!! > 0) {
                        if (it.data?.results[0].type.toLowerCase() == "trailer" && it.data?.results[0].site.toLowerCase() == "youtube") {
                            rlTrailer.visibility = View.VISIBLE
                            trailer = it.data?.results[0].key
                        }
                    }
                }
                Status.LOADING -> {
                    showLoading()
                }
                Status.ERROR -> {
                    hideLoading()
                    it.message?.let { showMessage(it) }
                }
            }
        })

        mainViewModel.similarMoviesLiveData.observe(this, Observer {
            "data posted credits".showLog()
            when (it?.status) {
                Status.SUCCESS -> {
                    hideLoading()

                    listSimilarMovie.clear()
                    listSimilarMovie.addAll(it.data?.results!!)
                    similarMoviesAdapter.notifyDataSetChanged()

                    if(listSimilarMovie.size==0)
                    {
                        txtSimilarMoviesTitle.visibility=View.GONE
                    }

                }
                Status.LOADING -> {
                    showLoading()
                }
                Status.ERROR -> {
                    hideLoading()
                    it.message?.let { showMessage(it) }
                }
            }
        })

        mainViewModel.reviewsLiveData.observe(this, Observer {
            "data posted reviews".showLog()
            when (it?.status) {
                Status.SUCCESS -> {
                    hideLoading()

                    listReviews.clear()
                    listReviews.addAll(it.data?.results!!)
                    reviewsAdapter.notifyDataSetChanged()

                    if(listReviews.size==0)
                    {
                        txtReviewsTitle.visibility=View.GONE
                    }
                }
                Status.LOADING -> {
                    showLoading()
                }
                Status.ERROR -> {
                    hideLoading()
                    it.message?.let { showMessage(it) }
                }
            }
        })

        mainViewModel.getSynopsis(modelNowPlaying.id)
        mainViewModel.getCredits(modelNowPlaying.id)
        mainViewModel.getMovieVideos(modelNowPlaying.id)
        mainViewModel.getSimilarMovies(modelNowPlaying.id)
        mainViewModel.getReviews(modelNowPlaying.id)
    }

    override fun registerListeners() {
        txtIMDb.setOnClickListener {
            openIMDb()
        }

        rlTrailer.setOnClickListener {
            openTrailer()
        }

        imgBack.setOnClickListener {
            finish()
        }
    }

    private fun openTrailer() {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(Constants.TrailerYoutubeUrl + trailer)
                )
            )
        } catch (ex: ActivityNotFoundException) {
            toast("Activity Not Found")
        }
    }

    private fun openIMDb() {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.IMDbUrl + "" + imdb_id)))
        } catch (ex: ActivityNotFoundException) {
            toast("Activity Not Found")
        }
    }
}