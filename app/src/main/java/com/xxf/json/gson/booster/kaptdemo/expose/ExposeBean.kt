package com.xxf.json.gson.booster.kaptdemo.expose

import com.xxf.json.gson.booster.annotation.JsonModel

@JsonModel
class ExposeBean {
    @Transient
    val name: String = ""
}