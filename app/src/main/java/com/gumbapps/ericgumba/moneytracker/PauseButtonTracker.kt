package com.gumbapps.ericgumba.moneytracker

import android.content.Context

class PauseButtonTracker(context: Context) {

    private val PAUSE_BUTTON_TRACKER = "PAUSE_BUTTON_TRACKER"
    private val WAS_AT_RESUME = "WAS_AT_RESUME"
    private val pauseTracker = context.getSharedPreferences(PAUSE_BUTTON_TRACKER, 0)
    private val pauseTrackerEditor = pauseTracker.edit()


    fun pauseButtonStateAtResume(): Boolean{
        return pauseTracker.getBoolean(WAS_AT_RESUME, true)
    }
    fun switchPauseButtonStateToPause(){

        val x = pauseTracker.getBoolean(WAS_AT_RESUME, true)
        pauseTrackerEditor.putBoolean(WAS_AT_RESUME, false)
        pauseTrackerEditor.apply()
    }

    fun switchPauseButtonStateToResume(){
        pauseTrackerEditor.putBoolean(WAS_AT_RESUME, true)
        pauseTrackerEditor.apply()
    }


}