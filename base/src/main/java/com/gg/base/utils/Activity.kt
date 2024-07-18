package com.gg.base.utils

import android.app.Activity
import android.view.*
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.zy.multistatepage.bindMultiState

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

fun Activity.createMultiState() = lazy(LazyThreadSafetyMode.NONE) {
    bindMultiState()
}

fun Fragment.createMultiState(view: View? = null) = lazy(LazyThreadSafetyMode.NONE) {
    (view ?: requireView()).bindMultiState()
}

