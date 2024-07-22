package com.example.module.ui.transformers


import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class CircularImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private val path = Path()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.reset()
        path.addCircle(
            (w / 2).toFloat(),
            (h / 2).toFloat(),
            (w / 2).toFloat(),
            Path.Direction.CW
        )
        path.close()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        canvas.clipPath(path)
        super.onDraw(canvas)
        canvas.restore()
    }
}
