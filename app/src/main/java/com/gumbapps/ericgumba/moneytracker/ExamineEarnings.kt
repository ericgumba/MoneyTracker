package com.gumbapps.ericgumba.moneytracker

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class ExamineEarnings : AppCompatActivity() {

    lateinit var tracker: Tracker

    var daysAgo = 0

    lateinit var timeTracker: TimeTracker
    lateinit var activityState: ActivityState
    lateinit var pauseButtonTracker: PauseButtonTracker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_examine_earnings)

        initObjects()

        val lifeTimeWorkedAndEarnings = getSharedPreferences("lifeTimeWorkAndEarnings",0)

        val totalTimeTextView: TextView = findViewById(R.id.total_time_worked)
        val totalMoneyTextView: TextView = findViewById(R.id.total_earnings)
        val dateTextView: TextView = findViewById(R.id.date)
        val timeForSpecificDayTextView: TextView = findViewById(R.id.time_worked_for_specific_day)
        val moneyForSpecificDayTextView: TextView = findViewById(R.id.money_earned_for_specific_day)
        val prevButton: Button = findViewById(R.id.prev)
        val nextButton: Button = findViewById(R.id.next)
        val settingsButton: Button = findViewById(R.id.settings)
        val startWorkButton: Button = findViewById(R.id.start_work)
        val totalMoney = "$" + tracker.formatMoney(lifeTimeWorkedAndEarnings.getFloat("lifeTimeEarnings",0.0.toFloat()))
        val todaysMoney = "$" + tracker.formatMoney(tracker.getMoney(daysAgo))
        pauseButtonTracker = PauseButtonTracker(this)
        dateTextView.text = tracker.getDate(daysAgo)
        totalMoneyTextView.text = totalMoney
        totalTimeTextView.text = tracker.convertSeconds(lifeTimeWorkedAndEarnings.getInt("lifeTimeWorked", 0))
        timeForSpecificDayTextView.text = tracker.convertSeconds(tracker.getTime(daysAgo))
        moneyForSpecificDayTextView.text = todaysMoney

        startWorkButton.setOnClickListener {
            val intent = Intent(this, TrackIncome::class.java)
            startActivity(intent)

            timeTracker.reset()

            pauseButtonTracker.switchPauseButtonStateToResume()
            activityState.switchStates(activityState.TRACK_INCOME)
            finish()
        }
        settingsButton.setOnClickListener {
            val intent = Intent(this, EditStatsActivity::class.java)
            startActivity(intent)
            finish()

        }


        prevButton.setOnClickListener {
            daysAgo += 1
            val moneys = "$" + tracker.formatMoney(tracker.getMoney(daysAgo))
            dateTextView.text = tracker.getDate(daysAgo)
            timeForSpecificDayTextView.text = tracker.convertSeconds(tracker.getTime(daysAgo))
            moneyForSpecificDayTextView.text = moneys

        }
        nextButton.setOnClickListener {
            daysAgo -= 1
            val moneys = "$" + tracker.formatMoney(tracker.getMoney(daysAgo))
            dateTextView.text = tracker.getDate(daysAgo)
            timeForSpecificDayTextView.text = tracker.convertSeconds(tracker.getTime(daysAgo))
            moneyForSpecificDayTextView.text = moneys
        }


    }

    private fun initObjects(){

        tracker = Tracker(this)
        timeTracker = TimeTracker(this)
        activityState = ActivityState(this)
    }

    private fun finishTrackIncome(){

        val intent = Intent(this, TrackIncome::class.java)
        startActivity(intent)
        finish()
    }
    private fun showToast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
