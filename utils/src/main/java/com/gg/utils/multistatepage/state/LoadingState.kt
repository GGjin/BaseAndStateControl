package com.gg.utils.multistatepage.state

import android.content.Context
import android.view.*
import android.widget.TextView
import com.gg.utils.R
import com.gg.utils.multistatepage.*

class LoadingState : MultiState() {
    private lateinit var tvLoadingMsg: TextView
    override fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateContainer
    ): View {
        return inflater.inflate(R.layout.mult_state_loading, container, false)
    }

    override fun onMultiStateViewCreate(view: View) {
        tvLoadingMsg = view.findViewById(R.id.tv_loading_msg)
        setLoadingMsg(MultiStatePage.config.loadingMsg)
    }

    fun setLoadingMsg(loadingMsg: String) {
        tvLoadingMsg.text = loadingMsg
    }
}