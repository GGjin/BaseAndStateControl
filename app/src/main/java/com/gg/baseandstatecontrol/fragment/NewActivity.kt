package com.gg.baseandstatecontrol.fragment

import androidx.activity.viewModels
import com.gg.base.BaseActivity
import com.gg.baseandstatecontrol.databinding.ActivityNewBinding
import com.gg.baseandstatecontrol.vm.NewViewModel

/**
 * Filename: NewActivity
 * Author: GG
 * Date: 2024/7/3
 * Description:
 */
class NewActivity :BaseActivity<ActivityNewBinding>() {

    private val  mViewModel : NewViewModel by viewModels()

    override fun initView() {

    }

    override fun initData() {

    }
}