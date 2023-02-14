package com.gg.baseandstatecontrol.fragment

import com.gg.base.BaseFragment
import com.gg.baseandstatecontrol.databinding.FragmentStateBinding
import com.gg.utils.multistatepage.state.ErrorState
import com.gg.utils.multistatepage.state.LoadingState
import com.gg.utils.multistatepage.state.SuccessState


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

        binding.error.setOnClickListener {
            stateView.show(errorState)

        }
        binding.normal.setOnClickListener {
            stateView.show<SuccessState>()
        }
        binding.loading.setOnClickListener {
            stateView.show<LoadingState>()
        }
    }

    override fun initData() {
    }

}