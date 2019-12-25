package com.example.rsp

import com.example.rsp.model.Player
import org.junit.Test

import org.junit.Assert.*
import kotlin.random.Random

class PlayerTest {

    @Test
    fun getLower() {
        assertTrue(
            Player.ROCK.getLower().contains(
                Player.SCISSORS))
        assertFalse(
            Player.ROCK.getLower().contains(
                Player.PAPER))
    }

    @Test
    fun getHigher() {
        assertTrue(
            Player.ROCK.getHigher().contains(
                Player.PAPER))
        assertFalse(
            Player.ROCK.getHigher().contains(
                Player.SCISSORS))
    }

    @Test
    fun checkResult() {
        val user = getRandomSelection()
        val computer = getRandomSelection()
        when (user.isWon(computer)) {
            0 -> {
                assertTrue(user == computer)
                assertFalse(user.getHigher().contains(computer))
                assertFalse(user.getLower().contains(computer))
            }
            1 -> {
                assertTrue(user.getLower().contains(computer))
                assertFalse(user.getHigher().contains(computer))
            }
            -1 -> {
                assertTrue(user.getHigher().contains(computer))
                assertFalse(user.getLower().contains(computer))
            }
        }
    }


    fun getRandomSelection(): Player {
        return Player.values()[Random.nextInt(3)]
    }
}