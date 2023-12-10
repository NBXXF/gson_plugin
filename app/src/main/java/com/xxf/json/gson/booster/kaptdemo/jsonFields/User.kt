package com.xxf.json.gson.booster.kaptdemo.jsonFields

import com.xxf.json.gson.booster.annotation.JsonField
import com.xxf.json.gson.booster.annotation.JsonModel

@JsonModel
class User(
    //@JsonAdapter(StringAllAssertNoNullAdapter::class)
    @JsonField(attached = true)
    val name:String="",
           @JsonField(attached = false)
           val age:Int=1){
    @JsonField(attached = false)
    var des:String="";
}