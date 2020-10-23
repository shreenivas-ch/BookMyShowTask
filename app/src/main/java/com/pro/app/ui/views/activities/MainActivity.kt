package com.pro.app.ui.views.activities

import android.content.Intent
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.pro.app.R
import com.pro.app.data.Status
import com.pro.app.data.models.ModelNowPlaying
import com.pro.app.data.models.ModelSearch
import com.pro.app.data.models.OnClick
import com.pro.app.extensions.showLog
import com.pro.app.extensions.showMessage
import com.pro.app.ui.adapters.MoviesAdapter
import com.pro.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    lateinit var listSearch: ArrayList<ModelSearch>

    lateinit var list: ArrayList<ModelNowPlaying>
    lateinit var listTmp: ArrayList<ModelNowPlaying>
    lateinit var adapter: MoviesAdapter

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initialization() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        }

        list = ArrayList()
        listTmp = ArrayList()
        listSearch = ArrayList()

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
            "data posted".showLog()
            when (it?.status) {
                Status.SUCCESS -> {
                    hideLoading()
                    list.clear()
                    listTmp.clear()
                    list.addAll(it.data?.results!!)
                    listTmp.addAll(it.data?.results!!)
                    adapter.notifyDataSetChanged()


                    listSearch.clear()
                    for (i in list.indices) {
                        var arr = list[i].title.split(" ")
                        for (word in arr) {
                            listSearch.add(ModelSearch(word.toLowerCase().trim(), i))
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

        mainViewModel.getNowPlaying()

    }

    override fun registerListeners() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(str: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var searchString = str.toString().toLowerCase().trim()
                if (searchString.isEmpty()) {
                    list.clear()
                    list.addAll(listTmp)
                } else {
                    list.clear()
                    var arrSearchWords = searchString.split(" ")

                    for (i in listTmp.indices) {
                        var arrTitleWords = listTmp[i].title.toLowerCase().split(" ")
                        var flag = true
                        for (a in arrSearchWords.indices) {

                            for (b in arrTitleWords.indices) {

                                if (a == arrSearchWords.size - 1) {
                                    flag = arrTitleWords[b].startsWith(arrSearchWords[a])
                                } else {
                                    if (arrTitleWords[b] == arrSearchWords[a]) {

                                    } else {
                                        flag = false
                                    }
                                }
                            }
                        }
                        if (flag) {
                            if (!list.contains(listTmp[i])) {
                                list.add(listTmp[i])
                            }
                        }
                    }
                }
                adapter.notifyDataSetChanged()
            }

        })
    }
}