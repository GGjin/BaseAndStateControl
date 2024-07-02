package com.gg.utils.multistatepage

import android.app.Activity
import android.view.View
import androidx.fragment.app.Fragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun View.bindMultiState() = MultiStatePage.bindMultiState(this)

fun Activity.bindMultiState() = MultiStatePage.bindMultiState(this)

fun Activity.createMultiState() = lazy(LazyThreadSafetyMode.NONE) {
    bindMultiState()
}

fun Fragment.createMultiState(view: View? = null) = lazy(LazyThreadSafetyMode.NONE) {
    (view ?: requireView()).bindMultiState()
}