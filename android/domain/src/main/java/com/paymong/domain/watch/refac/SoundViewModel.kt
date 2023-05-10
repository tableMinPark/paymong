package com.paymong.domain.watch.refac

import android.app.Application
import android.media.SoundPool
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel

class SoundViewModel (
    private val application: Application
): AndroidViewModel(application) {

    // training
    var trainingButtonSound by mutableStateOf(0)
    var trainingWinSound by mutableStateOf(0)
    var trainingLoseSound by mutableStateOf(0)

    private lateinit var soundPool: SoundPool

    init {
        soundPool = SoundPool.Builder().setMaxStreams(1).build()
        trainingButtonSound = soundPool.load(application, com.paymong.common.R.raw.button_sound, 1)
        trainingWinSound = soundPool.load(application, com.paymong.common.R.raw.win_sound, 1)
        trainingLoseSound = soundPool.load(application, com.paymong.common.R.raw.lose_sound, 1)
    }

}