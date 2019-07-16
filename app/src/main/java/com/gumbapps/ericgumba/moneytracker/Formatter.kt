package com.gumbapps.ericgumba.moneytracker

import android.content.Context
import java.math.RoundingMode
import java.text.DecimalFormat

class Formatter(context: Context) {
    fun formatMoney(money: Float): String {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(money)
    }
    fun convertSeconds(totalSeconds:Int): String{
        val hours = (totalSeconds / 3600)
        val minutes = (totalSeconds / 60) % 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}