package com.example.woof.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.woof.*
import com.example.woof.main.adapters.WoofListAdapter
import com.example.woof.databinding.FragmentWoofListBinding
import com.example.woof.main.WoofMainContract
import com.example.woof.main.WoofMainViewModel
import com.example.woof.utils.SpaceItemDecoration
import com.example.woof.utils.dpToPx
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class WoofListFragment : Fragment(R.layout.fragment_woof_list) {
    private val binding by viewBinding(FragmentWoofListBinding::bind)

    private val viewModel by activityViewModels<WoofMainViewModel>()
    private val woofAdapter = WoofListAdapter(){
        viewModel.setEvent { WoofMainContract.Event.ItemClicked(this) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dogListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(SpaceItemDecoration(16.dpToPx()))
            adapter = woofAdapter
        }

        binding.swipeRefreshLayout.isRefreshing = false
        viewModel.dogList.observe(this) {
            binding.swipeRefreshLayout.isRefreshing = false
            woofAdapter.submitList(it)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.setEvent { WoofMainContract.Event.RefreshDogList }
        }
    }

//    private fun collectDogs(){
//        lifecycleScope.launchWhenStarted {
//            viewModel.dogList
//                .onCompletion {
//                    binding.swipeRefreshLayout.isRefreshing = false
//                }
//                .collect {
////                    val it = when(it){
////                        is GetRandomDogUseCase.State.Error -> null
////                        is GetRandomDogUseCase.State.Success -> it.model
////                    }
//
//                    binding.textView.append(it?.url ?: "error")
//                    binding.textView.append("\n")
//                }
//        }
//    }
}