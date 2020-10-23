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
import com.pro.app.data.models.ModelCredit
import com.pro.app.data.models.ModelReview
import com.pro.app.data.models.OnClick
import com.pro.app.utils.Constants
import java.util.*

class ReviewsAdapter(
    internal var list: ArrayList<ModelReview>
) : RecyclerView.Adapter<ReviewsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return MyViewHolder(itemView)
    }

    private fun bindViews(holder: MyViewHolder, position: Int) {
        val data = list[position]

        holder.txtName.text = data.author
        holder.txtReview.text = data.content
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        bindViews(holder, position)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rlMain = itemView.findViewById<View>(R.id.rlMain) as RelativeLayout
        val txtName = itemView.findViewById(R.id.txtName) as TextView
        val txtReview = itemView.findViewById(R.id.txtReview) as TextView
    }

}