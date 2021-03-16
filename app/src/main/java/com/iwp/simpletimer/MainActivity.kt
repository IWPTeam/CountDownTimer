package com.iwp.simpletimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.iwp.simpletimer.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.timer
import kotlin.math.min
//ViewBinding
lateinit var binding: ActivityMainBinding
//if timer active this variable will be true, else false
var isRun = false
//just for easy-access map names
const val seconds = "seconds"
const val minutes = "minutes"
const val hours = "hours"
//time(in seconds) for timer
var timerSeconds=3690
lateinit var timer:CountDownTimer
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun goOrStopTimer(view: View) {
        if (isRun) {
            timer.cancel()
            binding.timeTv.text="00:00:00"
            binding.goOrStopTimer.text="start"
            isRun=false
        } else {
            //start or resume timer
            timer=object : CountDownTimer(secToMSec(timerSeconds), secToMSec(1)) {
                override fun onTick(millisUntilFinished: Long) {
                    val time = getNormalTime(mSecToSec(millisUntilFinished))
                    val dateString = String.format(
                        Locale.getDefault(),
                        "%02d:%02d:%02d",
                        time[hours],
                        time[minutes],
                        time[seconds]
                    )
                    binding.timeTv.text = dateString
                    timerSeconds--
                }

                override fun onFinish() {
                    binding.timeTv.text = "Time is down"
                }

            }.start()
            binding.goOrStopTimer.text="stop"
            isRun=true
        }
    }

    fun mSecToSec(mSec: Long) = mSec.toInt() / 1000
    fun secToMSec(sec: Int) = (sec * 1000).toLong()
    fun getNormalTime(sec: Int): Map<String, Int> {
        return mapOf(
            hours to sec / 3600,
            minutes to (sec % 3600) / 60,
            seconds to sec % 60
        )
    }
}