package com.gg.baseandstatecontrol.utils

import android.app.Activity
import com.gg.baseandstatecontrol.navigationbar.DefaultNavigationBar

/**
 * @description:
 * @author: Jinyu.Guo
 * @createDate: 2022/8/26 026 16:57
 * @updateUser:
 * @updateDate: 2022/8/26 026 16:57
 */


fun Activity.setNavigationBar(init: NavigationBarDSL.() -> Unit?) {
    val dsl = NavigationBarDSL()
    dsl.init()
    DefaultNavigationBar.Builder(this)
        .putClickMap(dsl.clickInfoMap)
        .putTextMap(dsl.textInfoMap)
        .putIconMap(dsl.iconInfoMap)
        .create()
}
