package com.example.analogueclock.activity


import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.analogueclock.databinding.ActivityClockBinding
import com.example.analogueclock.utility.ClockSurfaceView

class ClockActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClockBinding
    private var clockSurfaceView: ClockSurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityClockBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Analogue Clock"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#F57C00")))

        binding.timer.setOnClickListener {
            val myIntent = Intent(it.context, TimerActivity::class.java)
            startActivity(myIntent)
        }
        binding.settings.setOnClickListener {
            val myIntent = Intent(it.context, SettingsActivity::class.java)
            startActivity(myIntent)
        }
        clockSurfaceView = ClockSurfaceView(this, 400F)

        binding.clockView.addView(clockSurfaceView)
    }

    override fun onResume() {
        super.onResume()
        clockSurfaceView?.onResumeClock()
    }

    override fun onPause() {
        super.onPause()
        clockSurfaceView?.onPauseClock()
    }
}