package com.gg.baseandstatecontrol.utils

/**
 * @description:
 * @author: Jinyu.Guo
 * @createDate: 2022/8/26 026 17:06
 * @updateUser:
 * @updateDate: 2022/8/26 026 17:06
 */

class NavigationBarDSL {

    var textInfoMap: MutableMap<Int, String?> = mutableMapOf()
    var clickInfoMap: MutableMap<Int, (() -> Unit)?> = mutableMapOf()
    var iconInfoMap: MutableMap<Int, Int?> = mutableMapOf()

    fun setText(viewId: Int, text: String?) {
        textInfoMap[viewId] = text
    }

    fun setClick(viewId: Int, listener: (() -> Unit)? = null) {
        clickInfoMap[viewId] = listener
    }

    fun setIcon(viewId: Int, imgId: Int?) {
        iconInfoMap[viewId] = imgId
    }

}