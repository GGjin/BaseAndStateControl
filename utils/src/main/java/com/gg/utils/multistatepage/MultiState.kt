package com.gg.utils.multistatepage

import android.content.Context
import android.view.LayoutInflater
import android.view.View

/**
 * @description:
 * @author: Jinyu.Guo
 * @createDate: 2022/8/29 029 11:01
 * @updateUser:
 * @updateDate: 2022/8/29 029 11:01
 */
abstract class MultiState {

    /**
     * 创建stateView
     */
    abstract fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateContainer
    ): View

    /**
     * stateView创建完成
     */
    abstract fun onMultiStateViewCreate(view: View)
}