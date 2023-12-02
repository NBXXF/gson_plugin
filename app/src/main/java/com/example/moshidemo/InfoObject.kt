package com.example.moshidemo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InfoObject(
        @Json(name = "id") val id: String?,
        @Json(name = "name") val name: String,
        @Json(name = "email") val email: String,
        @Json(name = "phone") val phone: String,
        @Json(name = "myEnums") val myEnums: List<MyEnum>
)

@JsonClass(generateAdapter = true)
data class InfoObject2 (
        @Json(name = "id")
        var id: String = "xxx",
        @Json(name = "name")
        var name: String = "",
        @Json(name = "email")
        var email: String = "",
        @Json(name = "phone")
        var phone: String = "",
        @Json(name = "myEnums")
        var myEnums: List<MyEnum> = listOf()
)