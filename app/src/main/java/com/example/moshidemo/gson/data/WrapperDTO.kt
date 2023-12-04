package com.example.moshidemo.gson.data

import com.xxf.json.gson.booster.annotation.AutoGeneratedJsonAdapter

@AutoGeneratedJsonAdapter
class WrapperDTO(val child: Child = Child(1)) {
    @AutoGeneratedJsonAdapter
    class Child(val age:Int=1){

    }
}

@AutoGeneratedJsonAdapter
data class WrapperDTO2(val child: Child2 = Child2(1)) {
    companion object{
        @AutoGeneratedJsonAdapter
       data class Child2(val age:Int=1){

        }
    }
}