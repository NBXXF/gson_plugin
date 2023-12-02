package com.example.moshidemo

import java.lang.IllegalArgumentException

enum class MyEnum(val type: Int) {
    A(1), B(2), C(3);

    companion object {
        fun fromType(type: Int): MyEnum {
            return when (type) {
                1 -> A
                2 -> B
                3 -> C
                else -> throw IllegalArgumentException("Undefined enum type")
            }
        }
    }
}
