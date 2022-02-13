package com.example.woof.media

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.woof.databinding.ActivityImageBinding
import com.example.woof.utils.extra
import com.example.woof.utils.viewBinding
import com.example.woof.utils.visible

class ImageActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityImageBinding::inflate)
    private val url by extra<String>(URL_EXTRA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        Glide.with(this)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressView.visible(false)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressView.visible(false)
                    return false
                }

            })
            .into(binding.imageView)

    }

    companion object {
        const val URL_EXTRA = "ImageActivity_URL_EXTRA"
    }
}