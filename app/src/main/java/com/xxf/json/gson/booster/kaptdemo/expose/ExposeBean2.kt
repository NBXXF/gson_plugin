package com.xxf.json.gson.booster.kaptdemo.expose

import com.xxf.json.gson.booster.annotation.JsonModel

@JsonModel
data class ExposeBean2(
    @Transient
    val name: String = ""
)