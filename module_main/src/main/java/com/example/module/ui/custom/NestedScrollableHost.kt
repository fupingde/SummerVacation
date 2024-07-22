import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.viewpager2.widget.ViewPager2

class NestedScrollableHost @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private var viewPager2: ViewPager2? = null

    init {
        post {
            viewPager2 = findViewPager2(this)
        }
    }

    private fun findViewPager2(view: View): ViewPager2? {
        var parent: ViewParent? = view.parent
        while (parent != null) {
            if (parent is ViewPager2) {
                return parent
            }
            parent = parent.parent
        }
        return null
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        viewPager2?.let {
            if (it.isUserInputEnabled) {
                return super.onInterceptTouchEvent(ev)
            }
        }
        return false
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return super.onTouchEvent(ev)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility != GONE) {
                child.layout(0, 0, child.measuredWidth, child.measuredHeight)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
        }
    }
}
