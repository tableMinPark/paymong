package com.paymong.ui

import com.paymong.data.app.api.Apis
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val api = com.paymong.data.app.api.Apis.create()
        api.register("id")
    }
}