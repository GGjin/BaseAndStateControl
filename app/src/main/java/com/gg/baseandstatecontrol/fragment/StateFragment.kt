package com.gg.baseandstatecontrol.fragment

import androidx.lifecycle.lifecycleScope
import com.gg.base.BaseFragment
import com.gg.baseandstatecontrol.databinding.FragmentStateBinding
import com.zy.multistatepage.state.*
import kotlinx.coroutines.*

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