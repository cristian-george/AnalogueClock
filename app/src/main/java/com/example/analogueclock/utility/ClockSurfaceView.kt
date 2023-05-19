package com.example.analogueclock.utility

import android.annotation.SuppressLint
import android.content.Context

import android.view.SurfaceView
import android.view.SurfaceHolder
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Paint
import java.lang.IllegalArgumentException
import java.util.*

@SuppressLint("ViewConstructor")
class ClockSurfaceView(context: Context?, private val length: Float) : SurfaceView(context),
    Runnable {
    private var thread: Thread? = null
    private var surfaceHolder: SurfaceHolder? = null
    private var isRunning = false

    fun onResumeClock() {
        isRunning = true
        thread = Thread(this)
        thread!!.start()
    }

    fun onPauseClock() {
        var retry = true
        isRunning = false
        while (retry) {
            try {
                thread!!.join()
                retry = false
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    override fun run() {
        var hour: Int
        var min: Int
        var sec: Int
        while (isRunning) {
            if (!surfaceHolder!!.surface.isValid) {
                continue
            }
            val canvas = surfaceHolder!!.lockCanvas()
            val paint = Paint().apply { color = Color.parseColor("#FFE0B2") }
            canvas.drawPaint(paint)

            val pHr = Paint()
            pHr.strokeWidth = 15f
            val pSec = Paint()
            pSec.strokeWidth = 8f
            val pMin = Paint()
            pMin.strokeWidth = 10f
            paint.textSize = 45f
            paint.strokeWidth = 2f
            var sp: SharedPreferences? = null
            if (super.getContext().getSharedPreferences("myPref", Context.MODE_PRIVATE) != null) {
                sp = super.getContext().getSharedPreferences("myPref", Context.MODE_PRIVATE)
            }
            val editor =
                super.getContext().getSharedPreferences("myPref", Context.MODE_PRIVATE).edit()
            if (sp != null) {
                if (!sp.contains("hourColor")) {
                    editor.putString("hourColor", "#303F9F")
                    editor.apply()
                }
                if (!sp.contains("minutesColor")) {
                    editor.putString("minutesColor", "#FF4081")
                    editor.apply()
                }
                if (!sp.contains("secondsColor")) {
                    editor.putString("secondsColor", "#5D6CBC")
                    editor.apply()
                }
                try {
                    pHr.color = Color.parseColor(sp.getString("hourColor", "#303F9F"))
                } catch (e: IllegalArgumentException) {
                    pHr.color = sp.getString("hourColor", "#303F9F")!!.toInt()
                }
                try {
                    pMin.color = Color.parseColor(sp.getString("minutesColor", "#FF4081"))
                } catch (e: IllegalArgumentException) {
                    pMin.color = sp.getString("minutesColor", "#FF4081")!!.toInt()
                }
                try {
                    pSec.color = Color.parseColor(sp.getString("secondsColor", "#5D6CBC"))
                } catch (e: IllegalArgumentException) {
                    pSec.color = sp.getString("secondsColor", "#5D6CBC")!!.toInt()
                }
            }
            paint.color = Color.parseColor("#303F9F")
            val secMarks =
                RegPoly(60, length, (width / 2).toFloat(), (height / 2).toFloat(), canvas, paint)
            val hourMarks = RegPoly(
                12,
                length - 20,
                (width / 2).toFloat(),
                (height / 2).toFloat(),
                canvas,
                paint
            )
            val hourHand = RegPoly(
                60,
                length - 100,
                (width / 2).toFloat(),
                (height / 2).toFloat(),
                canvas,
                pHr
            )
            val minHand = RegPoly(
                60,
                length - 50,
                (width / 2).toFloat(),
                (height / 2).toFloat(),
                canvas,
                pMin
            )
            val secHand = RegPoly(
                60,
                length - 30,
                (width / 2).toFloat(),
                (height / 2).toFloat(),
                canvas,
                pSec
            )

            val bodyLength = length - 80
            val body =
                RegPoly(
                    60,
                    bodyLength,
                    (width / 2).toFloat(),
                    (height / 2).toFloat(),
                    canvas,
                    paint
                )
            val number =
                RegPoly(
                    12,
                    360F,
                    ((width - 25) / 2).toFloat(),
                    ((height + 25) / 2).toFloat(),
                    canvas,
                    paint
                )
            secMarks.drawPoints()
            hourMarks.drawPoints()
            body.drawRegPoly()
            for (i in 1..12) {
                canvas.drawText(
                    i.toString(),
                    number.getX((i + 9) % 12),
                    number.getY((i + 9) % 12),
                    paint
                )
            }
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            val calendar = Calendar.getInstance()
            hour = calendar[Calendar.HOUR]
            min = calendar[Calendar.MINUTE]
            sec = calendar[Calendar.SECOND]
            secHand.drawRadius(sec + 45)
            minHand.drawRadius(min + 45)
            hourHand.drawRadius(hour * 5 + min / 12 + 45)
            surfaceHolder!!.unlockCanvasAndPost(canvas)
        }
    }

    init {
        surfaceHolder = this.holder
    }
}