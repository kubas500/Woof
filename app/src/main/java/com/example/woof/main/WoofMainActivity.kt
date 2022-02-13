package com.example.woof.main

import android.app.ActivityManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.example.woof.R
import com.example.woof.databinding.ActivityWoofMainBinding
import com.example.woof.main.fragments.WoofListFragment
import com.example.woof.main.fragments.WoofProgressFragment
import com.example.woof.main.fragments.WoofWelcomeFragment
import com.example.woof.media.ImageActivity
import com.example.woof.media.VideoActivity
import com.example.woof.utils.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlin.system.exitProcess

class WoofMainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityWoofMainBinding::inflate)

    private val viewModel by viewModels<WoofMainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                val fragment = when (it) {
                    WoofMainContract.State.Welcome -> WoofWelcomeFragment()
                    WoofMainContract.State.Progress -> WoofProgressFragment()
                    WoofMainContract.State.DogsList -> WoofListFragment()
                }

                supportFragmentManager.commit {
                    this.replace(binding.containerView.id, fragment)
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.effect.collect {
                when (it) {
                    is WoofMainContract.Effect.Snackbar -> Snackbar.make(
                        binding.root,
                        it.resID,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    WoofMainContract.Effect.CloseApp -> {
                        (getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager)?.let { manager ->
                            manager.appTasks.forEach { appTask ->
                                appTask.finishAndRemoveTask()
                            }
                        }
                    }
                    is WoofMainContract.Effect.Dialog -> {
                        MaterialAlertDialogBuilder(this@WoofMainActivity)
                            .setMessage(it.resID)
                            .setPositiveButton(R.string.ok) { _: DialogInterface, i: Int ->
                                it.actionPositive.invoke()
                            }
                            .setNegativeButton(R.string.cancel) { _: DialogInterface, i: Int ->
                                it.actionNegative.invoke()
                            }
                            .show()
                    }
                    is WoofMainContract.Effect.OpenImage -> {
                        startActivity(
                            Intent(this@WoofMainActivity, ImageActivity::class.java).apply {
                                putExtra(ImageActivity.URL_EXTRA, it.url)
                            }
                        )
                    }
                    is WoofMainContract.Effect.OpenVideo -> {
                        startActivity(
                            Intent(this@WoofMainActivity, VideoActivity::class.java).apply {
                                putExtra(VideoActivity.URL_EXTRA, it.url)
                            }
                        )
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        viewModel.setEvent { WoofMainContract.Event.OnBackClicked }
    }
}