package com.xxf.json.gson.booster.kaptdemo.enums

import com.google.gson.annotations.SerializedName
import com.xxf.json.gson.booster.annotation.JsonModel
import com.xxf.json.gson.booster.enums.SerializedType

@JsonModel(serializedType = SerializedType.LONG)
enum class Enum3 {
    @SerializedName("1")
    A,

    @SerializedName("2")
    B,

    @SerializedName("3")
    C
}