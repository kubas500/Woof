package com.example.woof.network.usecase

import com.example.woof.network.deserializers.WoofDeserializer
import com.example.woof.network.model.WoofModel
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.awaitResult

class GetRandomDogUseCase {

    private val URL = "https://random.dog/woof.json"

    suspend fun get() = Fuel.get(URL)
        .awaitResult(WoofDeserializer())
        .fold(
            { data ->
                State.Success(data)
            },
            { error ->
                State.Error(error.message ?: "Unknown error. Check internet connection")
            }
        )

    sealed class State {
        class Success(val model: WoofModel) : State()
        class Error(val msg: String) : State()
    }
}


