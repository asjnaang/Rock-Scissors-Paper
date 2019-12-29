package com.example.rsp.ui.main.screens.playground

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.example.rsp.R
import com.example.rsp.helper.Utils
import com.example.rsp.helper.addOnPropertyChanged
import com.example.rsp.helper.plusOne
import com.example.rsp.model.BaseViewModel
import com.example.rsp.model.Player

class PlaygroundViewModel(private val application: Application) : BaseViewModel(application) {
    lateinit var playgroundViewListener: PlaygroundViewListener
    val isPlayModeEnabled: ObservableField<Int> = ObservableField(View.VISIBLE)
    val isCountDownTimerEnabled: ObservableField<Int> = ObservableField(View.GONE)
    val userScore: ObservableInt = ObservableInt(0)
    var roundScore: ObservableInt = ObservableInt(0)
    var computerScore: ObservableInt = ObservableInt(0)
    val isUserClickEnabled: ObservableBoolean = ObservableBoolean(false)
    val isComputerClickEnabled: ObservableBoolean = ObservableBoolean(false)
    private var userSelection: Player? = null
    private var computerSelection: Player = Player.ROCK

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
        view.animate().translationXBy(-1 * Utils.getPixelsByWidth(R.dimen.scaleXBy, application.applicationContext))
    }

    private fun animateXPositive(view: View) {
        view.animate().translationXBy(Utils.getPixelsByWidth(R.dimen.scaleXBy, application.applicationContext))
    }

    private fun animateY(view: View, isUserClicked: Boolean, isForward: Boolean = true) {
        val scaleYByValue = Utils.getPixelsByHeight(R.dimen.scaleYBy, application.applicationContext)
        val animationLength = if (isUserClicked) -1 * scaleYByValue else scaleYByValue
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
            playgroundViewListener?.showWinningAnimation(-1)
            Toast.makeText(
                application.applicationContext,
                application.applicationContext?.getString(R.string.choose_wisely),
                Toast.LENGTH_LONG
            ).show()
            return
        }
        when (val result = userSelection?.isWon(computerSelection)) {
            0 -> {/* It's DRAW */
                playgroundViewListener?.showWinningAnimation(result)
            }
            1 -> {
                userScore.plusOne()
                playgroundViewListener?.showWinningAnimation(result)
            }
            -1 -> {
                computerScore.plusOne()
                playgroundViewListener?.showWinningAnimation(result)
            }

        }
    }

    fun resetUserSelection() {
        userSelection = null
    }
}

interface PlaygroundViewListener {
    fun getPlayerImage(): Drawable?
    fun showWinningAnimation(result: Int)
}
