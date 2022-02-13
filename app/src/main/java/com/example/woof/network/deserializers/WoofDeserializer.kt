package com.example.woof.network.deserializers

import com.example.woof.network.model.WoofModel
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

class WoofDeserializer: ResponseDeserializable<WoofModel> {
    override fun deserialize(content: String): WoofModel? {
        return Gson().fromJson(content, WoofModel::class.java)
    }
}