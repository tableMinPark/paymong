package com.paymong.domain.watch

import android.app.Application
import android.media.SoundPool
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel

class ActivityViewModel (
    private val application: Application
): AndroidViewModel(application) {

    var buttonSound by mutableStateOf(0)
    var winSound by mutableStateOf(0)
    var loseSound by mutableStateOf(0)
    private lateinit var soundPool : SoundPool
    init {
        SoundPool.Builder().setMaxStreams(1).build()
        buttonSound = soundPool.load(application, com.paymong.common.R.raw.button_sound, 1)
        winSound = soundPool.load(application, com.paymong.common.R.raw.win_sound, 1)
        loseSound = soundPool.load(application, com.paymong.common.R.raw.lose_sound, 1)
    }
}