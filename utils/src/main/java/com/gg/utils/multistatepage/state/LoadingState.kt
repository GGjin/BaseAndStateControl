package com.gg.utils.multistatepage.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
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