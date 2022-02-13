package com.example.woof.main.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.woof.main.WoofItem

abstract class WoofViewHolder<T: WoofItem>(itemView: View): RecyclerView.ViewHolder(itemView) {

    abstract fun onBind(item: T)
}