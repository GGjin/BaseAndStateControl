package com.gg.utils.multistatepage

/**
 * @description:
 * @author: Jinyu.Guo
 * @createDate: 2022/8/29 029 11:03
 * @updateUser:
 * @updateDate: 2022/8/29 029 11:03
 */
fun interface OnNotifyListener<T : MultiState> {
    fun onNotify(multiState: T)
}