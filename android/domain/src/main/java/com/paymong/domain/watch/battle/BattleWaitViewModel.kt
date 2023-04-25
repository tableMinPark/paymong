package com.paymong.domain.watch.battle

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.google.android.gms.location.*

class BattleWaitViewModel constructor(
    private val stateHandle: SavedStateHandle,
): ViewModel() {

//    private lateinit var load: Job
    private lateinit var characterId: String

    private lateinit var ctx : Context
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null // 현재 위치를 가져오기 위한 변수
    private lateinit var mLastLocation: Location // 위치 값을 가지고 있는 객체
    private lateinit var mLocationRequest: LocationRequest // 위치 정보 요청의 매개변수를 저장하는
    private val REQUEST_PERMISSION_LOCATION = 10

    init {
//        if (::load.isInitialized) load.cancel()
//
//        load = viewModelScope.launch {
//            characterId = stateHandle.get<String>("characterId")
//                ?: throw IllegalStateException("No characterId was passed to destination.")
//            println(characterId)
//        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    @SuppressLint("MissingPermission")
    fun setLocationManager(context: Context, callback: () -> Unit) {
        ctx = context
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(ctx)
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED
            || ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(
                ctx,
                "위치 권한이 없습니다.\n권한에 동의해주세요.\n(애플리케이션 - 권한 - 페이몽)",
                Toast.LENGTH_LONG
            ).show()
            callback()
        }

        mFusedLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
    }
    // 시스템으로 부터 위치 정보를 콜백으로 받음
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            // 시스템에서 받은 location 정보를 onLocationChanged()에 전달
            mLastLocation = locationResult.lastLocation
            println("위도 : " + mLastLocation.latitude) // 갱신 된 위도
            println("경도 : " + mLastLocation.longitude) // 갱신 된 경도

            mFusedLocationProviderClient!!.removeLocationUpdates(this)
        }
    }
}