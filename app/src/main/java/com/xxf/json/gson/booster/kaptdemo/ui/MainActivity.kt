package com.xxf.json.gson.booster.kaptdemo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.xxf.json.gson.booster.kaptdemo.R
import com.xxf.json.gson.booster.kaptdemo.data.Foo
import com.xxf.json.gson.booster.kaptdemo.data2.Parent
import com.xxf.json.gson.booster.kaptdemo.data2.Utils
import com.xxf.json.gson.booster.kaptdemo.ui.utils.runCosting
import com.xxf.json.gson.plugin.AutoTypeAdapterFactory
import java.util.concurrent.TimeUnit

class MainActivity : FragmentActivity() {

    companion object {
        private const val TAG = "=========>gson"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val json = json()
        findViewById<Button>(R.id.testBtn).setOnClickListener {
            val common = GsonBuilder().create()
            val boost = GsonBuilder()
                .registerTypeAdapterFactory(AutoTypeAdapterFactory())
                .create()

            val commonTimeCost = runCoastingWithJson(common, json)
            val boostTimeCost = runCoastingWithJson(boost, json)
            findViewById<TextView>(R.id.result).text = """
                正常情况: ${TimeUnit.NANOSECONDS.toMicros(commonTimeCost) / 1000.0}
                使用注解生成适配器后:  ${TimeUnit.NANOSECONDS.toMicros(boostTimeCost) / 1000.0}
            """.trimIndent()
            test()
        }
    }

    private fun test(){
        try {
            val gson = Utils.create().newBuilder()
                .registerTypeAdapterFactory(AutoTypeAdapterFactory())
                .create()
            val v=Parent(name = "李四")
            val toJson = gson.toJsonTree(v) as JsonObject
            toJson.addProperty("age","")
            val fromJson = gson.fromJson<Parent>(toJson, Parent::class.java)
            println("====================>int 兼容用系统的:${fromJson}")

        }catch (e:Throwable) {
            println("====================>int 兼容错误:${e}")
        }
    }

    private fun runCoastingWithJson(gson: Gson, json: String): Long {
        var bean:Foo?=null
        return runCosting {
            bean = gson.fromJson<Foo>(json, Foo::class.java)
        }.also {
            Log.d(TAG, "$bean")
        }
    }

    private fun json() = assets.open("test.json").bufferedReader().use { it.readText() }
}