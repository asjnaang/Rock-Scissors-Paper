package com.example.rsp.helper

import com.example.rsp.model.Player
import kotlin.random.Random

object Utils {

    fun getRandomPlayer(): Player {
        return Player.values()[Random.nextInt(3)]
    }

}