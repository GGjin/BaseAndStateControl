package com.gg.baseandstatecontrol.activity

import androidx.activity.viewModels
import com.blankj.utilcode.util.LogUtils
import com.gg.base.BaseActivity
import com.gg.baseandstatecontrol.databinding.ActivityNewBinding
import com.gg.baseandstatecontrol.vm.NewViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.measureTime

/**
 * Filename: NewActivity
 * Author: GG
 * Date: 2024/7/3
 * Description:
 */
class NewActivity : BaseActivity<ActivityNewBinding>() {

    private val mViewModel: NewViewModel by viewModels()

    override fun initView() {
        val time = measureTime {
            CoroutineScope(Dispatchers.Default).launch {

            }
        }
        LogUtils.w(time)
    }

    override fun initData() {

    }
}