package com.example.rsp.ui.main.screens.playground

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.rsp.AppConstants
import com.example.rsp.R
import com.example.rsp.databinding.PlaygroundFragmentBinding
import com.example.rsp.helper.NavigationListener
import com.example.rsp.helper.Utils
import com.example.rsp.helper.addOnPropertyChanged
import com.example.rsp.helper.filterNull
import kotlinx.android.synthetic.main.mode_play.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlaygroundFragment : Fragment(), NavigationListener, PlaygroundViewListener {
    companion object {
        fun newInstance() = PlaygroundFragment()
    }

    private var selectedMode = 0
    private var timer = 3
    private val viewModel: PlaygroundViewModel by viewModel()
    private lateinit var binding: PlaygroundFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PlaygroundFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
        viewModel.navigationListener = this
        viewModel.playgroundViewListener = this
        selectedMode = arguments?.getInt(AppConstants.SELECTED_MODE).filterNull()
        viewModel.isCountDownTimerEnabled.addOnPropertyChanged {
            if (it.get() == View.VISIBLE) {
                countdown_tv.text = timer.toString()
                startCountDownAnimation()
            }
        }

    }

    private fun startCountDownAnimation() {
        val animation = AnimationUtils.loadAnimation(activity, R.anim.scale_up)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {}
            override fun onAnimationEnd(p0: Animation?) {
                if (timer == 0) return
                timer--
                startCountDownAnimation()
            }
            override fun onAnimationStart(p0: Animation?) {
                if (timer == 0) {
                    endAnimation()
                    triggerGameSelection()
                } else {
                    countdown_tv.text = timer.toString()
                }
            }
        })
        countdown_tv.startAnimation(animation)
    }

    private fun triggerGameSelection() {
        if (selectedMode == AppConstants.COMPUTER_VS_COMPUTER) {
            triggerComputerSelection()
            triggerAutoUserSelection()
        } else {
            triggerComputerSelection()
        }
    }

    private fun triggerAutoUserSelection() {
        val random = Utils.getRandomPlayer()
        binding.root.findViewById<ConstraintLayout>(R.id.bottom_rsp_images).findViewWithTag<ImageView>(random.name).performClick()
    }

    private fun endAnimation() {
        countdown_tv.text = getString(R.string.go)
        countdown_tv.clearAnimation()
        countdown.background = activity?.getDrawable(R.drawable.dark_ring)
        Handler().postDelayed({
            viewModel.updatePlayModeEnabled()
        }, 2000)
    }

    private fun triggerComputerSelection() {
        val random = Utils.getRandomPlayer()
        binding.root.findViewById<ConstraintLayout>(R.id.top_rsp_images).findViewWithTag<ImageView>(random.name).performClick()
    }

    override fun goBack() {
        fragmentManager?.popBackStackImmediate()
    }

    override fun getPlayerImage(): Drawable? {
        return if (selectedMode == AppConstants.HUMAN_VS_COMPUTER) {
            activity?.getDrawable(R.mipmap.human)
        } else {
            activity?.getDrawable(R.mipmap.computer)
        }
    }

}
