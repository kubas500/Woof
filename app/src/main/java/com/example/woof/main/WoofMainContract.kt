package com.example.woof.main

import androidx.annotation.StringRes
import com.example.woof.mvi.UiEffect
import com.example.woof.mvi.UiEvent
import com.example.woof.mvi.UiState

class WoofMainContract {

    sealed class State : UiState {
        object Welcome: State()
        object Progress: State()
        object DogsList: State()
    }

    sealed class Event : UiEvent {
        object ShowDogs: Event()
        object RefreshDogList: Event()
        class ItemClicked(val item: WoofItem): Event()
        object OnBackClicked: Event()
    }

    sealed class Effect : UiEffect {
        class Snackbar(@StringRes val resID: Int): Effect()
        class Dialog(@StringRes val resID: Int, val actionPositive: () -> Unit, val actionNegative: () -> Unit): Effect()
        object CloseApp: Effect()
        class OpenImage(val url: String): Effect()
        class OpenVideo(val url: String): Effect()
    }
}