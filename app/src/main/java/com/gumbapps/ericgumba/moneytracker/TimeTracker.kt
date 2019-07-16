package com.gumbapps.ericgumba.moneytracker

import android.content.Context
import android.content.SharedPreferences
import java.util.*

class TimeTracker(context: Context) {
    private val TODAYS_TIME = "TODAYS_TIME"
    private val TIME_WORKED = "TIME_WORKED"
    private val SAVED_TIME_DATE = "SAVED_TIME_DATE"
    private val WAS_PAUSED = "WAS_PAUSED"
    private val time: SharedPreferences = context.getSharedPreferences(TODAYS_TIME, 0) // ??
    private val timeEditor: SharedPreferences.Editor = time.edit()


    val secondsWorked = time.getInt(TIME_WORKED, 0)
    val savedTimeDate = time.getLong(SAVED_TIME_DATE,0)
    val wasPaused = time.getBoolean(WAS_PAUSED, false)

    fun pause(){
        timeEditor.putBoolean(WAS_PAUSED, true)
        timeEditor.apply()
    }

    fun getPauseState(): Boolean{
        return time.getBoolean(WAS_PAUSED, false)
    }

    fun reset(){
        timeEditor.putInt(TIME_WORKED,0)
        timeEditor.putBoolean(WAS_PAUSED, false)
        timeEditor.apply()
    }

    fun savedSeconds():Int{
        return time.getInt(TIME_WORKED, 0)
    }

    fun save(seconds:Int){
        saveTimeWorked(seconds)
        saveDate()
    }

    private fun saveTimeWorked(seconds:Int){
        timeEditor.putInt(TIME_WORKED,seconds)
        timeEditor.apply()
    }

    private fun saveDate(){

        timeEditor.putLong(SAVED_TIME_DATE, Date().time)
        timeEditor.apply()
    }

    fun currentDate():Long{
        return Date().time
    }

}