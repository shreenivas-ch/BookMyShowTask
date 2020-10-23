package com.pro.app.ui.views.activities

import android.content.Intent
import android.os.Build
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.pro.app.R
import com.pro.app.data.Status
import com.pro.app.data.models.ModelNowPlaying
import com.pro.app.data.models.OnClick
import com.pro.app.extensions.showLog
import com.pro.app.extensions.showMessage
import com.pro.app.ui.adapters.MoviesAdapter
import com.pro.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    lateinit var list: ArrayList<ModelNowPlaying>
    lateinit var adapter: MoviesAdapter

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initialization() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        }

        list = ArrayList()

        rvMovies.layoutManager = GridLayoutManager(this, 3)
        rvMovies.setHasFixedSize(true)
        adapter = MoviesAdapter(this, list, object : OnClick {
            override fun onMovieClicked(modelNowPlaying: ModelNowPlaying) {
                var intent = Intent(this@MainActivity, MovieDetailsActivity::class.java)
                intent.putExtra("movie", modelNowPlaying)
                startActivity(intent)
            }
        })

        rvMovies.adapter = adapter

        mainViewModel.nowPlayingLiveData.observe(this, Observer {
            "LoginActivity: data posted".showLog()
            when (it?.status) {
                Status.SUCCESS -> {
                    hideLoading()
                    list.clear()
                    list.addAll(it.data?.results!!)
                    adapter.notifyDataSetChanged()
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

        mainViewModel.getNowPlaying()

    }
}