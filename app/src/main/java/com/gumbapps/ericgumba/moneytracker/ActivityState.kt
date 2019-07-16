package com.gumbapps.ericgumba.moneytracker

import android.content.Context

class ActivityState(context: Context) {

    val EXAMINE = "EXAMINE"
    val SET_INCOME = "SET_INCOME"
    val TRACK_INCOME = "TRACK_INCOME"
    val ACTIVITY_EXAMINER = "ACTIVITY_EXAMINER"


    val state = context.getSharedPreferences(ACTIVITY_EXAMINER, 0)
    val stateEditor = state.edit()

    fun switchStates(newState: String){

        when {
            newState.equals(EXAMINE) -> stateEditor.putString(ACTIVITY_EXAMINER, EXAMINE)
            newState.equals(SET_INCOME) -> stateEditor.putString(ACTIVITY_EXAMINER, SET_INCOME)
            newState.equals(TRACK_INCOME) -> stateEditor.putString(ACTIVITY_EXAMINER, TRACK_INCOME)
            else -> stateEditor.putString(ACTIVITY_EXAMINER, SET_INCOME)
        }

        stateEditor.apply()
    }

    fun getState():String{
        return state.getString(ACTIVITY_EXAMINER, SET_INCOME)
    }

}