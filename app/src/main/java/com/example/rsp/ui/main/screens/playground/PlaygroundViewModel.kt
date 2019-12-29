package com.example.rsp.ui.main.screens.playground

import android.app.Application
import android.graphics.drawable.Drawable
import android.os.Handler
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.example.rsp.R
import com.example.rsp.model.BaseViewModel
import com.example.rsp.model.Player

class PlaygroundViewModel(val application: Application) : BaseViewModel(application) {
    private val SCALE_XBY = 430F
    private val SCALE_YBY = 500F
    lateinit var playgroundViewListener: PlaygroundViewListener
    val isPlayModeEnabled: ObservableField<Int> = ObservableField(View.VISIBLE)
    val isCountDownTimerEnabled: ObservableField<Int> = ObservableField(View.GONE)
    val userScore: ObservableInt = ObservableInt(0)
    var roundScore: ObservableInt = ObservableInt(0)
    var computerScore: ObservableInt = ObservableInt(0)
    val isUserClickEnabled: ObservableBoolean = ObservableBoolean(false)
    val isComputerClickEnabled: ObservableBoolean = ObservableBoolean(false)
    lateinit var userSelection: Player
    lateinit var computerSelection: Player

    fun updatePlayModeEnabled() {
        isPlayModeEnabled.set(if (isPlayModeEnabled.get() == View.VISIBLE) View.GONE else View.VISIBLE)
        isCountDownTimerEnabled.set(if (isCountDownTimerEnabled.get() == View.VISIBLE) View.GONE else View.VISIBLE)
    }

    fun getUserImage(): Drawable? {
        return playgroundViewListener?.getPlayerImage()
    }

    fun goBack() {
        navigationListener?.goBack()
    }

    fun getPlayButtonText(): String {
        return if (roundScore.get() == 0) application.getString(R.string.play) else application.getString(R.string.play_again)
    }

    fun onGameStateSelected(player: Player, view: View, isUserClicked: Boolean) {
        if (isUserClicked) {
            userSelection = player
            isUserClickEnabled.set(false)
        } else {
            computerSelection = player
        }
        when(player) {
            Player.ROCK -> {
                animateXPositive(view)
                animateY(view, isUserClicked)

            }
           Player.SCISSORS -> {
                animateY(view, isUserClicked)
            }
            Player.PAPER -> {
                animateXNegative(view)
                animateY(view, isUserClicked)
            }
        }

        Handler().postDelayed({
            revertGameAnimationState(player, view, isUserClicked)
        }, 2000)
    }

    private fun animateXNegative(view: View) {
        view.animate().translationXBy(-1 * SCALE_XBY)
    }

    private fun animateXPositive(view: View) {
        view.animate().translationXBy(SCALE_XBY)
    }

    private fun animateY(view: View, isUserClicked: Boolean, isForward: Boolean = true) {
        val animationLength = if (isUserClicked) -1 * SCALE_YBY else SCALE_YBY
        view.animate().translationYBy(if (isForward) animationLength else -1 * animationLength)
    }

    private fun revertGameAnimationState(player: Player, view: View, isUserClicked: Boolean) {
        when(player) {
            Player.ROCK -> {
                animateXNegative(view)
                animateY(view, isUserClicked, false)
            }
            Player.SCISSORS -> {
                animateY(view, isUserClicked, false)
            }
            Player.PAPER -> {
                animateXPositive(view)
                animateY(view, isUserClicked, false)
            }
        }
    }

}

interface PlaygroundViewListener {
    fun getPlayerImage(): Drawable?
}
