package com.xxf.json.gson.booster.kaptdemo.data2

import com.xxf.json.gson.booster.annotation.JsonModel
import com.xxf.json.gson.booster.kaptdemo.data.WrapperDTO2
import java.math.BigDecimal


@JsonModel
data class WrapperDTO4(val child: WrapperDTO2.Companion.Child2?=null) {
}
@JsonModel(nullSafe = true,strictType = false)
data class Parent(val child:Parent?= Parent(child=null), val age:Int=5,val name:String="张三"){
    var ss:BigDecimal?=null;
    var ss2:Number?=null;
    override fun toString(): String {
        return "Parent(child=$child, age=$age, name='$name')"
    }
}

//open class a:b(){
//
//}
//open class b:a(){}
