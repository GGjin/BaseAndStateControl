package com.gg.baseandstatecontrol.navigationbar

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.gg.baseandstatecontrol.navigationbar.AbsNavigationBar.Builder.AbsNavigationParams
import com.gg.utils.app.clickWithTrigger

/**
 * Created by GG on 2017/8/29.
 */
abstract class AbsNavigationBar<P : AbsNavigationParams?>(val params: P) : INavigationBar {

    private var mParentView: View? = null

    private fun createAndBindView() {
        if (params!!.mParent == null) {
            val activityRoot = (params.mContext as Activity).window.decorView as ViewGroup
            params.mParent = activityRoot.getChildAt(0) as ViewGroup
        }

//        GeelyLog.w(params.mParent!!.childCount)

        //需要单独处理Activity  Activity的实现方式和AppCompatActivity的不一样
        if (params.mParent == null) {
            return
        }
        mParentView = LayoutInflater.from(params.mContext).inflate(bindLayoutId(), params.mParent, false)
        params.mParent!!.addView(mParentView, 0)
//        GeelyLog.w(params.mParent!!.childCount)
        applyViews()
    }

    fun setText(viewId: Int, text: String?) {
        val tv = findViewById<TextView>(viewId)
        if (TextUtils.isEmpty(text)) {
            tv.visibility = View.INVISIBLE
        } else {
            tv.visibility = View.VISIBLE
            tv.text = text
        }
    }

    fun setIcon(viewId: Int, iconId: Int?) {
        val iv = findViewById<ImageView>(viewId)
        if (iconId != null && iconId != 0) {
            iv.visibility = View.VISIBLE
            iv.setImageResource(iconId)
        } else {
            iv.visibility = View.GONE
        }
    }

    fun setOnClickListener(viewId: Int, listener: (() -> Unit)?) {
        findViewById<View>(viewId).clickWithTrigger {
            listener?.invoke()
        }
    }

    private fun <T : View?> findViewById(viewId: Int): T {
        return mParentView!!.findViewById<View>(viewId) as T
    }

    abstract class Builder<ANP : AbsNavigationParams> {
        open lateinit var P: ANP

        constructor(context: Context, parent: ViewGroup?) {
//            P = ANP(context, parent)
        }

        constructor(context: Context) {
//            P = ANP(context, null)
        }

        abstract fun create(): AbsNavigationBar<*>?

        open class AbsNavigationParams(var mContext: Context, var mParent: ViewGroup?)
    }

    init {
        createAndBindView()
    }
}