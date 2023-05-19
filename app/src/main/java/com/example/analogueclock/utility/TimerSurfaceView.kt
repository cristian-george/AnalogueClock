package com.example.analogueclock.utility

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.SurfaceHolder
import android.view.SurfaceView

@SuppressLint("ViewConstructor")
class TimerSurfaceView(context: Context, private val length: Float, private var secs: Float) :
    SurfaceView(context), Runnable {

    private var thread: Thread? = null
    private var isRunning = false

    private val holder: SurfaceHolder = getHolder()


    fun onResumeTimer() {
        isRunning = true
        thread = Thread(this)
        thread!!.start()
    }

    fun onPauseTimer() {
        var reEntry = true
        isRunning = false
        while (reEntry) {
            try {
                thread!!.join()
                reEntry = false
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    fun setSeconds(secs: Float) {
        this.secs = secs
    }

    override fun run() {
        var sec = 0
        while (isRunning) {
            if (holder.surface.isValid) {
                if (sec > secs) {
                    isRunning = false
                    continue
                }

                val canvas = holder.lockCanvas()

                val paint = Paint().apply { color = Color.parseColor("#FFE0B2") }
                canvas.drawPaint(paint)

                paint.textSize = 45f
                paint.strokeWidth = 2f
                paint.color = Color.parseColor("#303F9F")
                val secMarks =
                    RegPoly(
                        60,
                        length,
                        (width / 2).toFloat(),
                        (height / 2).toFloat(),
                        canvas,
                        paint
                    )
                val hourMarks = RegPoly(
                    12,
                    length - 20,
                    (width / 2).toFloat(),
                    (height / 2).toFloat(),
                    canvas,
                    paint
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


                paint.apply {
                    color = Color.BLACK
                    strokeWidth = 10f
                }

                val rectF = RectF(
                    (width / 2 - bodyLength),
                    (height / 2 - bodyLength),
                    (width / 2 + bodyLength),
                    (height / 2 + bodyLength)
                )

                val startAngle = -90f
                val endAngle = 360f * sec / secs
                canvas.drawArc(rectF, startAngle, endAngle, true, paint)

                try {
                    Thread.sleep(1000)
                } catch (ignored: Exception) {
                }

                sec++

                holder.unlockCanvasAndPost(canvas)
            }
        }
    }
}
