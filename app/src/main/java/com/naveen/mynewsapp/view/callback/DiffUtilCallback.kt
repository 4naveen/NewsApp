package com.naveen.mynewsapp.view.callback

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.naveen.mynewsapp.service.data.model.MediaItem

class DiffUtilCallback(private val oldList: List<MediaItem>, private val newList: List<MediaItem>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title === newList.get(newItemPosition).title
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old: MediaItem = oldList.get(oldItemPosition)
        val new: MediaItem = newList.get(newItemPosition)
        return old.equals(new)
    }
}