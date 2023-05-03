package com.paymong.data.app.repository

import android.hardware.display.VirtualDisplay
import com.paymong.data.app.api.Apis
import com.paymong.data.dto.request.LoginReq
import com.paymong.data.dto.response.LoginRes

class LoginRepository {

    private val api = Apis.create()
    
    fun registerLogin(id : String) {
        val playerId = LoginReq(id)
//        api.register(playerId)?.enqueue(object : Callback<LoginRes> {
//
//        })

            println("api = $api")
    }
}