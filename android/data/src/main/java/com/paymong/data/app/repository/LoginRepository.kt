package com.paymong.data.app.repository

import com.paymong.data.app.api.Apis

class LoginRepository {

    private val api = Apis.create()
    
    fun registerLogin(id : String) {
        if(id!=""){
            api.register(id)
            println("api = $api")
        }
    }
}