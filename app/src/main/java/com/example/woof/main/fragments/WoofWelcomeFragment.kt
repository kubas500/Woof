package com.example.woof.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.woof.R
import com.example.woof.main.WoofMainContract
import com.example.woof.main.WoofMainViewModel
import com.example.woof.databinding.FragmentWoofWelcomeBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class WoofWelcomeFragment : Fragment(R.layout.fragment_woof_welcome) {
    private val binding by viewBinding(FragmentWoofWelcomeBinding::bind)

    private val viewModel by activityViewModels<WoofMainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.showDogsButton.setOnClickListener {
            viewModel.setEvent { WoofMainContract.Event.ShowDogs }
        }
    }
}