package com.xxf.json.gson.booster.kaptdemo.expose

import com.google.gson.annotations.Expose
import com.xxf.json.gson.booster.annotation.JsonModel

@JsonModel
data class ExposeBean3(
    @Expose(serialize = false)
    val name: String = "",
    @Expose(deserialize = false)
    val name2: String = ""
)