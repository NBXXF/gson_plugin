package com.example.moshidemo

import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    val gson = GsonBuilder().create()
    val moshi = Moshi.Builder()
        .build()

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onResume() {
        super.onResume()

        val moshiBuilder = Moshi.Builder().add(MyEnumAdapter())
        val moshi = moshiBuilder.build()

        val clientBuilder = OkHttpClient.Builder().apply {
            addInterceptor(MyFakeInterceptor())
        }
        val client = clientBuilder.build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()

//        val service: ApiService = retrofit.create<ApiService>(ApiService::class.java)
//        service.getInfoObjects()
//            .subscribeOn(Schedulers.io())
//            .subscribe({ infoObjects ->
//                Log.d("OBJECTS", infoObjects.toString())
//            }, { error ->
//                Log.e("APIERROR", error.localizedMessage)
//            })

//        Thread(Runnable {
//            repeat(1){
//
//                val infoObject = InfoObject("123", "zhangsan", "xx", "xx", listOf(MyEnum.A))
//                test(infoObject,it)
//                test2(infoObject,it)
//
//                // val jsonAdapter: JsonAdapter<InfoObject> = moshi.adapter<InfoObject>(InfoObject::class.java)
//                val toJson = "{\"email\":\"xx\",\"id\":\"123\",\"myEnums\":[\"A\"],\"name\":\"zhangsan\",\"phone\":\"xx\"}"
//                // val toJson = GsonBuilder().create().toJson(infoObject)
//                test3(toJson,it)
//                test4(toJson,it)
//                println("==================================")
//            }
//        }).start()

        Thread(Runnable {
            testGsonOrMoshi()
        }).start()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun testGsonOrMoshi() {
        val json = json()
        val take1 = traceOnceJson(json) {
            gson.fromJson(it, Foo::class.java)
        }

        val adapter = moshi.adapter<Foo>(Foo::class.java);
        val take2 = traceOnceJson(json) {
            adapter.fromJson(it)!!
        }
        println("===================>ttt1:${take1}")
        println("===================>ttt2:${take2}")
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun traceOnceJson(json: String, callback: (json: String) -> Foo): Long {
        val start = SystemClock.elapsedRealtimeNanos()
        val bean = kotlin.runCatching {
            callback(json)
        }.onFailure {
        }.getOrNull()
        val end = SystemClock.elapsedRealtimeNanos()
        return end - start
    }


    private fun json() = assets.open("test.json").bufferedReader().use { it.readText() }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun test(obj: InfoObject, i: Int) {
        val jsonAdapter: JsonAdapter<InfoObject> = moshi.adapter<InfoObject>(InfoObject::class.java)
        val start = SystemClock.elapsedRealtimeNanos();
        val toJson = jsonAdapter.toJson(obj)
        val end = SystemClock.elapsedRealtimeNanos();
        println("======>take:${(end - start) / 1000}")
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun test2(obj: InfoObject, i: Int) {
        val start = SystemClock.elapsedRealtimeNanos();
        val toJson = gson.toJson(obj)
        val end = SystemClock.elapsedRealtimeNanos();
        println("======>take:" + "${(end - start) / 1000}")
    }


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun test3(obj: String, i: Int) {
        val jsonAdapter: JsonAdapter<InfoObject> = moshi.adapter<InfoObject>(InfoObject::class.java)
        val start = SystemClock.elapsedRealtimeNanos();
        val toJson = jsonAdapter.fromJson(obj)
        val end = SystemClock.elapsedRealtimeNanos();
        println("======>take:${(end - start) / 1000}")
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun test4(obj: String, i: Int) {
        val start = SystemClock.elapsedRealtimeNanos();
        val toJson = gson.fromJson<InfoObject>(obj, InfoObject::class.java)
        val end = SystemClock.elapsedRealtimeNanos();
        println("======>take:" + "${(end - start) / 1000}")
    }
}