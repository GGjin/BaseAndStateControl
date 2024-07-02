package com.gg.utils.multistatepage.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.gg.utils.R
import com.gg.utils.multistatepage.MultiState
import com.gg.utils.multistatepage.MultiStateContainer
import com.gg.utils.multistatepage.MultiStatePage

class EmptyState : MultiState() {

    private lateinit var tvEmptyMsg: TextView
    private lateinit var imgEmpty: ImageView

    override fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateContainer
    ): View {
        return inflater.inflate(R.layout.mult_state_empty, container, false)
    }

    override fun onMultiStateViewCreate(view: View) {
        tvEmptyMsg = view.findViewById(R.id.tv_empty_msg)
        imgEmpty = view.findViewById(R.id.img_empty)

        setEmptyMsg(MultiStatePage.config.emptyMsg)
        setEmptyIcon(MultiStatePage.config.emptyIcon)
    }

    fun setEmptyMsg(emptyMsg: String) {
        tvEmptyMsg.text = emptyMsg
    }

    fun setEmptyIcon(@DrawableRes emptyIcon: Int) {
        imgEmpty.setImageResource(emptyIcon)
    }
}