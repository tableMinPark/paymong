package com.paymong.domain.watch.refac

import android.app.Application
import android.media.SoundPool
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.paymong.common.code.SoundCode

class SoundViewModel (
    application: Application
): AndroidViewModel(application) {
    private val soundPool: SoundPool = SoundPool.Builder().setMaxStreams(1).build()

    // main
    private var mainButtonSound by mutableStateOf(0)
    // feed
    private var feedButtonSound by mutableStateOf(0)

    // training
    private var trainingButtonSound by mutableStateOf(0)
    private var trainingWinSound by mutableStateOf(0)
    private var trainingLoseSound by mutableStateOf(0)

    // walking
    private var walkingButtonSound by mutableStateOf(0)

    // battle
    private var battleButtonSound by mutableStateOf(0)
    private var battleWinSound by mutableStateOf(0)
    private var battleLoseSound by mutableStateOf(0)
    private var battleAttackSound by mutableStateOf(0)
    private var battleDefenceSound by mutableStateOf(0)


    init {
        mainButtonSound = soundPool.load(application, com.paymong.common.R.raw.button_sound, 1)

        feedButtonSound = soundPool.load(application, com.paymong.common.R.raw.button_sound, 1)

        trainingButtonSound = soundPool.load(application, com.paymong.common.R.raw.button_sound, 1)
        trainingWinSound = soundPool.load(application, com.paymong.common.R.raw.win_sound, 1)
        trainingLoseSound = soundPool.load(application, com.paymong.common.R.raw.lose_sound, 1)

        walkingButtonSound = soundPool.load(application, com.paymong.common.R.raw.button_sound, 1)

        battleButtonSound = soundPool.load(application, com.paymong.common.R.raw.button_sound, 1)
        battleWinSound = soundPool.load(application, com.paymong.common.R.raw.win_sound, 1)
        battleLoseSound = soundPool.load(application, com.paymong.common.R.raw.lose_sound, 1)
        battleAttackSound = soundPool.load(application, com.paymong.common.R.raw.attack_sound, 1)
        battleDefenceSound = soundPool.load(application, com.paymong.common.R.raw.defence_sound, 1)
    }

    fun soundPlay (soundCode: SoundCode): Int {
        return when(soundCode) {
            SoundCode.MAIN_BUTTON -> getSound(mainButtonSound)
            SoundCode.FEED_BUTTON -> getSound(feedButtonSound)
            SoundCode.TRAINING_BUTTON -> getSound(trainingButtonSound)
            SoundCode.TRAINING_WIN -> getSound(trainingWinSound)
            SoundCode.TRAINING_LOSE -> getSound(trainingLoseSound)
            SoundCode.WALKING_BUTTON -> getSound(walkingButtonSound)
            SoundCode.BATTLE_BUTTON -> getSound(battleButtonSound)
            SoundCode.BATTLE_WIN -> getSound(battleWinSound)
            SoundCode.BATTLE_LOSE -> getSound(battleLoseSound)
            SoundCode.BATTLE_ATTACK -> getSound(battleAttackSound)
            SoundCode.BATTLE_DEFENCE -> getSound(battleDefenceSound)

            else -> 0
        }
    }

    private fun getSound(sound : Int) : Int {
        return soundPool.play(sound, 0.5f, 0.5f, 1, 0, 1.0f)
    }
}