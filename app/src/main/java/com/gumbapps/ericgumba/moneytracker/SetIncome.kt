package com.gumbapps.ericgumba.moneytracker

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.widget.Button

class SetIncome : AppCompatActivity() {
    lateinit var incomeInput: TextInputEditText
    lateinit var tracker: Tracker
    lateinit var income: Income
    lateinit var timeTracker: TimeTracker
    lateinit var pauseButtonTracker: PauseButtonTracker
    lateinit var activityState: ActivityState

//    startService(new Intent(this, MyService.class));
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_income)
        val confirmButton: Button = this.findViewById(R.id.confirmButton)
        tracker = Tracker(this)
        timeTracker = TimeTracker(this)
        activityState = ActivityState(this)
        pauseButtonTracker = PauseButtonTracker(this)
         determineActivity()

        confirmButton.setOnClickListener{

            if (!incomeInput.text.isNullOrBlank() ) {
                moveToTrackEarningsActivity()
            }
        }

        incomeInput = findViewById(R.id.incomeInput)

    }

    private fun determineActivity(){
        if (activityState.getState() == activityState.EXAMINE){
            moveToExamineEarningsActivity()
        } else if (activityState.getState() == activityState.TRACK_INCOME){
            forceMoveToTrackEarningsActivity()
        }
    }

    private fun moveToExamineEarningsActivity(){

        val intentTwo = Intent(this, ExamineEarnings::class.java)
        startActivity(intentTwo)
        activityState.switchStates(activityState.EXAMINE)
        finish()

    }
    private fun forceMoveToTrackEarningsActivity(){
        val intent = Intent(this, TrackIncome::class.java)
        startActivity(intent)
        activityState.switchStates(activityState.TRACK_INCOME)
        finish()

    }

    private fun moveToTrackEarningsActivity() {
        val intent = Intent(this, TrackIncome::class.java)
            tracker.setIncome(incomeInput.text.toString().toFloat())
            tracker.disallowForChange()
            timeTracker.reset()
            pauseButtonTracker.switchPauseButtonStateToResume()
            startActivity(intent)
            activityState.switchStates(activityState.TRACK_INCOME)
            finish()
    }
}
