package com.gumbapps.ericgumba.moneytracker

import android.content.Context

class TotalTimeAndMoney(val context: Context) {
    val mPrefs = context.getSharedPreferences("lifeTimeWorkAndEarnings", 0)
    val lifeTimeEarnings:Float = mPrefs.getFloat("lifeTimeEarnings", 0.0.toFloat())
    val lifeTimeWorked:Int = mPrefs.getInt("lifeTimeWorked", 0 )

}