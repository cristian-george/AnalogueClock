package com.example.analogueclock.utility

import android.graphics.Canvas
import android.graphics.Paint
import kotlin.math.cos
import kotlin.math.sin

class RegPoly(
    private val n: Int,
    r: Float,
    private val x0: Float,
    private val y0: Float,
    canvas: Canvas?,
    paint: Paint?
) {
    private val x: FloatArray
    private val y: FloatArray
    private var c: Canvas? = null
    private var p: Paint? = null
    fun getX(i: Int): Float {
        return x[i % n]
    }

    fun getY(i: Int): Float {
        return y[i % n]
    }

    fun drawRegPoly() {
        for (i in 0 until n) {
            c!!.drawLine(x[i], y[i], x[(i + 1) % n], y[(i + 1) % n], p!!)
        }
    }

    fun drawPoints() {
        for (i in 0 until n) {
            c!!.drawCircle(x[i], y[i], 5f, p!!)
        }
    }

    fun drawRadius(i: Int) {
        c!!.drawLine(x0, y0, x[i % n], y[i % n], p!!)
    }

    init {
        c = canvas
        p = paint
        x = FloatArray(n)
        y = FloatArray(n)
        for (i in 0 until n) {
            x[i] = (x0 + r * cos(2 * Math.PI * i / n)).toFloat()
            y[i] = (y0 + r * sin(2 * Math.PI * i / n)).toFloat()
        }
    }
}