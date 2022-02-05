package com.example.newyorkburritos.api


import okhttp3.Interceptor
import okhttp3.Response

private const val API_KEY = "pdQYzkH8JHHYanoSn8k1UlmXojCJjTKYUkC6NXxNVNLzgpOZ6BSNsxdb9ZDjytRju0y4EQWIg8B6a6rxoTwDiusy0CNtj1SPm-bdAY8pR1Kw8qkiKLyD0Cl_yUXJYXYx"
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization","Bearer $API_KEY")
            .build()
        return chain.proceed(request)
    }
}