package com.carsales.covid.Functions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

class OwnFunctions {

    //fix date from calendar
    fun fixDateCalendar(y: Int, m: Int, d: Int) : String{

        val new_month = if (m < 10) "0"+(m+1).toString() else (m+1).toString()
        val new_day = if (d < 10) "0"+(d+1).toString() else (d+1).toString()

        return "$y-$new_month-$new_day"
    }

    //animation visible - gone
    fun animationVisibleGone(view: View, visibility: Int, alpha: Float, duration: Long) {
        view.animate()
            .alpha(alpha)
            .setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    view.visibility = visibility
                }
            })
    }
}