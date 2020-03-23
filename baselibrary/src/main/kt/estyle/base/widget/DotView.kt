package estyle.base.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import estyle.base.R

class DotView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var dotMargin: Int = 0
    private var dotSize: Int = 0
    private var defaultColor = 0
    private var selectedColor = 0

    var dotCount: Int = 0
        set(value) {
            field = value
            selectedPosition = 0// todo 可能不会触发setter
            visibility = if (value > 1) VISIBLE else GONE
            requestLayout()
        }

    var selectedPosition: Int = 0
        set(value) {
            field = value
            invalidate()
        }

    private val paint by lazy { Paint() }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DotView)
        dotMargin = typedArray.getDimensionPixelSize(R.styleable.DotView_dotMargin, 0)
        selectedColor = typedArray.getColor(R.styleable.DotView_selectColor, selectedColorGlobal)
        defaultColor = typedArray.getColor(R.styleable.DotView_defaultColor, defaultColorGlobal)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var widthSize = MeasureSpec.getSize(width)
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST) {
            widthSize = (dotSize + dotMargin) * dotCount - dotMargin
        }
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            dotSize = MeasureSpec.getSize(heightMeasureSpec)
        }
        setMeasuredDimension(widthSize, dotSize)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (i in 0 until dotCount) {
            paint.color = if (i == selectedPosition) selectedColor else defaultColor
            canvas.drawCircle(
                dotSize / 2f + (dotSize + dotMargin) * i,
                dotSize / 2f,
                dotSize / 2f,
                paint
            )
        }
    }

    companion object {
        var defaultColorGlobal: Int = 0
        var selectedColorGlobal: Int = 0
    }
}