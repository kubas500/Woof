package com.example.woof.network.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetRepeatedlyRandomDogUseCaseFlow() {

    fun get() = flow {
        try {
            while (true) {
                emit(GetRandomDogUseCase().get())
            }
        } finally {

        }
    }.flowOn(Dispatchers.IO)
}