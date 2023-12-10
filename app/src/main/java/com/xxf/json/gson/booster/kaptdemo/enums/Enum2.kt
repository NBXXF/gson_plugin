package com.xxf.json.gson.booster.kaptdemo.enums

import com.google.gson.annotations.SerializedName
import com.xxf.json.gson.booster.annotation.JsonModel
import com.xxf.json.gson.booster.annotation.SerializedType

@JsonModel(serializedType = SerializedType.INT)
enum class Enum2 {
    @SerializedName("1")
    A,

    @SerializedName("2")
    B,

    @SerializedName("3")
    C
}