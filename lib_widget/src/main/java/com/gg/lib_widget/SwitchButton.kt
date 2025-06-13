package com.gg.lib_widget

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.addListener
import androidx.core.graphics.toColorInt
import com.blankj.utilcode.util.LogUtils
import com.gg.utils.app.dp2px
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @description:
 * @author: GG
 * @createDate: 2025 6月 12 15:08
 */
class SwitchButton : View {

    private val gestureDetector: GestureDetector by lazy { GestureDetector(context, GestureListener()) }

    private var listener: ((SwitchButton, SwitchState) -> Unit)? = null
    /**
     * 按钮半径
     */

    /**
     * 背景半径
     */
    private var viewRadius = 0f

    /**
     * 按钮半径
     */
    private var buttonRadius = 0f

    /**
     * 背景高
     */
    private var height = 0f

    /**
     * 背景宽
     */
    private var width = 0f

    /**
     * 背景位置
     */
    private var left = 0f
    private var top = 0f
    private var right = 0f
    private var bottom = 0f
    private var centerX = 0f
    private var centerY = 0f

    private var isCanLoading = false

    /**
     * button在end的 背景颜色
     */
    private var endColor = 0

    /**
     * button在start的 背景颜色
     */
    private var startColor = 0

    /**
     * 边框宽度px
     */
    private var borderWidth = 0

    /**
     * 按钮最左边
     */
    private var buttonMinX = 0f

    /**
     * 按钮最右边
     */
    private var buttonMaxX = 0f

