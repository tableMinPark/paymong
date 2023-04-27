package com.paymong.domain.watch.battle

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.location.*
import com.paymong.domain.watch.socket.SocketThread
import com.paymong.domain.watch.socket.WebSocketListener

@SuppressLint("MissingPermission")
class BattleWaitViewModel constructor (
    application: Application,
): AndroidViewModel(application) {

    private var context : Context
    private var characterId: String
    private var latitude: Double
    private var longitude: Double

    // ################################## GPS 설정 #####################################
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private var mLocationRequest: LocationRequest
    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            latitude = locationResult.lastLocation.latitude
            longitude = locationResult.lastLocation.longitude
            mFusedLocationProviderClient.removeLocationUpdates(this)
            // 소켓 시작
            webSocketListener = WebSocketListener()
            socketThread = SocketThread(webSocketListener)
            socketThread.isDaemon = true
            socketThread.start()
        }
    }

    // ################################## 소켓 설정 #####################################
    private lateinit var socketThread: SocketThread
    private lateinit var socketThreadHandler: Handler
    private lateinit var webSocketListener: WebSocketListener

    init {
        context = application
        characterId = ""
        latitude = 0.0
        longitude = 0.0

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        mLocationRequest =  LocationRequest.create().apply { priority = LocationRequest.PRIORITY_HIGH_ACCURACY }

        if (::mFusedLocationProviderClient.isInitialized) {
            mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
        }
    }

    override fun onCleared() {
        super.onCleared()
        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback)
    }
}
