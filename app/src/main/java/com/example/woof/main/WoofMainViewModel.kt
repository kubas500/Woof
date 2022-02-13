package com.example.woof.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.woof.R
import com.example.woof.mvi.MviViewModel
import com.example.woof.network.usecase.GetRandomDogUseCase
import com.example.woof.network.usecase.GetRepeatedlyRandomDogUseCaseFlow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WoofMainViewModel :
    MviViewModel<WoofMainContract.State, WoofMainContract.Event, WoofMainContract.Effect>() {

    private val cacheDogList = mutableListOf<WoofItem>()
    val dogList: MutableLiveData<List<WoofItem>> = MutableLiveData()

    override fun createInitialState(): WoofMainContract.State = WoofMainContract.State.Welcome

    override fun handleEvent(event: WoofMainContract.Event) {
        when (event) {
            WoofMainContract.Event.ShowDogs -> {
                setState { WoofMainContract.State.Progress }
                fetchDogs()
            }
            WoofMainContract.Event.RefreshDogList -> fetchDogs()
            is WoofMainContract.Event.ItemClicked -> {
                if (event.item is WoofItem.Loaded)
                    when (event.item.getType()) {
                        WoofItem.Loaded.Type.GIF, WoofItem.Loaded.Type.IMAGE -> {
                            setEffect { WoofMainContract.Effect.OpenImage(event.item.url) }
                        }
                        WoofItem.Loaded.Type.VIDEO -> {
                            setEffect { WoofMainContract.Effect.OpenVideo(event.item.url) }
                        }
                        else -> {
                            setEffect { WoofMainContract.Effect.Snackbar(R.string.unsupported_file) }
                        }
                    }
            }
            WoofMainContract.Event.OnBackClicked -> {
                setEffect {
                    WoofMainContract.Effect.Dialog(
                        R.string.close_app_information,
                        actionPositive = {
                            setEffect { WoofMainContract.Effect.CloseApp }
                        },
                        actionNegative = {

                        })
                }
            }
        }
    }

    private fun fetchDogs() {
        viewModelScope.launch {
            GetRepeatedlyRandomDogUseCaseFlow().get()
                .take(10)
                .buffer()
                .onStart { cacheDogList.clear() }
                .onCompletion {
                    dogList.value = cacheDogList
                    setState { WoofMainContract.State.DogsList }
                }
                .collect {
                    val result = when (it) {
                        is GetRandomDogUseCase.State.Error -> WoofItem.Error(it.msg)
                        is GetRandomDogUseCase.State.Success -> WoofItem.Loaded(
                            it.model.size,
                            it.model.url
                        )
                    }
                    cacheDogList.add(result)
                }
        }
    }
}