    /**
     * 按钮画笔
     */
    private val buttonPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
        }
    }

    /**
     * 背景画笔
     */
    private val paint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }

    // 圆环背景颜色
    private var ringBgColor = 0

    // 进度颜色
    private var progressColor = 0

    private val buttonDuration = 200L
    private val loadingDuration = 1000L

    /**
     * 当前状态
     */
    private var viewState: ViewState = ViewState()
    private var afterState: ViewState = ViewState()
    private var beforeState: ViewState = ViewState()

    private var state: SwitchState? = null

    private var isButtonAnimating = false
    private val loadingAnimator by lazy {
        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = loadingDuration
            repeatCount = ValueAnimator.INFINITE
            interpolator = AccelerateDecelerateInterpolator()
        }
    }

    private val colorAnimator by lazy {
        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = buttonDuration
            interpolator = AccelerateDecelerateInterpolator()
        }
    }

    private var animator: ValueAnimator? = null
    private val animatorSet by lazy { AnimatorSet() }

    private val argbEvaluator by lazy { ArgbEvaluator() }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(0, 0, 0, 0)
    }

    /**
     * 初始化参数
     */
    private fun init(context: Context, attrs: AttributeSet?) {
        var typedArray: TypedArray? = null
        if (attrs != null) {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchButton)
        }
        isCanLoading = optBoolean(typedArray, R.styleable.SwitchButton_sb_can_loading, false)
        endColor = optColor(typedArray, R.styleable.SwitchButton_sb_end_color, Color.RED)
        startColor = optColor(typedArray, R.styleable.SwitchButton_sb_start_color, Color.GREEN)
        ringBgColor = optColor(typedArray, R.styleable.SwitchButton_sb_ring_color, "#BDBDBD".toColorInt())
        progressColor = optColor(typedArray, R.styleable.SwitchButton_sb_progress_color, "#42A5F5".toColorInt())
        borderWidth = optPixelSize(typedArray, R.styleable.SwitchButton_sb_border_width, 2.dp2px())
        val stateInt = optInt(typedArray, R.styleable.SwitchButton_sb_state, 0)
        afterState.state = when (stateInt) {
            0 -> SwitchState.True
            1 -> SwitchState.False
            else -> SwitchState.Loading
        }
        viewState.copy(afterState)
        typedArray?.recycle()
        super.setClickable(true)
        setPadding(0, 0, 0, 0)
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun onMeasure(widthMeasureSpecParam: Int, heightMeasureSpecParam: Int) {
        var widthMeasureSpec = widthMeasureSpecParam
        var heightMeasureSpec = heightMeasureSpecParam
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        if (widthMode == MeasureSpec.UNSPECIFIED || widthMode == MeasureSpec.AT_MOST) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(DEFAULT_WIDTH, MeasureSpec.EXACTLY)
        }
        if (heightMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.AT_MOST) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(DEFAULT_HEIGHT, MeasureSpec.EXACTLY)
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val viewPadding = borderWidth.toFloat()
        height = h - viewPadding - viewPadding
        width = w - viewPadding - viewPadding
        viewRadius = height * 0.5f
        buttonRadius = viewRadius - borderWidth
        left = viewPadding
        top = viewPadding
        right = w - viewPadding
        bottom = h - viewPadding
        centerX = (left + right) * 0.5f
        centerY = (top + bottom) * 0.5f
        buttonMinX = left + viewRadius
        buttonMaxX = right - viewRadius
        updateViewState(false)
        viewState.copy(afterState)
        invalidate()
    }

    private fun updateViewState(withAnimator: Boolean) {
        when (afterState.state) {
            SwitchState.True -> setStartViewState(withAnimator)
            SwitchState.False -> setEndViewState(withAnimator)
            else -> setLoadingViewState(withAnimator)
        }
    }

    private fun setStartViewState(withAnimator: Boolean) {
        afterState.radius = viewRadius
        afterState.checkStateColor = startColor
        afterState.buttonX = buttonMinX
        loadingAnimator.cancel()
        if (withAnimator) {
            setButtonProgress()
        }
    }

    private fun setEndViewState(withAnimator: Boolean) {
        afterState.radius = 0f
        afterState.checkStateColor = endColor
        afterState.buttonX = buttonMaxX
        loadingAnimator.cancel()
        if (withAnimator) {
            setButtonProgress()
        }
    }

    private fun setLoadingViewState(withAnimator: Boolean) {
        afterState.radius = viewRadius
        afterState.buttonX = centerX
        if (withAnimator) {
            setLoadingProgress()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (afterState.state == SwitchState.Loading && !isButtonAnimating) {
            drawLoading(canvas)
            return
        }
        // 绘制开启背景色
        paint.style = Paint.Style.FILL
        paint.color = viewState.checkStateColor
        paint.strokeWidth = borderWidth.toFloat()
        if (isButtonAnimating) {
            if (beforeState.state == SwitchState.True) {
                drawRoundRect(canvas, viewState.buttonX - viewRadius - borderWidth, top, right - viewState.buttonX + viewRadius + borderWidth, bottom, viewRadius, paint)
            } else {
                drawRoundRect(canvas, centerX * 2 - viewState.buttonX - viewRadius, top, viewState.buttonX + viewRadius + borderWidth, bottom, viewRadius, paint)
            }
        } else {
            if (beforeState.state == SwitchState.Loading) {
                if (afterState.state == SwitchState.True) {
                    drawRoundRect(canvas, viewState.buttonX - viewRadius - borderWidth, top, right - viewState.buttonX + viewRadius + borderWidth, bottom, viewRadius, paint)
                } else {
                    drawRoundRect(canvas, centerX * 2 - viewState.buttonX - viewRadius, top, viewState.buttonX + viewRadius + borderWidth, bottom, viewRadius, paint)
                }
            } else {
                drawRoundRect(canvas, left, top, right, bottom, viewRadius, paint)
            }
        }
        // 绘制按钮
        drawButton(canvas, viewState.buttonX, centerY)
    }

    private fun drawLoading(canvas: Canvas) {
        paint.color = ringBgColor
        paint.strokeWidth = borderWidth.toFloat() * 1.5f
        // 绘制圆环背景
        canvas.drawArc(centerX - viewRadius + borderWidth, centerY - viewRadius + borderWidth, centerX + viewRadius - borderWidth, centerY + viewRadius - borderWidth, 0f, 360f, false, paint)
        // 绘制进度弧，从 -90 度开始（让进度从顶部开始），扫过的角度根据进度计算
        paint.color = progressColor
        canvas.drawArc(centerX - viewRadius + borderWidth, centerY - viewRadius + borderWidth, centerX + viewRadius - borderWidth, centerY + viewRadius - borderWidth, -120f + viewState.progress * 360f, 80f, false, paint)
        buttonPaint.style = Paint.Style.FILL
        canvas.drawCircle(centerX, centerY, buttonRadius - borderWidth + 1f, buttonPaint)
    }

    private fun drawRoundRect(canvas: Canvas, left: Float, top: Float, right: Float, bottom: Float, backgroundRadius: Float, paint: Paint) {
        canvas.drawRoundRect(left, top, right, bottom, backgroundRadius, backgroundRadius, paint)
    }

    private fun drawButton(canvas: Canvas, x: Float, y: Float) {
        buttonPaint.let { canvas.drawCircle(x, y, buttonRadius, it) }
        paint.style = Paint.Style.STROKE
        paint.let { canvas.drawCircle(x, y, buttonRadius, it) }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        try {
            gestureDetector.onTouchEvent(event)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return true
    }

    fun setState(state: SwitchState, withAnimator: Boolean = true) {
        if (beforeState.state == viewState.state) {
            return
        }
        beforeState.copy(viewState)
        afterState.state = state
        updateViewState(withAnimator)
    }

    fun getState(): SwitchState = viewState.state!!

    fun setSwitchStateListener(listener: (SwitchButton, SwitchState) -> Unit) {
        this.listener = listener
    }

    /**
     * 保存动画状态
     */
    private class ViewState {
        /**
         * 按钮x位置[buttonMinX-buttonMaxX]
         */
        var buttonX = 0f

        /**
         * 状态背景颜色
         */
        var checkStateColor = 0

        var progress = 1f

        /**
         * 状态背景的半径
         */
        var radius = 0f

        var state: SwitchState? = null

        fun copy(source: ViewState) {
            buttonX = source.buttonX
            checkStateColor = source.checkStateColor
            radius = source.radius
            progress = source.progress
            state = source.state
        }
    }

    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean {
            // 如果状态相同，则不做任何操作
            if (beforeState.state == viewState.state || viewState.state == SwitchState.Loading) {
                return true
            }
            if (isCanLoading) {
                isButtonAnimating = true
                setState(SwitchState.Loading)
                GlobalScope.launch {
                    delay(1500)
                    MainScope().launch {
                        if (beforeState.state == SwitchState.True) {
                            setState(SwitchState.False)
                        } else {
                            setState(SwitchState.True)
                        }
                    }
                }
            } else {
                if (viewState.state == SwitchState.True) {
                    setState(SwitchState.False)
                } else {
                    setState(SwitchState.True)
                }

            }
            return true
        }
    }

    /**
     * 设置进度，带动画
     */
    private fun setLoadingProgress() {
        loadingAnimator.addUpdateListener { animation ->
            viewState.progress = animation.animatedValue as Float
            invalidate()
        }
        setButtonProgress {
            isButtonAnimating = false
            loadingAnimator.start()
        }
    }

    /**
     * 设置进度，带动画
     */
    private fun setButtonProgress(endFlow: (() -> Unit)? = null) {
        colorAnimator.addUpdateListener { animation ->
            viewState.checkStateColor = argbEvaluator.evaluate(
                animation.animatedValue as Float,
                beforeState.checkStateColor,
                afterState.checkStateColor
            ) as Int
        }
        animator = ValueAnimator.ofFloat(beforeState.buttonX, afterState.buttonX).apply {
            duration = buttonDuration
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animation ->
                viewState.buttonX = animation.animatedValue as Float
                invalidate()
            }

        }
        animatorSet.addListener(onEnd = {
            endFlow?.invoke()
            viewState.copy(afterState)
            if (state != viewState.state) {
                state = viewState.state
                listener?.invoke(this@SwitchButton, state!!)
            }
        })
        animatorSet.playTogether(animator, colorAnimator)
        animatorSet.start()
    }

    enum class SwitchState {
        True,
        False,
        Loading
    }

    companion object {
        private val DEFAULT_WIDTH = 58.dp2px()
        private val DEFAULT_HEIGHT = 36.dp2px()

        private fun optInt(typedArray: TypedArray?, index: Int, def: Int): Int = typedArray?.getInt(index, def) ?: def

        private fun optPixelSize(typedArray: TypedArray?, index: Int, def: Int): Int = typedArray?.getDimensionPixelOffset(index, def) ?: def

        private fun optColor(typedArray: TypedArray?, index: Int, def: Int): Int = typedArray?.getColor(index, def) ?: def

        private fun optBoolean(typedArray: TypedArray?, index: Int, def: Boolean): Boolean = typedArray?.getBoolean(index, def) ?: def
    }
}