package com.xxf.json.gson.booster.kaptdemo.data

import com.xxf.json.gson.booster.annotation.JsonModel

@JsonModel
class WrapperDTO(val child:Child= Child(1)) {
    @JsonModel
    class Child(val age:Int=1){
    }
}

@JsonModel
data class WrapperDTO2(val child:Child2= Child2(1)) {
    companion object{

        @JsonModel
       data class Child2(val age:Int=1){

        }
    }
}