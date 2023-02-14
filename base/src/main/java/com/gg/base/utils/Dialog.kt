package com.gg.base.utils

import android.app.Dialog
import androidx.viewbinding.ViewBinding

/**
 * @description:
 * @author: Jinyu.Guo
 * @createDate: 2022/8/25 025 16:53
 * @updateUser:
 * @updateDate: 2022/8/25 025 16:53
 */
inline fun <reified VB : ViewBinding> Dialog.binding() = lazy(LazyThreadSafetyMode.NONE) {
    inflateBinding<VB>(layoutInflater).also { setContentView(it.root) }
}
