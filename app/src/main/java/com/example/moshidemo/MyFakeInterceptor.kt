package com.example.moshidemo

import okhttp3.*

class MyFakeInterceptor : Interceptor {

    // Return mock data instead of going to real endpoint
    override fun intercept(chain: Interceptor.Chain): Response? {
        var response: Response? = null
        if (chain.request().url().host().contains("github")) {
            val responseString: String = """
            [
                {
                    "id" : "aaaa",
                    "name" : "aName",
                    "email" : "aEmail",
                    "phone" : "1234",
                    "myEnums" : [1, 2]
                },
                {
                    "id" : "bbbb",
                    "name" : "bName",
                    "email" : "bEmail",
                    "phone" : "1234",
                    "myEnums" : [1, 2, 3]
                }
            ]
        """.trimIndent()
            response = Response.Builder()
                .code(200)
                .message(responseString)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(
                    ResponseBody.create(
                        MediaType.parse("application/json"),
                        responseString.toByteArray()
                    )
                )
                .addHeader("content-type", "application/json")
                .build()
        } else {
            response = chain.proceed(chain.request())
        }
        return response
    }
}