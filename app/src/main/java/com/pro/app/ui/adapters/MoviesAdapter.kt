package com.pro.app.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pro.app.R
import com.pro.app.data.models.ModelNowPlaying
import com.pro.app.data.models.OnClick
import com.pro.app.utils.AppUtilsKotlin
import com.pro.app.utils.Constants.IMAGE_BASE_URL
import java.util.*

class MoviesAdapter(
    private var context: Context,
    private var list: ArrayList<ModelNowPlaying>,
    private var onClick: OnClick,
    private var screen: String = "main"
) : RecyclerView.Adapter<MoviesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MyViewHolder {

        var layout = if (screen == "main") R.layout.item_movie else R.layout.item_similarmovie
        val itemView =
            LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return MyViewHolder(itemView)
    }

    private fun bindViews(holder: MyViewHolder, position: Int) {
        val data = list[position]

        holder.txtRating.text = data.vote_average
        holder.txtYear.text = AppUtilsKotlin.getReleaseYear(data.release_date)
        holder.txtTitle.text = data.title

        if (data.poster_path != "") {
            holder.txtTitle.visibility = View.GONE
            Glide.with(context)
                .load(IMAGE_BASE_URL + data.poster_path).dontTransform()
                .placeholder(R.drawable.bg_placeholder)
                .into(holder.imgCover)
        } else {
            holder.txtTitle.visibility = View.VISIBLE
        }

        holder.rlMain.setOnClickListener {
            onClick.onMovieClicked(data)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        bindViews(holder, position)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rlMain = itemView.findViewById<View>(R.id.rlMain) as RelativeLayout
        val imgCover = itemView.findViewById<View>(R.id.imgCover) as ImageView
        val txtRating = itemView.findViewById<View>(R.id.txtRating) as TextView
        val txtYear = itemView.findViewById<View>(R.id.txtYear) as TextView
        val txtTitle = itemView.findViewById<View>(R.id.txtTitle) as TextView
    }

}