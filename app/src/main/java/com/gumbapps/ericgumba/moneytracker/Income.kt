package com.gumbapps.ericgumba.moneytracker

import android.content.Context

class Income(context: Context) {

    val incomePreference = context.getSharedPreferences("income", 0)
    val incomeEditor = incomePreference.edit()


    val canChange = incomePreference.getBoolean("allowForChange", true)
    fun setIncome(newIncome:Float){
        incomeEditor.putFloat("income",newIncome)
        incomeEditor.apply()
    }
    fun getIncome():Float{
        return incomePreference.getFloat("income", 0.toFloat())
    }
    fun allowForChange(){
        incomeEditor.putBoolean("allowForChange", true)
        incomeEditor.apply()
    }
    fun disallowForChange(){
        incomeEditor.putBoolean("allowForChange", false)
        incomeEditor.apply()
    }

}