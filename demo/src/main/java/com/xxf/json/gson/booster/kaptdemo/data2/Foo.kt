package com.xxf.json.gson.booster.kaptdemo.data2

import com.google.gson.annotations.SerializedName
import com.xxf.json.gson.booster.annotation.JsonModel
import com.xxf.json.gson.booster.kaptdemo.data.Bar
import com.xxf.json.gson.booster.kaptdemo.data.Foo
import com.xxf.json.gson.booster.kaptdemo.data.NullableBean
import com.xxf.json.gson.booster.kaptdemo.data.VarFieldBean

@JsonModel(adapterNameSuffix = "GsonTypeAdapter", nullSafe = true)
data class Foo(
    @SerializedName("foo_int", alternate = ["xxx"])
    val intValue: Int = 0,
    @SerializedName("foo_string")
    val stringValue: String = "",
    @SerializedName("foo_long")
    val longValue: Long = 0L,
    @SerializedName("foo_boolean")
    val booleanValue: Boolean = false,
    @SerializedName("foo_double")
    val doubleValue: Double = 0.0,
    @SerializedName("foo_bar")
    val bar: Bar = Bar(),
    @SerializedName("foo_list_long")
    val listLong: List<Long> = listOf(),
    @SerializedName("foo_list_bar")
    val listBar: List<Bar> = listOf(),
    @SerializedName("foo_set_double")
    val setLong: Set<Double> = setOf(),
    @SerializedName("foo_set_bar")
    val setBar: Set<Bar> = setOf(),
    @SerializedName("foo_list_list_long")
    val nestedList: List<List<Long>> = listOf(),
    @SerializedName("foo_list_set_long")
    val listSet: List<Set<Long>> = listOf(),
    @SerializedName("foo_nullable_bean")
    val nullableBean: NullableBean = NullableBean(),
    @SerializedName("foo_test_enum")
    val testEnum: Foo.TestEnum = Foo.TestEnum.HELLO,
    @SerializedName("var_bean")
    val varFieldBean: VarFieldBean = VarFieldBean()
)