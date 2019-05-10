package cn.xhuww.sample.popup

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.PopupWindow
import cn.xhuww.sample.R
import kotlin.math.absoluteValue

class TextPopupWindow(activity: Activity) : PopupWindow(activity) {

    var actionViewOnClickListener: (view: View) -> Unit = {}
    private var actionView: View? = null

    init {
        val view = LayoutInflater.from(activity).inflate(R.layout.popup_text, null)
        contentView = view
        //设置宽高，PopupWindow未show之前无法获取 contentView 宽高
        height = activity.dip2px(40f)
        width = activity.dip2px(100f)

        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //实现点击外部关闭PopupWindow
        isOutsideTouchable = true
        //实现点击返回按钮关闭PopupWindow
        isFocusable = true

        //设置触摸事件拦截，在PopupWindow展示后，判断当前点击的位置是否处于展示PopupWindow的View的位置
        var downX = 0f
        setTouchInterceptor { _, event ->
            val finalActionView = actionView ?: return@setTouchInterceptor false
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downX = event.x
                }

                MotionEvent.ACTION_UP -> {
                    //排除 长按滑动事件
                    if (event.eventTime - event.downTime < TOUCH_INTERVALS_TIME &&
                        (downX - event.x).absoluteValue < MOVING_DISTANCE
                    ) {
                        val rect = finalActionView.getGlobalVisibleRect()
                        if (rect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                            actionViewOnClickListener(finalActionView)
                        }
                    }
                }
            }
            //若返回true 则点击外部不会关闭PopupWindow
            false
        }
    }

    fun showInTop(view: View) {
        actionView = view
        val rect = view.getGlobalVisibleRect()
        showAtLocation(view, Gravity.NO_GRAVITY, rect.centerX() - width / 2, rect.top - height)
    }

    fun showInBottom(view: View) {
        actionView = view
        val rect = view.getGlobalVisibleRect()
        showAtLocation(view, Gravity.NO_GRAVITY, rect.centerX() - width / 2, rect.bottom)
    }

    fun showInLeft(view: View) {
        actionView = view
        val rect = view.getGlobalVisibleRect()
        showAtLocation(view, Gravity.NO_GRAVITY, rect.left - width, rect.centerY() - height / 2)
    }

    fun showInRight(view: View) {
        actionView = view
        val rect = view.getGlobalVisibleRect()
        showAtLocation(view, Gravity.NO_GRAVITY, rect.right, rect.centerY() - height / 2)
    }

    companion object {
        private const val TOUCH_INTERVALS_TIME = 500
        private const val MOVING_DISTANCE = 30
    }
}

private fun View.getGlobalVisibleRect(): Rect {
    val rect = Rect()
    getGlobalVisibleRect(rect)
    return rect
}

fun Context.dip2px(dpValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}
