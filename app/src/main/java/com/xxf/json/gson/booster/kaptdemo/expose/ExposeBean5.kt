package com.xxf.json.gson.booster.kaptdemo.expose

import com.google.gson.annotations.Expose
import com.xxf.json.gson.booster.annotation.JsonModel

@JsonModel
class ExposeBean5(){
    @Expose(serialize = false)
    var name: String = ""
    @Expose(deserialize = false)
    var name2: String=""

    constructor(name: String,name2: String) : this() {
        this.name=name;
        this.name2=name2;
    }
}