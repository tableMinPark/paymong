package com.paymong.data.repository

import androidx.lifecycle.MutableLiveData

class ThingsRepository {

    private var _thingsCode: MutableLiveData<String> = MutableLiveData<String>()
    val thingsCode: MutableLiveData<String> = _thingsCode

    fun changeThingsCode(thingsCode: String) {
        _thingsCode.postValue(thingsCode)
    }

}