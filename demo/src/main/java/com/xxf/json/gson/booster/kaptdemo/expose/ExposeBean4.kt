package com.xxf.json.gson.booster.kaptdemo.expose

import com.google.gson.annotations.Expose
import com.xxf.json.gson.booster.annotation.JsonModel

@JsonModel
class ExposeBean4(
    @Expose(serialize = false)
    var name: String = "xx"
){
   // @Expose(deserialize = false, serialize = true)
    var name2: String="xxx"

    constructor(name: String,name2: String) : this() {
        this.name=name;
        this.name2=name2;
    }
}