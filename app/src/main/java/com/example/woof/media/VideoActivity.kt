package com.example.woof.media

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import com.example.woof.databinding.ActivityVideoBinding
import com.example.woof.utils.extra
import com.example.woof.utils.viewBinding
import com.example.woof.utils.visible

class VideoActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityVideoBinding::inflate)
    private val url by extra<String>(URL_EXTRA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        val mediaController = MediaController(this@VideoActivity);
        binding.videoView.setMediaController(mediaController)
        binding.videoView.setVideoPath(url)

        binding.videoView.setOnPreparedListener {
            binding.progressView.visible(false)
            binding.videoView.start()
        }
    }

    companion object {
        const val URL_EXTRA = "VideoActivity_URL_EXTRA"
    }
}