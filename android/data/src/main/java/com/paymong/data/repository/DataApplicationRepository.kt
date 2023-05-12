package com.paymong.data.repository

import com.paymong.data.DataApplication

class DataApplicationRepository {
    fun getValue(key: String) : String {
        return DataApplication.prefs.getString(key, "")
    }
    fun setValue(key: String, value: String) {
        DataApplication.prefs.setString(key, value)
    }
}