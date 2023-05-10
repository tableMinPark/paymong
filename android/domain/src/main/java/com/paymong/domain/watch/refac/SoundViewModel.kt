package com.paymong.domain.watch.refac

import android.app.Application
import android.media.SoundPool
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.paymong.common.code.SoundCode

class SoundViewModel (
    private val application: Application
): AndroidViewModel(application) {
    // main
    private var mainButtonSound by mutableStateOf(0)
    // feed
    private var feedButtonSound by mutableStateOf(0)

    // training
    private var trainingButtonSound by mutableStateOf(0)
    private var trainingWinSound by mutableStateOf(0)
    private var trainingLoseSound by mutableStateOf(0)

    private val soundPool: SoundPool = SoundPool.Builder().setMaxStreams(1).build()

    init {
        mainButtonSound = soundPool.load(application, com.paymong.common.R.raw.button_sound, 1)

        feedButtonSound = soundPool.load(application, com.paymong.common.R.raw.button_sound, 1)

        trainingButtonSound = soundPool.load(application, com.paymong.common.R.raw.button_sound, 1)
        trainingWinSound = soundPool.load(application, com.paymong.common.R.raw.win_sound, 1)
        trainingLoseSound = soundPool.load(application, com.paymong.common.R.raw.lose_sound, 1)
    }

    fun soundPlay (soundCode: SoundCode): Int {
        return when(soundCode) {
            SoundCode.MAIN_BUTTON -> getSound(mainButtonSound)
            SoundCode.FEED_BUTTON -> getSound(feedButtonSound)
            SoundCode.TRAINING_BUTTON -> getSound(trainingButtonSound)
            SoundCode.TRAINING_WIN -> getSound(trainingWinSound)
            SoundCode.TRAINING_LOSE -> getSound(trainingLoseSound)
            else -> 0
        }
    }

    fun getSound(sound : Int) : Int {
        return soundPool.play(sound, 0.5f, 0.5f, 1, 0, 1.0f)
    }
}