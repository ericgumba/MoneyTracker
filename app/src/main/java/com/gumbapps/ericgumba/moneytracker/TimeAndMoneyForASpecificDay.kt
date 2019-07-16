package com.gumbapps.ericgumba.moneytracker

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*


// theoretically

// intent (key = today's date, value = money)
// intent2 (key = ^          , value = time
class TimeAndMoneyForASpecificDay (val context: Context) {

    val time:SharedPreferences = context.getSharedPreferences("time", 0) // ??
    val money:SharedPreferences = context.getSharedPreferences("money",0)
    private val timeEditor:SharedPreferences.Editor = time.edit()
    private val moneyEditor:SharedPreferences.Editor = money.edit()


    // TODO: Refactor this into a date class

    private fun obtainDate(daysAgo: Int): String{
        val df = SimpleDateFormat("MMM d, yyyy")
        return df.format(getDaysAgo(daysAgo))
    }

    private fun getDaysAgo(daysAgo: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)

        return calendar.time
    }

    fun getTime(daysAgo: Int):Int{
        return time.getInt(obtainDate(daysAgo),0)
    }
    fun getMoney(daysAgo: Int): Float{
        return money.getFloat(obtainDate(daysAgo),0.toFloat())
    }


     fun saveTotalTimeWorkedAndMoneyEarned(timeWorked: Int, moneyEarned: Float){

        val mPrefs = context.getSharedPreferences("lifeTimeWorkAndEarnings", 0)
        val lifeTimeEarnings = mPrefs.getFloat("lifeTimeEarnings", 0.0.toFloat())
        val lifeTimeWorked = mPrefs.getInt("lifeTimeWorked", 0)
        val mEditor = mPrefs.edit()

        mEditor.putFloat( "lifeTimeEarnings", lifeTimeEarnings + moneyEarned )
        mEditor.putInt( "lifeTimeWorked", lifeTimeWorked + timeWorked )
        mEditor.apply()

    }
    fun saveTimeWorkedAndMoneyEarned(timeWorked: Int, moneyEarned: Float) {

        val current = obtainDate(0)
        val currentTimeWorked = time.getInt(current, 0)
        val currentMoneyEarned = money.getFloat(current, 0.toFloat())

        timeEditor.putInt(current, currentTimeWorked + timeWorked)
        moneyEditor.putFloat(current, currentMoneyEarned + moneyEarned)
        timeEditor.apply()
        moneyEditor.apply()
        saveTotalTimeWorkedAndMoneyEarned(timeWorked, moneyEarned)
    }
}