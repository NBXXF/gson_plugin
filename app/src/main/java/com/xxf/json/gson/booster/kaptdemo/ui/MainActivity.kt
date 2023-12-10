package com.xxf.json.gson.booster.kaptdemo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.xxf.json.gson.booster.kaptdemo.R
import com.xxf.json.gson.booster.kaptdemo.data.Foo
import com.xxf.json.gson.booster.kaptdemo.data2.Parent
import com.xxf.json.gson.booster.kaptdemo.data2.Utils
import com.xxf.json.gson.plugin.AutoTypeAdapterFactory
import java.util.concurrent.TimeUnit

class MainActivity : FragmentActivity() {

    companion object {
        private const val TAG = "MainActivityTest"
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

            val commonTimeCost = traceOnceJson(common, json)
            val boostTimeCost = traceOnceJson(boost, json)
            findViewById<TextView>(R.id.result).text = """
                common time cost: ${TimeUnit.NANOSECONDS.toMicros(commonTimeCost) / 1000.0}
                boost time cost:  ${TimeUnit.NANOSECONDS.toMicros(boostTimeCost) / 1000.0}
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
            println("====================>xxxx:${fromJson}")

        }catch (e:Throwable) {
            println("====================>xxxx:${e}")
        }
    }

    private fun traceOnceJson(gson: Gson, json: String): Long {
        val start = SystemClock.elapsedRealtimeNanos()
        val bean = kotlin.runCatching {
            gson.fromJson<Foo>(json, Foo::class.java)
        }.onFailure {
            Log.d(TAG, Log.getStackTraceString(it))
        }.getOrNull()
        val end = SystemClock.elapsedRealtimeNanos()
        Log.d(TAG, "$bean")
        return end - start
    }

    private fun json() = assets.open("test.json").bufferedReader().use { it.readText() }
}