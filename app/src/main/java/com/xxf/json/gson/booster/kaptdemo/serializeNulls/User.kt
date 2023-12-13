package com.xxf.json.gson.booster.kaptdemo.serializeNulls

import com.xxf.json.gson.booster.annotation.JsonModel
import com.xxf.json.gson.booster.enums.SerializeNulls
@JsonModel(serializeNulls = SerializeNulls.INHERIT)
class User1(
    val name: String = "",
    val age: Int = 1
) {
    var des: String = "";
}

@JsonModel(serializeNulls = SerializeNulls.YES)
class User2(
    val name: String = "",
    val age: Int = 1
) {
    var des: String = "";
}

@JsonModel(serializeNulls = SerializeNulls.NO)
class User3(
    val name: String = "",
    val age: Int = 1
) {
    var des: String = "";
}