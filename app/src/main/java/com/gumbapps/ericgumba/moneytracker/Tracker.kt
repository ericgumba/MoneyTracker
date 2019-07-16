package com.gumbapps.ericgumba.moneytracker

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

// this does not use dependency inversion
class Tracker (val context: Context ) {

    private val income = Income(context)
    private val totalTimeAndMoney =  TotalTimeAndMoney(context)
    private val timeAndMoneyForASpecificDay = TimeAndMoneyForASpecificDay(context)
    private val formatter = Formatter(context)

    fun getIncome(): Float{
        return income.getIncome()
    }
    fun setIncome(newIncome:Float){
        income.setIncome(newIncome)
    }
    fun getTime(daysAgo: Int): Int{
        return timeAndMoneyForASpecificDay.getTime(daysAgo)
    }
    fun getMoney(daysAgo: Int): Float{
        return timeAndMoneyForASpecificDay.getMoney(daysAgo)
    }
    fun getTotalTime():Int{
        return totalTimeAndMoney.lifeTimeWorked
    }

    fun getTotalMoney():Float{
        return totalTimeAndMoney.lifeTimeEarnings
    }
    fun saveTimeAndMoney(timeWorked: Int, moneyEarned: Float){
        timeAndMoneyForASpecificDay.saveTimeWorkedAndMoneyEarned(timeWorked, moneyEarned)
    }
    fun formatMoney(money: Float): String{
        return formatter.formatMoney(money)
    }
    fun convertSeconds(totalSeconds:Int): String{
        return formatter.convertSeconds(totalSeconds)
    }



    fun calculateOverTime(daysAgo: Int):Float{
        val timeWorked = getTime(daysAgo)
        val moneyEarned = getMoney(daysAgo)
        return calculateOverTimeHelper(timeWorked, moneyEarned)
    }

    fun calculateOverTimeHelper(timeWorked: Int, moneyEarned: Float):Float{
        val otLowerLimit = 28800
        val overtime = timeWorked - otLowerLimit
        val otMultiplier = 1.5
        val moneyRatio = moneyEarned / timeWorked.toFloat()

        val overtimeMoneyRatio = moneyRatio*otMultiplier.toFloat()

        return if (timeWorked <= otLowerLimit){
            0.toFloat()
        } else {
            overtime.toFloat() * overtimeMoneyRatio
        }
    }

    // TODO P0: test this function

    fun calculateDoubleTimeAndOvertime(timeWorked: Int, moneyEarned: Float):Float{
        val otLowerLimit = 28800
        val dtLowerLimit = 43200
        var overtime = timeWorked - otLowerLimit
        if (overtime > 14400){
            overtime = 14400
        }
        if (overtime < 0){
            overtime = 0
        }
        var doubleTime = timeWorked - dtLowerLimit
        if (doubleTime < 0){
            doubleTime = 0
        }
        val otMultiplier = 1.5
        val dtMultiplier = 2.0
        val moneyRatio = moneyEarned / (timeWorked.toFloat() + 0.01)

        val overtimeMoneyRatio = moneyRatio*otMultiplier.toFloat()
        val doubletimeMoneyRatio = moneyRatio*dtMultiplier.toFloat()


        return if (timeWorked <= otLowerLimit){
            moneyEarned
        } else {
            val regularMoney = otLowerLimit.toFloat()*moneyRatio
            val dtMoney = doubleTime.toFloat() * doubletimeMoneyRatio
            val otMoney = overtime.toFloat() * overtimeMoneyRatio

            (regularMoney + dtMoney + otMoney).toFloat()
        }
    }

    // TODO: Refactor this into a date class
    fun getDate(daysAgo: Int):String{
        return obtainDate(daysAgo)
    }

    private fun obtainDate(daysAgo: Int): String{
        val df = SimpleDateFormat("MMM d, yyyy")
        return df.format(getDaysAgo(daysAgo))
    }

    private fun getDaysAgo(daysAgo: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)

        return calendar.time
    }
    fun allowForChange(){
        income.allowForChange()
    }
    fun canChangeIncome():Boolean{
        return income.canChange
    }
    fun disallowForChange(){
        income.disallowForChange()
    }

}