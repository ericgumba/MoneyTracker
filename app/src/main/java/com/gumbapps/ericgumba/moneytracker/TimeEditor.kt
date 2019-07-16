package com.gumbapps.ericgumba.moneytracker

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*

class TimeEditor(context: Context) {
    val timeAndMoneyForASpecificDay = TimeAndMoneyForASpecificDay(context)
    val time: SharedPreferences = context.getSharedPreferences("time",0)
    private val timeEditor: SharedPreferences.Editor = time.edit()
    val mPrefs = context.getSharedPreferences("lifeTimeWorkAndEarnings", 0)

    private fun obtainDate(daysAgo: Int): String{
        val df = SimpleDateFormat("MMM d, yyyy")
        return df.format(getDaysAgo(daysAgo))
    }

    private fun getDaysAgo(daysAgo: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)
        return calendar.time
    }

    fun edit(daysAgo:Int, newTime:Int) {
        val oldTime = time.getInt(obtainDate(daysAgo), 0)
        val differenceTime = newTime - oldTime
        timeEditor.putInt(obtainDate(daysAgo), newTime)
        timeEditor.apply()
        saveNewTotal(differenceTime)
    }

    private fun saveNewTotal(differenceTime:Int) {
        var t = mPrefs.getInt("lifeTimeWorked",0)
        timeAndMoneyForASpecificDay.saveTotalTimeWorkedAndMoneyEarned(differenceTime,0.toFloat())
        t = mPrefs.getInt("lifeTimeWorked",0)

    }
}