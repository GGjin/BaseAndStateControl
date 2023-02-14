package com.gg.base.utils

import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.viewbinding.ViewBinding

/**
 * @description:
 * @author: Jinyu.Guo
 * @createDate: 2022/8/25 025 16:52
 * @updateUser:
 * @updateDate: 2022/8/25 025 16:52
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified  VB : ViewBinding> ComponentActivity.binding(setContentView: Boolean = true) = lazy(LazyThreadSafetyMode.NONE) {
    inflateBinding<VB>(layoutInflater).also { binding ->
        if (setContentView) setContentView(binding.root)
    }
}

fun <VB : ViewBinding> ComponentActivity.binding(inflate: (LayoutInflater) -> VB, setContentView: Boolean = true) = lazy(LazyThreadSafetyMode.NONE) {
    inflate(layoutInflater).also { binding ->
        if (setContentView) setContentView(binding.root)
    }
}

