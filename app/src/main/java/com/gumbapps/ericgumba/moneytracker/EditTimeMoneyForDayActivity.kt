package com.gumbapps.ericgumba.moneytracker

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView

import android.text.SpannableStringBuilder
import android.widget.Button
import android.text.InputFilter




// TODO: P0 Implement submit and back button

class EditTimeMoneyForDayActivity : AppCompatActivity() {

    lateinit var tracker: Tracker
    lateinit var moneyEditor: MoneyEditor
    lateinit var timeEditor: TimeEditor
    lateinit var formatter: Formatter
    lateinit var moneyInput: TextInputEditText
    lateinit var hoursInput: TextInputEditText
    lateinit var hours: TextView
    lateinit var minutes: TextView
    lateinit var money: TextView
    lateinit var prevButton: Button
    lateinit var nextButton: Button
    lateinit var backButton: Button
    lateinit var submitButton: Button
    lateinit var includeOvertimeButton: Button
    lateinit var dateTextView: TextView
    lateinit var minutesInput: TextInputEditText
    var daysAgo = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_time_money_for_day)

        tracker = Tracker(this)
        timeEditor = TimeEditor(this)
        moneyEditor = MoneyEditor(this)
        formatter = Formatter(this)

        moneyInput = findViewById(R.id.new_money_earned)
        hoursInput = findViewById(R.id.new_hours_worked)
        hours = findViewById(R.id.hours_worked)
        minutes = findViewById(R.id.minutes_worked)
        money = findViewById(R.id.money)

        minutesInput = findViewById(R.id.new_minutes_worked)
        dateTextView = findViewById(R.id.date)

        prevButton = findViewById(R.id.prev)
        nextButton = findViewById(R.id.next)

        submitButton = findViewById(R.id.submit)
        backButton = findViewById(R.id.back)
        includeOvertimeButton = findViewById(R.id.overtime)

        setTexts()

        minutesInput.filters = arrayOf<InputFilter>(InputFilterMinMax("0", "59"))
        hoursInput.filters = arrayOf<InputFilter>(InputFilterMinMax("0", "24"))

        prevButton.setOnClickListener {
            daysAgo += 1
            setTexts()
        }
        nextButton.setOnClickListener {
            daysAgo -= 1
            setTexts()

        }

        includeOvertimeButton.setOnClickListener {
            val hoursToSeconds = hoursText()
            val minutesToSeconds = minutesText()
            val moneyEarned = moneyText()

            val overtimeMoney = tracker.
                calculateDoubleTimeAndOvertime(hoursToSeconds + minutesToSeconds,
                   moneyEarned )

            moneyInput.text = SpannableStringBuilder(( overtimeMoney
                     ).toString())
        }

        submitButton.setOnClickListener {
            moneyEditor.edit(daysAgo, moneyToFloat())
            timeEditor.edit(daysAgo, hoursToSeconds()+minutesToSeconds())
            val intent = Intent(this, ExamineEarnings::class.java)
            startActivity(intent)
            finish()
        }

        backButton.setOnClickListener {
            val intent = Intent(this, ExamineEarnings::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setTexts(){
        money.text = SpannableStringBuilder(tracker.getMoney(daysAgo).toString())
        hours.text = (tracker.getTime(daysAgo) / 3600).toString()
        minutes.text = ((tracker.getTime(daysAgo) / 60) % 60).toString()
        moneyInput.text = SpannableStringBuilder(tracker.getMoney(daysAgo).toString())
        hoursInput.text = SpannableStringBuilder( (tracker.getTime(daysAgo) / 3600).toString())
        minutesInput.text =  SpannableStringBuilder( ((tracker.getTime(daysAgo) / 60) % 60).toString())
        dateTextView.text = tracker.getDate(daysAgo)

    }
    private fun moneyToFloat():Float{
        return if (moneyInput.text!!.isBlank()){
            0.toFloat()
        } else{
            moneyInput.text.toString().toFloat()
        }
    }

    private fun hoursText():Int{
        return if (hours.text!!.isBlank()){
            0
        } else{
            hours.text.toString().toInt() * 3600
        }

    }
    private fun minutesText(): Int{

        return if (minutes.text!!.isBlank()){
            0
        } else{
            minutes.text.toString().toInt() * 60
        }
    }

    private fun moneyText():Float{
        return if (moneyInput.text!!.isBlank()){
            0.toFloat()
        } else{
            moneyInput.text.toString().toFloat()
        }
    }

    private fun hoursToSeconds(): Int{
        return if (hoursInput.text!!.isBlank()){
            0
        } else{
            hoursInput.text.toString().toInt() * 3600
        }
    }

    private fun minutesToSeconds(): Int{

        return if (minutesInput.text!!.isBlank()){
            0
        } else{
            minutesInput.text.toString().toInt() * 60
        }
    }

}
