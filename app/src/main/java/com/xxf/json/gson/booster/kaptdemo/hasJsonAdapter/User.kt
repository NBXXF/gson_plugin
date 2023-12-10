package com.xxf.json.gson.booster.kaptdemo.hasJsonAdapter

import com.google.gson.annotations.JsonAdapter
import com.xxf.json.gson.booster.annotation.JsonModel

@JsonModel
class User(
    @JsonAdapter(StringAllAssertNoNullAdapter::class)
    val name:String="",
    val age:Int=1,
    @JsonAdapter(IntegerTypeAdapter::class)
    val sex:Int=1,
) {
}