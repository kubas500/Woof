package com.example.woof.main.adapters

import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.woof.R
import com.example.woof.main.WoofItem
import com.example.woof.main.viewholders.WoofViewHolder
import com.example.woof.main.viewholders.WoofViewHolderType

class WoofListAdapter(private val action: WoofItem.() -> Unit): ListAdapter<WoofItem, WoofViewHolder<WoofItem>>(
    WoofDiff()
) {

    private class WoofDiff : DiffUtil.ItemCallback<WoofItem>() {
        override fun areItemsTheSame(
            oldItem: WoofItem,
            newItem: WoofItem
        ) : Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: WoofItem,
            newItem: WoofItem
        ) : Boolean {
            return oldItem == newItem
        }
    }

    override fun submitList(list: List<WoofItem>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WoofViewHolder<WoofItem> {
        return WoofViewHolderType(viewType).createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: WoofViewHolder<WoofItem>, position: Int) {
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.fall_down)

        val item = getItem(position)
        holder.onBind(item)
        holder.itemView.setOnClickListener {
            action.invoke(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is WoofItem.Loaded -> WoofViewHolderType.LOADED.type
            is WoofItem.Error -> WoofViewHolderType.ERROR.type
        }
    }

}