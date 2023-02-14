package com.gg.utils.multistatepage.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.gg.utils.multistatepage.MultiState
import com.gg.utils.multistatepage.MultiStateContainer

/**
 * @description:
 * @author: Jinyu.Guo
 * @createDate: 2022/8/29 029 11:06
 * @updateUser:
 * @updateDate: 2022/8/29 029 11:06
 */
class SuccessState : MultiState() {
    override fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateContainer
    ): View {
        return View(context)
    }

    override fun onMultiStateViewCreate(view: View) = Unit

}