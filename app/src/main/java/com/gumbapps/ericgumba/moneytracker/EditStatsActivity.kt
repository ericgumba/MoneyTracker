package com.gumbapps.ericgumba.moneytracker

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.widget.Button

class EditStatsActivity : AppCompatActivity() {

    lateinit var changeHourly: Button
    lateinit var changeTimeMoneyStats: Button
    lateinit var back: Button
    lateinit var activityState: ActivityState
    lateinit var tracker: Tracker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_stats)

        tracker = Tracker(this)
        activityState = ActivityState(this)
        changeHourly = findViewById(R.id.change_income)
        changeTimeMoneyStats = findViewById(R.id.change_time_money)

        back = findViewById(R.id.back)
        changeHourly.setOnClickListener {
            activityState.switchStates(activityState.SET_INCOME)
            val intent = Intent(this, SetIncome::class.java)
            startActivity(intent)
            finish()
        }

        changeTimeMoneyStats.setOnClickListener {
            val intent = Intent(this, EditTimeMoneyForDayActivity::class.java)
            startActivity(intent)
            finish()
        }
        back.setOnClickListener {
            startActivity(Intent(this, ExamineEarnings::class.java))
            finish()
        }
    }

}
