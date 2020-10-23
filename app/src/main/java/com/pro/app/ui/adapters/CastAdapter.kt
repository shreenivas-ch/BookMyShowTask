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
import com.pro.app.data.models.OnClick
import com.pro.app.utils.Constants
import java.util.*

class CastAdapter(
    internal var context: Context,
    internal var list: ArrayList<ModelCredit>,
    internal var onClick: OnClick
) : RecyclerView.Adapter<CastAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cast, parent, false)
        return MyViewHolder(itemView)
    }

    private fun bindViews(holder: MyViewHolder, position: Int) {
        val data = list[position]

        Glide.with(context)
            .load(Constants.IMAGE_BASE_URL +data.profile_path).dontTransform()
            .placeholder(R.drawable.bg_placeholder)
            .into(holder.imgCover)

        holder.txtCharacter.text = data.character
        holder.txtName.text = data.name

        holder.rlMain.setOnClickListener {
            onClick.onCreditClicked(data)
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
        val imgCover = itemView.findViewById(R.id.imgCover) as ImageView
        val txtName = itemView.findViewById(R.id.txtName) as TextView
        val txtCharacter = itemView.findViewById(R.id.txtCharacter) as TextView
    }

}