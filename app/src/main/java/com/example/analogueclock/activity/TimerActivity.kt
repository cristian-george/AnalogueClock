package com.example.analogueclock.activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.analogueclock.databinding.ActivityTimerBinding
import com.example.analogueclock.utility.TimerSurfaceView

class TimerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimerBinding
    private var timerSurfaceView: TimerSurfaceView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Timer"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#F57C00")))

        binding.setTimer.setOnClickListener {
            val secs = binding.editText.text.toString().toFloat()
            timerSurfaceView?.onPauseTimer()
            timerSurfaceView?.setSeconds(secs)
            timerSurfaceView?.onResumeTimer()

            binding.setTimer.isEnabled = false
        }

        binding.resetTimer.setOnClickListener {
            timerSurfaceView?.onPauseTimer()
            timerSurfaceView?.setSeconds(0F)
            timerSurfaceView?.onResumeTimer()

            binding.setTimer.isEnabled = true
        }

        timerSurfaceView = TimerSurfaceView(this, 400F, 0F)

        binding.timerView.addView(timerSurfaceView)
    }

    override fun onResume() {
        super.onResume()
        timerSurfaceView?.onResumeTimer()
    }

    override fun onPause() {
        super.onPause()
        timerSurfaceView?.onPauseTimer()
    }
}