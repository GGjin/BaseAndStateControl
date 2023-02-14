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

/**
 * @description:
 * @author: Jinyu.Guo
 * @createDate: 2022/8/29 029 11:05
 * @updateUser:
 * @updateDate: 2022/8/29 029 11:05
 */

class ErrorState : MultiState() {

    private lateinit var tvErrorMsg: TextView
    private lateinit var imgError: ImageView
    private lateinit var tvRetry: TextView

    private var retry: OnRetryClickListener? = null

    override fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateContainer
    ): View {
        return inflater.inflate(R.layout.mult_state_error, container, false)
    }

    override fun onMultiStateViewCreate(view: View) {
        tvErrorMsg = view.findViewById(R.id.tv_error_msg)
        imgError = view.findViewById(R.id.img_error)
        tvRetry = view.findViewById(R.id.tv_retry)

        setErrorMsg(MultiStatePage.config.errorMsg)
        setErrorIcon(MultiStatePage.config.errorIcon)

        tvRetry.setOnClickListener { retry?.retry() }
    }

    fun setErrorMsg(errorMsg: String) {
        tvErrorMsg.text = errorMsg
    }

    fun setErrorIcon(@DrawableRes errorIcon: Int) {
        imgError.setImageResource(errorIcon)
    }

    fun retry(retry: OnRetryClickListener) {
        this.retry = retry
    }

    fun interface OnRetryClickListener {
        fun retry()
    }
}