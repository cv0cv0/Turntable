package me.gr.turntable.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import me.gr.turntable.ext.toDp
import me.gr.turntable.ext.toSp

class TurntableView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { textSize = 16.toSp() }
    private val arcRectF = RectF()
    private val textRectF = RectF()
    private val textPath = Path()

    private val ringWidth = 20.toDp()
    private val arcColors = arrayOf(0xfff481eb.toInt(), 0xffffdeb1.toInt())
    private val texts = arrayOf("四等奖", "五等奖", "六等奖", "七等奖", "八等奖", "九等奖", "十等奖", "一等奖", "二等奖", "三等奖")
    private val count = texts.size
    private val sweepAngle = 360f / count

    private var center = 0f
    private var startAngle = 0f

    private var valueListener: ((value: String) -> Unit)? = null

    init {
        ValueAnimator::class.java.getDeclaredField("sDurationScale").run {
            isAccessible = true
            if (getFloat(null) != 1f) {
                setFloat(null, 1f)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = MeasureSpec.getSize(widthMeasureSpec)
        val h = MeasureSpec.getSize(heightMeasureSpec)
        val size = Math.min(w, h)
        center = size / 2f
        arcRectF.set(ringWidth, ringWidth, size - ringWidth, size - ringWidth)
        textRectF.set(ringWidth * 3, ringWidth * 3, size - ringWidth * 3, size - ringWidth * 3)
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        paint.color = 0xffff4321.toInt()
        canvas.drawCircle(center, center, center, paint)

        startAngle = 0f
        repeat(count) {
            paint.color = arcColors[it % 2]
            canvas.drawArc(arcRectF, startAngle, sweepAngle, true, paint)
            drawText(canvas, texts[it])
            startAngle += sweepAngle
        }
    }

    fun start() {
        val degree = 360 * Math.random().toFloat()
        ObjectAnimator.ofFloat(this, "rotation", 0f, 1080 + degree).run {
            duration = 5000
            interpolator = DecelerateInterpolator()
            doOnEnd {
                var rotateAngle = 270 - degree
                if (rotateAngle < 0) rotateAngle += 360
                val position = (rotateAngle / sweepAngle).toInt()
                valueListener?.invoke(texts[position])
            }
            start()
        }
    }

    fun setValueListener(listener: (value: String) -> Unit) {
        valueListener = listener
    }

    private fun drawText(canvas: Canvas, text: String) {
        textPath.reset()
        textPath.addArc(textRectF, startAngle, sweepAngle)
        paint.color = Color.RED
        val textWidth = paint.measureText(text)
        val hOffset = sweepAngle * Math.PI.toFloat() * (center - ringWidth * 3) / 180 / 2 - textWidth / 2
        canvas.drawTextOnPath(text, textPath, hOffset, 0f, paint)
    }
}