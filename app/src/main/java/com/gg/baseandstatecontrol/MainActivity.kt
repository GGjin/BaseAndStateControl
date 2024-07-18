package com.gg.baseandstatecontrol

import com.gg.base.BaseActivity
import com.gg.baseandstatecontrol.databinding.ActivityMainBinding
import com.gg.baseandstatecontrol.fragment.StateFragment
import com.gg.baseandstatecontrol.utils.setNavigationBar

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun initView() {
        setNavigationBar {
            setText(R.id.left_text, "返回")
            setText(R.id.middle_text, "设置")
            setClick(R.id.left_view) {
                finish()
            }
        }
        val fragment = StateFragment()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment, fragment)
            .commit()
    }

    override fun initData() {
    }
}