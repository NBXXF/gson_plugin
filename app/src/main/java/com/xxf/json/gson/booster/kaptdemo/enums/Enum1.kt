package com.xxf.json.gson.booster.kaptdemo.enums

import com.google.gson.annotations.SerializedName
import com.xxf.json.gson.booster.annotation.JsonModel

@JsonModel
enum class Enum1 {
    @SerializedName("x")
    A,

    @SerializedName("2")
    B,

    @SerializedName("3")
    C
}