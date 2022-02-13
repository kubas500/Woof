package com.example.woof.main.viewholders

import android.annotation.SuppressLint
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.example.woof.R
import com.example.woof.main.WoofItem
import com.example.woof.databinding.ItemWoofLoadedBinding
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.woof.utils.dpToPx
import com.example.woof.utils.visible

class WoofLoadedViewHolder(val binding: ItemWoofLoadedBinding): WoofViewHolder<WoofItem.Loaded>(binding.root) {

    @SuppressLint("SetTextI18n")
    override fun onBind(item: WoofItem.Loaded) {
        binding.woofSizeText.text = binding.root.context.getString(R.string.size_file, item.getMB())
        binding.woofExtensionText.text = "${item.getType().name.toLowerCase()}/${item.getFileExtension()} "

        binding.woofImageProgress.visible(true)

        val options = RequestOptions().frame(0).transform(RoundedCorners(8.dpToPx()))
        Glide.with(binding.woofImage.context)
            .asBitmap()
            .load(item.url)
            .apply(options)
            .thumbnail(0.05f)
            .listener(object : RequestListener<Bitmap>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.woofImageProgress.visible(false)
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.woofImageProgress.visible(false)
                    return false
                }

            })
            .transition(BitmapTransitionOptions.withCrossFade())
            .into(binding.woofImage)
    }

}