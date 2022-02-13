package com.example.woof.main.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.woof.databinding.ItemWoofErrorBinding
import com.example.woof.databinding.ItemWoofLoadedBinding
import com.example.woof.main.WoofItem
import java.lang.IllegalStateException

enum class WoofViewHolderType(val type: Int) {
    LOADED(0) {
        override fun createViewHolder(parent: ViewGroup) =
            WoofLoadedViewHolder(ItemWoofLoadedBinding.inflate(LayoutInflater.from(parent.context), parent, false)) as WoofViewHolder<WoofItem>
    },
    ERROR(1) {
        override fun createViewHolder(parent: ViewGroup) =
            WoofErrorViewHolder(ItemWoofErrorBinding.inflate(LayoutInflater.from(parent.context), parent, false)) as  WoofViewHolder<WoofItem>
    }
    ;

    abstract fun createViewHolder(parent: ViewGroup): WoofViewHolder<WoofItem>

    companion object {
        operator fun invoke(type: Int) = values().find { it.type == type }
            ?: throw IllegalStateException("Trying to use not existing view holder type")
    }
}