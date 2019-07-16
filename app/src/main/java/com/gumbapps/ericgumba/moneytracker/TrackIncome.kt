package com.gumbapps.ericgumba.moneytracker

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
//import com.google.android.gms.ads.AdRequest
//import com.google.android.gms.ads.InterstitialAd
//import com.google.android.gms.ads.MobileAds
import java.math.RoundingMode.*
import java.text.DecimalFormat
import java.util.*
import kotlin.concurrent.timerTask
//import jdk.nashorn.internal.objects.NativeDate.getTime


//TODO: P0 Figure out how to run this in the background of app.

class TrackIncome : AppCompatActivity() {


    lateinit var time: TextView
    lateinit var pauseButtonTracker: PauseButtonTracker
    private lateinit var mInterstitialAd: InterstitialAd
    lateinit var tracker: Tracker
    lateinit var money: TextView
    private var totalSecondsWorked = 0
    private var totalMoneyEarned = 0.00
    private var income = 0.00
    private lateinit var activityState: ActivityState
    lateinit private var timeTracker: TimeTracker
    lateinit private var pauseResumeButton: Button
    lateinit var timer: Timer
    lateinit var runTime: Runnable
    lateinit var timeHandler: Handler

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_income)

        initObjects()

        // set up ads
        MobileAds.initialize(this, "ca-app-pub-9137217174806568~9043276962")

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-9137217174806568/2038884912"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        // set up vars
        income = tracker.getIncome().toDouble()
        money = findViewById(R.id.money)
        time = findViewById(R.id.time)

        pauseResumeButton = findViewById(R.id.pause_resume_button)
        val endWorkButton: Button = findViewById(R.id.end_work)
        timer.schedule(timerTask { timeHandler.post(runTime) },1000, 1000)


        // set listeners

        mInterstitialAd.adListener = object : AdListener() {

            private fun showToast(message: String) {
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            }

            override fun onAdClicked() {
                super.onAdClicked()
            }
            override fun onAdClosed() {
//                val nextIntent = Intent(applicationContext, ExamineEarnings::class.java)
//
//                startActivity(nextIntent)
//                activityState.switchStates(activityState.EXAMINE)
//                timeTracker.reset()
//                pauseButtonTracker.switchPauseButtonStateToResume()
//                finish()
//
                moveToExamineEarnings()

            }

            override fun onAdFailedToLoad(p0: Int) {
                super.onAdFailedToLoad(p0)
                showToast(String.format("Ad failed to load with error code %d.", p0))

                Log.e("themessage", p0.toString())

            }
        }
        pauseResumeButton.setOnClickListener { pauseOrResumeTime() }
        endWorkButton.setOnClickListener { endWork() }

    }

    private fun initObjects(){

        tracker = Tracker(this)
        timeTracker = TimeTracker(this)
        activityState = ActivityState(this)
        pauseButtonTracker = PauseButtonTracker(this)
        timer = Timer("settingUp", false)
        timeHandler =  Handler()
        runTime = Runnable { updateTimer() }


    }

    private fun moveToExamineEarnings(){
        tracker.saveTimeAndMoney(totalSecondsWorked, totalMoneyEarned.toFloat())
        val nextIntent = Intent(applicationContext, ExamineEarnings::class.java)
        startActivity(nextIntent)
        activityState.switchStates(activityState.EXAMINE)
        timeTracker.reset()
        pauseButtonTracker.switchPauseButtonStateToResume()
        finish()
    }


    private fun endWork(){
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        } else {
            moveToExamineEarnings()
        }
    }


    private fun pauseOrResumeTime(){

        if (pauseResumeButton.text.toString().toLowerCase() == "pause"){
            changePauseButton()
        } else {
            changeResumeButton()
        }
    }

    private fun changePauseButton(){
        pauseResumeButton.text = getString(R.string.resume)
        pauseButtonTracker.switchPauseButtonStateToPause()
        timer.cancel()
        pauseResumeButton.setOnClickListener { pauseOrResumeTime() }
    }

    private fun changeResumeButton(){
        pauseResumeButton.text = getString(R.string.pause)
        pauseButtonTracker.switchPauseButtonStateToResume()
        timer = Timer("settingUp", false)

        timer.schedule(timerTask { timeHandler.post(runTime) },1000, 1000)
        pauseResumeButton.setOnClickListener { pauseOrResumeTime() }
    }

    private fun updateTimer() {
        totalSecondsWorked += 1
        totalMoneyEarned = (totalSecondsWorked.toDouble() * (income / 3600.0))
        val df = DecimalFormat("#.##")
        df.roundingMode = CEILING
        val moneys = "$" + tracker.formatMoney(totalMoneyEarned.toFloat())
        time.text = tracker.convertSeconds(totalSecondsWorked)
        money.text = moneys
    }

    override fun onPause() {
        super.onPause()

        val p = pauseButtonTracker.pauseButtonStateAtResume()
        timeTracker.save(totalSecondsWorked)
        timeTracker.pause()


    }

    override fun onStop() {
        super.onStop()
        val p = pauseButtonTracker.pauseButtonStateAtResume()

    }

    override fun onResume() {
        super.onResume()
        val q = pauseButtonTracker.pauseButtonStateAtResume()
        val p = timeTracker.getPauseState()

        if (p && q) {

            val a = timeTracker.currentDate().toInt() / 1000
            val b = timeTracker.savedTimeDate.toInt() / 1000
            val endDate = a - b
            val c = timeTracker.secondsWorked
            totalSecondsWorked = c + (a - b)

        } else if (p && !pauseButtonTracker.pauseButtonStateAtResume()) {
            pauseResumeButton.text = getString(R.string.resume)
            totalSecondsWorked = timeTracker.secondsWorked
            totalSecondsWorked -= 1
            updateTimer()
            timer.cancel()
            pauseResumeButton.setOnClickListener {
                pauseOrResumeTime()
            }
        }

    }
}
