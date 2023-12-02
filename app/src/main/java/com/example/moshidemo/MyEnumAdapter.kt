package com.example.moshidemo

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class MyEnumAdapter {
    @ToJson
    private fun toJson(enum: MyEnum): Int {
        return enum.type
    }

    @FromJson
    fun fromJson(type: Int): MyEnum {
        return MyEnum.fromType(type)
    }
}