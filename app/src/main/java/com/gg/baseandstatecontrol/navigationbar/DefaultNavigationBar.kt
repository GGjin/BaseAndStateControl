package com.gg.baseandstatecontrol.navigationbar

import android.content.Context
import android.view.ViewGroup
import com.gg.baseandstatecontrol.R
import com.gg.baseandstatecontrol.navigationbar.DefaultNavigationBar.Builder.DefaultNavigationParams

/**
 * Created by GG on 2017/8/29.
 */
class DefaultNavigationBar(params: DefaultNavigationParams?) : AbsNavigationBar<DefaultNavigationParams?>(params) {

    override fun bindLayoutId(): Int = R.layout.title_bar

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

    class Builder(context: Context?, parent: ViewGroup? = null) : AbsNavigationBar.Builder<DefaultNavigationParams>() {

        override var anp: DefaultNavigationParams = DefaultNavigationParams(context, parent)

        override fun create(): DefaultNavigationBar = DefaultNavigationBar(anp)

        fun putText(viewId: Int, text: String?): Builder {
            anp.textInfoMap[viewId] = text
            return this
        }

        fun putClick(viewId: Int, listener: (() -> Unit)? = null): Builder {
            anp.clickInfoMap[viewId] = listener
            return this
        }

        fun putIcon(viewId: Int, imgId: Int?): Builder {
            anp.iconInfoMap[viewId] = imgId
            return this
        }

        fun putTextMap(map: MutableMap<Int, String?>): Builder {
            anp.textInfoMap.putAll(map)
            return this
        }

        fun putClickMap(map: MutableMap<Int, (() -> Unit)?>): Builder {
            anp.clickInfoMap.putAll(map)
            return this
        }

        fun putIconMap(map: MutableMap<Int, Int?>): Builder {
            anp.iconInfoMap.putAll(map)
            return this
        }

        inner class DefaultNavigationParams(context: Context?, parent: ViewGroup?) : AbsNavigationParams(context, parent) {

            var textInfoMap: MutableMap<Int, String?> = mutableMapOf()
            var clickInfoMap: MutableMap<Int, (() -> Unit)?> = mutableMapOf()
            var iconInfoMap: MutableMap<Int, Int?> = mutableMapOf()

        }
    }
}