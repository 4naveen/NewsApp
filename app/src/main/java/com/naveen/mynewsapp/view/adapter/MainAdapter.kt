package com.naveen.mynewsapp.view.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naveen.mynewsapp.R
import com.naveen.mynewsapp.service.data.model.MediaItem
import com.naveen.mynewsapp.view.callback.DiffUtilCallback
import com.naveen.mynewsapp.view.ui.VideoViewActivity
import kotlinx.android.synthetic.main.row_item.view.*


class MainAdapter( val context:Context,private val apiResponse: ArrayList<MediaItem>) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {
    private var url_list: ArrayList<String> ?= ArrayList()
    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var row : MediaItem? = null
        private var context:Context?=null
        init {
            this.context=context
            itemView.setOnClickListener {this}
        }
        fun bind(row: MediaItem) {
            this.row = row
            itemView.apply {
                title.text = row.title
                Glide.with(image.context)
                    .load(row.image)
                    .into(image)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        )

    override fun getItemCount(): Int = apiResponse.size

    fun addList(newApiResponse: ArrayList<MediaItem>) {

        val diffCallback = DiffUtilCallback(apiResponse, newApiResponse)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        apiResponse.clear()
        apiResponse.addAll(newApiResponse)
        diffResult.dispatchUpdatesTo(this)

    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(apiResponse[position])
        for (url in apiResponse){
            url_list!!.add(url.url)
        }
        holder.itemView.setOnClickListener(View.OnClickListener {
            Toast.makeText(context, "clicked"+apiResponse[position].url, Toast.LENGTH_SHORT).show();


            val intent = Intent(context, VideoViewActivity::class.java).apply {
                putStringArrayListExtra("urls", url_list)
                putExtra("position", position)
            }
            context.startActivity(intent)
        })
    }
}