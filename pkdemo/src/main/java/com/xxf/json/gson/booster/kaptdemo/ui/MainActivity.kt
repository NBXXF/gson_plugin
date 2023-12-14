package com.xxf.json.gson.booster.kaptdemo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.xxf.json.gson.booster.kaptdemo.R
import com.xxf.json.gson.booster.kaptdemo.data.Foo
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
            val boost =
                GsonBuilder().registerTypeAdapterFactory(AutoTypeAdapterFactory()).create().apply {
                        AutoTypeAdapterFactory.load(this)
                    }
            val moshi = Moshi.Builder().build()

            val commonTimeCost = runCoastingWithJson(common, json)
            val boostTimeCost = runCoastingWithJson(boost, json)
            val moshiCodeGenTimeCost = runCoastingWithMoshi(moshi, json)
//            val ksTimeCost = runCoastingWithKS(json)

            val model = common.fromJson<Foo>(json, Foo::class.java)



            findViewById<TextView>(R.id.result).text = buildString {
                this.append("\n反序列化时间")
                this.append(
                    "\ngson正常情况:".wrapLength(20) + "${
                        TimeUnit.NANOSECONDS.toMicros(
                            commonTimeCost
                        ) / 1000.0
                    }"
                )
                this.append(
                    "\ngson加速后:".wrapLength(20) + "${
                        TimeUnit.NANOSECONDS.toMicros(
                            boostTimeCost
                        ) / 1000.0
                    }"
                )
                this.append(
                    "\nmoshi(code-gen):".wrapLength(20) + "${
                        TimeUnit.NANOSECONDS.toMicros(
                            moshiCodeGenTimeCost
                        ) / 1000.0
                    }"
                )
                // this.append("\nkotlinx.serialization:${TimeUnit.NANOSECONDS.toMicros(ksTimeCost) / 1000.0}")

                this.append("\n序列化时间")
                val model = common.fromJson<Foo>(json, Foo::class.java)
                this.append("\ngson正常情况:".wrapLength(20) + "${runCosting { common.toJson(model) } / 1000.0}")
                this.append("\ngson加速后:".wrapLength(20) + "${runCosting { boost.toJson(model) } / 1000.0}")
                this.append("\nmoshi(code-gen):".wrapLength(20) + "${
                    runCosting {
                        moshi.adapter<Foo>(Foo::class.java).toJson(model)
                    } / 1000.0
                }")

            }
        }
    }

    inline fun String.wrapLength(length: Int):String {
        val spaceCpount = length - this.length
        return buildString {
            append(this@wrapLength)
            if (spaceCpount > 0) {
                repeat(spaceCpount) {
                    append(" ")
                }
            }
        }
    }


    private fun runCoastingWithJson(gson: Gson, json: String): Long {
        var bean: Foo? = null
        return runCosting {
            bean = gson.fromJson<Foo>(json, Foo::class.java)
        }.also {
            Log.d(TAG, "$bean")
        }
    }

    private fun runCoastingWithMoshi(moshi: Moshi, json: String): Long {
        var bean: Foo? = null
        return runCosting {
            bean = moshi.adapter<Foo>(Foo::class.java).fromJson(json)
        }.also {
            Log.d(TAG, "$bean")
        }
    }

//    private fun runCoastingWithKS(json: String): Long {
//        var bean: Foo? = null
//        return runCosting {
//            bean =  Json.decodeFromString<Foo>(json)
//        }.also {
//            Log.d(TAG, "$bean")
//        }
//    }

    private fun json() = assets.open("test.json").bufferedReader().use { it.readText() }
}