package com.gumbapps.ericgumba.moneytracker

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*

class MoneyEditor(context: Context) {
    val timeAndMoneyForASpecificDay = TimeAndMoneyForASpecificDay(context)
    val tracker = Tracker(context)
    val money:SharedPreferences = context.getSharedPreferences("money",0)
    private val moneyEditor: SharedPreferences.Editor = money.edit()
    val mPrefs = context.getSharedPreferences("lifeTimeWorkAndEarnings", 0)
    val mEditor = mPrefs.edit()
    private fun obtainDate(daysAgo: Int): String{
        val df = SimpleDateFormat("MMM d, yyyy")
        return df.format(getDaysAgo(daysAgo))
    }

    private fun getDaysAgo(daysAgo: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)
        return calendar.time
    }

    fun edit(daysAgo:Int, newMoney:Float) {
        val oldMoney = money.getFloat(obtainDate(daysAgo), 0.0.toFloat())
        val differenceMoney = newMoney - oldMoney
        moneyEditor.putFloat(obtainDate(daysAgo), newMoney)
        moneyEditor.apply()
        saveNewTotal(differenceMoney)
    }

    private fun saveNewTotal(differenceMoney:Float) {
        var m = mPrefs.getFloat("lifeTimeEarnings",0.toFloat())
        timeAndMoneyForASpecificDay.saveTotalTimeWorkedAndMoneyEarned(0,differenceMoney)
        m = mPrefs.getFloat("lifeTimeEarnings",0.toFloat())
    }
}