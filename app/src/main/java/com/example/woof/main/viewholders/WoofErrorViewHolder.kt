package com.example.woof.main.viewholders

import com.example.woof.main.WoofItem
import com.example.woof.databinding.ItemWoofErrorBinding

class WoofErrorViewHolder(val binding: ItemWoofErrorBinding): WoofViewHolder<WoofItem.Error>(binding.root) {

    override fun onBind(item: WoofItem.Error) {
        binding.woofErrorText.text = item.errorMsg
    }

}