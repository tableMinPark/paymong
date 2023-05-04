package com.paymong.data.api

import android.util.Log
import com.paymong.data.DataApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.HTTP
import java.io.IOException
import java.util.concurrent.TimeUnit


interface Api {
    companion object {
        private const val BASE_URL = "http://dev.paymong.com:8080/"
        private const val TIMEOUT_LIMIT = 180L

        fun getInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build()
        }

        private fun getOkHttpClient() : OkHttpClient {
            return OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_LIMIT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_LIMIT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_LIMIT, TimeUnit.SECONDS)
                .addInterceptor(AccessTokenInterceptor())
//                .addInterceptor(AccessTokenExpireInterceptor())
                .build()
        }
        private class AccessTokenInterceptor : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val accessToken = DataApplication.prefs.getString("accessToken", "")

                Log.d("accessToken", accessToken)

                return if (accessToken != null) {
                    val token = "Bearer $accessToken"
                    val newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", token)
                        .build()
                    chain.proceed(newRequest)
                } else {
                    chain.proceed(chain.request())
                }
            }
        }

//        private class AccessTokenExpireInterceptor : Interceptor {
//            @Throws(IOException::class)
//            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
//                val response = chain.proceed(chain.request())
//
//                // 토큰 만료
//                if (response.code() == 403) {
//
//                }
//                // 권한 없음
//                else if (response.code() == 401) {
//                    // 패스
//                }
//
//                return if (accessToken != null) {
//                    val token = "Bearer $accessToken"
//                    val newRequest = chain.request().newBuilder()
//                        .addHeader("Authorization", token)
//                        .build()
//                    chain.proceed(newRequest)
//                } else {
//                    chain.proceed(chain.request())
//                }
//            }
//        }
    }
}

