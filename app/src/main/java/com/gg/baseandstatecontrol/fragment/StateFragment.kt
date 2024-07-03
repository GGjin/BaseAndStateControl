package com.gg.baseandstatecontrol.fragment

import androidx.lifecycle.lifecycleScope
import com.gg.base.BaseFragment
import com.gg.baseandstatecontrol.databinding.FragmentStateBinding
import com.gg.utils.multistatepage.state.ErrorState
import com.gg.utils.multistatepage.state.LoadingState
import com.gg.utils.multistatepage.state.SuccessState
import kotlinx.coroutines.*


/**
 * @description:
 * @author: Jinyu.Guo
 * @createDate: 2022/8/29 029 14:46
 * @updateUser:
 * @updateDate: 2022/8/29 029 14:46
 */
class StateFragment : BaseFragment<FragmentStateBinding>() {

    override fun initView() {
        val errorState = ErrorState()

        errorState.retry {
            stateView.show<SuccessState>()
        }

        mBinding.error.setOnClickListener {
            stateView.show(errorState)

        }
        mBinding.normal.setOnClickListener {
            stateView.show<SuccessState>()
        }
        mBinding.loading.setOnClickListener {
            stateView.show<LoadingState>()
            lifecycleScope.launch {
                delay(2000)
                stateView.show<SuccessState>()
            }
        }
    }

    override fun initData() {
    }

}