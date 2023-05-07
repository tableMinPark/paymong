package com.paymong.domain.app

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.common.code.CharacterCode
import com.paymong.common.code.LandingCode
import com.paymong.common.code.MapCode
import com.paymong.common.code.MongStateCode
import com.paymong.data.model.request.AddMongReqDto
import com.paymong.data.model.request.LoginReqDto
import com.paymong.data.repository.ManagementRepository
import com.paymong.data.repository.AuthRepository
import com.paymong.data.repository.InformationRepository
import com.paymong.data.repository.MemberRepository
import com.paymong.domain.entity.Mong
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {
    // 로그인 플래그
    var loginState by mutableStateOf(LandingCode.LOADING)
    // google play game 인가 여부
    var isAuthenticated by mutableStateOf(false)

    // 몽 생성
    var mongname by mutableStateOf("")
    var mongsleepStart by mutableStateOf("")
    var mongsleepEnd by mutableStateOf("")

    // 메인화면
    var eggTouchCount by mutableStateOf(0)
    var point by mutableStateOf(0L)
    var mapCode by mutableStateOf(MapCode.MP000)

    // 몽 정보
    var mong by mutableStateOf(Mong())
    var stateCode by mutableStateOf(MongStateCode.CD000)
    var poopCount by mutableStateOf(0)

    private val memberRepository: MemberRepository = MemberRepository()
    private val informationRepository: InformationRepository = InformationRepository()
    private val managementRepository: ManagementRepository = ManagementRepository()
    private val authRepository: AuthRepository = AuthRepository()

    // 메인화면 진입시 초기화
    fun mainInit() {
        viewModelScope.launch(Dispatchers.IO) {
            findMong()
            findPoint()
        }
    }

    // 로그인
    fun login(playerId : String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.login(LoginReqDto(playerId))
                .catch { loginState = LandingCode.LOGIN_FAIL }
                .collect { values ->
                    loginState = if (values)
                        LandingCode.LOGIN_SUCCESS
                    else
                        LandingCode.LOGIN_FAIL
                }
        }
    }
    // 랜딩화면 로그인 확인
    fun loginCheck() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.reissue()
                .catch {
                    loginState = LandingCode.LOGIN_FAIL
                }
                .collect {values ->
                    loginState = if (values)
                        LandingCode.LOGIN_SUCCESS
                    else
                        LandingCode.LOGIN_FAIL
                }
            Log.e("loginCheck()", loginState.toString())
        }
    }

    // 몽 생성
    fun addMong(){
        viewModelScope.launch(Dispatchers.IO) {
            managementRepository.addMong(AddMongReqDto(mongname,mongsleepStart,mongsleepEnd))
                .catch {
                    it.printStackTrace()
                }
                .collect { data ->
                    mong = Mong(
                        0L,
                        data.name,
                        CharacterCode.valueOf(data.mongCode)
                    )
                }
        }
    }
    // 몽 정보
    private fun findMong() {
        viewModelScope.launch(Dispatchers.IO) {
            informationRepository.findMong()
                .catch {
                    it.printStackTrace()
                }
                .collect { data ->
                    mong = Mong(
                        data.mongId,
                        data.name,
                        CharacterCode.valueOf(data.mongCode)
                    )
                    stateCode = MongStateCode.valueOf(data.stateCode)
                    poopCount = data.poopCount
                    mapCode = MapCode.valueOf(data.mapCode ?: "MP000")
                }
        }
    }
    // 포인트
    private fun findPoint() {
        viewModelScope.launch(Dispatchers.IO) {
            memberRepository.findMember()
                .catch {
                    it.printStackTrace()
                }
                .collect { data ->
                    point = data.point
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}