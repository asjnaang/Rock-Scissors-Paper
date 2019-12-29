package com.example.rsp.helper

import android.content.Context
import android.util.TypedValue
import com.example.rsp.model.Player
import kotlin.random.Random


object Utils {

    fun getRandomPlayer(): Player {
        return Player.values()[Random.nextInt(1, 100) % 3]
    }

    fun convertDpToPixel(dp: Float, context: Context): Float {
        return (dp * context.resources.displayMetrics.densityDpi.toFloat()) / context.resources.displayMetrics.density
    }

    fun getPixelsByWidth(id: Int, context: Context): Float {
        return context.resources.displayMetrics.widthPixels / getDimen(id, context)
    }

    fun getPixelsByHeight(id: Int, context: Context): Float {
        return context.resources.displayMetrics.heightPixels / getDimen(id, context)
    }

    private fun getDimen(id: Int, context: Context): Float {
        val tempVal = TypedValue()
        context.resources.getValue(id, tempVal, true)
        return tempVal.float
    }

}