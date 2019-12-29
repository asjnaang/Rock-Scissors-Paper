package com.example.rsp.ui.main.screens.playground

import android.app.Application
import android.graphics.drawable.Drawable
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.example.rsp.R
import com.example.rsp.helper.addOnPropertyChanged
import com.example.rsp.helper.plusOne
import com.example.rsp.model.BaseViewModel
import com.example.rsp.model.Player

class PlaygroundViewModel(val application: Application) : BaseViewModel(application) {
    private val scaleXby = 430F
    private val scaleYBy = 500F
    lateinit var playgroundViewListener: PlaygroundViewListener
    val isPlayModeEnabled: ObservableField<Int> = ObservableField(View.VISIBLE)
    val isCountDownTimerEnabled: ObservableField<Int> = ObservableField(View.GONE)
    val playButtonText: ObservableField<String> =
        ObservableField(application.getString(R.string.play))
    val userScore: ObservableInt = ObservableInt(0)
    var roundScore: ObservableInt = ObservableInt(0)
    var computerScore: ObservableInt = ObservableInt(0)
    val isUserClickEnabled: ObservableBoolean = ObservableBoolean(false)
    val isComputerClickEnabled: ObservableBoolean = ObservableBoolean(false)
    private var userSelection: Player? = null
    private var computerSelection: Player = Player.ROCK

    fun initObservables() {
        isCountDownTimerEnabled.addOnPropertyChanged {
            playgroundViewListener?.onCountDownTimerChanged(it)
        }
        roundScore.addOnPropertyChanged {
            if (it.get() >= 1) {
                playButtonText.set(application.getString(R.string.play_again))
            } else {
                playButtonText.set(application.getString(R.string.play))
            }
        }
    }

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

    fun onGameStateSelected(player: Player, view: View, isUserClicked: Boolean) {
        if (isUserClicked) {
            userSelection = player
        } else {
            computerSelection = player
        }
        isUserClickEnabled.set(false)
        when (player) {
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
        view.animate().translationXBy(-1 * scaleXby)
    }

    private fun animateXPositive(view: View) {
        view.animate().translationXBy(scaleXby)
    }

    private fun animateY(view: View, isUserClicked: Boolean, isForward: Boolean = true) {
        val animationLength = if (isUserClicked) -1 * scaleYBy else scaleYBy
        view.animate().translationYBy(if (isForward) animationLength else -1 * animationLength)
    }

    private fun revertGameAnimationState(player: Player, view: View, isUserClicked: Boolean) {
        when (player) {
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

    fun updateScores() {
        roundScore.plusOne()
        if (null == userSelection) {
            computerScore.plusOne()
            playgroundViewListener?.showWinningAnimation(false)
            return
        }
        when (userSelection?.isWon(computerSelection) ?: -1) {
            0 -> {/* It's DRAW */
            }
            1 -> {
                userScore.plusOne()
                playgroundViewListener?.showWinningAnimation(true)
            }
            -1 -> {
                computerScore.plusOne()
                playgroundViewListener?.showWinningAnimation(false)
            }

        }
    }

    fun resetUserSelection() {
        userSelection = null
    }
}

interface PlaygroundViewListener {
    fun getPlayerImage(): Drawable?
    fun onCountDownTimerChanged(it: ObservableField<Int>)
    fun showWinningAnimation(userWon: Boolean)
}
