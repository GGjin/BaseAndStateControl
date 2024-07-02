package com.gg.utils.multistatepage

fun interface OnNotifyListener<T : MultiState> {
    fun onNotify(multiState: T)
}