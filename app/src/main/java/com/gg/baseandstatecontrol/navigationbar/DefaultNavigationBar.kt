package com.gg.baseandstatecontrol.navigationbar

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import com.gg.baseandstatecontrol.R
import com.gg.baseandstatecontrol.navigationbar.DefaultNavigationBar.Builder.DefaultNavigationParams

/**
 * Created by GG on 2017/8/29.
 */
class DefaultNavigationBar(params: DefaultNavigationParams?) : AbsNavigationBar<DefaultNavigationParams?>(params) {


    override fun bindLayoutId(): Int {
        return R.layout.title_bar
    }

    override fun applyViews() {
        params?.clickInfoMap?.forEach { (viewId, listener) ->
            setOnClickListener(viewId, listener)
        }
        params?.textInfoMap?.forEach { (viewId, text) ->
            setText(viewId, text)
        }
        params?.iconInfoMap?.forEach { (viewId, imgId) ->
            setIcon(viewId, imgId)
        }

    }

    class Builder : AbsNavigationBar.Builder<DefaultNavigationParams> {
        override var P: DefaultNavigationParams

        constructor(context: Context?, parent: ViewGroup?) : super(context!!, parent) {
            P = DefaultNavigationParams(context, parent)
        }

        constructor(context: Context?) : super(context!!) {
            P = DefaultNavigationParams(context, null)
        }


        override fun create(): DefaultNavigationBar? {
            return DefaultNavigationBar(P)
        }

        fun putText(viewId: Int, text: String?): Builder {
            P.textInfoMap[viewId] = text
            return this
        }

        fun putClick(viewId: Int, listener: (() -> Unit)? = null): Builder {
            P.clickInfoMap[viewId] = listener
            return this
        }

        fun putIcon(viewId: Int, imgId: Int?): Builder {
            P.iconInfoMap[viewId] = imgId
            return this
        }

        fun putTextMap(map: MutableMap<Int, String?>): Builder {
            P.textInfoMap.putAll(map)
            return this
        }

        fun putClickMap(map: MutableMap<Int, (() -> Unit)?>): Builder {
            P.clickInfoMap.putAll(map)
            return this
        }

        fun putIconMap(map: MutableMap<Int, Int?>): Builder {
            P.iconInfoMap.putAll(map)
            return this
        }


        inner class DefaultNavigationParams(context: Context?, parent: ViewGroup?) : AbsNavigationParams(context!!, parent) {

            var textInfoMap: MutableMap<Int, String?> = mutableMapOf()
            var clickInfoMap: MutableMap<Int, (() -> Unit)?> = mutableMapOf()
            var iconInfoMap: MutableMap<Int, Int?> = mutableMapOf()

        }
    }
}