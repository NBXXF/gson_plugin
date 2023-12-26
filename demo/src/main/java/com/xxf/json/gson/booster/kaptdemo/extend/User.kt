package com.xxf.json.gson.booster.kaptdemo.extend

import com.xxf.json.gson.booster.annotation.JsonModel

@JsonModel
open class Parent(
    open var name1: String="",
    var age: Int=1
) {

}

@JsonModel
class WChild(
    override var name1: String = "",
    age2: Int = 1,
):Parent(name1,age2) {
    var des: String = "";
}
