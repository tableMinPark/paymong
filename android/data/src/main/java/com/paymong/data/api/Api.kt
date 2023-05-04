package com.paymong.data.api

import android.util.Log
import com.paymong.data.DataApplication
import com.paymong.data.model.request.ReissueReqDto
import com.paymong.data.repository.LoginRepository
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
                .addInterceptor(AccessTokenExpireInterceptor())
                .build()
        }
        
        // 헤더에 토큰 탑재
        private class AccessTokenInterceptor : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val accessToken = DataApplication.prefs.getString("accessToken", "")

                Log.d("intercept - request", chain.request().toString())

                return if ("" != accessToken) {
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

        // 토큰 재발급 
        private class AccessTokenExpireInterceptor : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val response = chain.proceed(chain.request())

                Log.d("intercept - response", response.toString())

                val loginRepository = LoginRepository()

                // 토큰 만료
                if (response.code() == 403) {
                    val isSuccess = loginRepository.reissue()
                    // 토큰 재발급 성공 시 기존 요청 재전송
                    if (isSuccess) {
                        val accessToken = DataApplication.prefs.getString("accessToken", "")
                        val token = "Bearer $accessToken"
                        val newRequest = chain.request().newBuilder()
                            .addHeader("Authorization", token)
                            .build()
                        return chain.proceed(newRequest)
                    }
                }

                return response
            }
        }
    }
}

