package com.gg.baseandstatecontrol

import android.content.Intent
import com.blankj.utilcode.util.LogUtils
import com.gg.base.BaseActivity
import com.gg.baseandstatecontrol.activity.NewActivity
import com.gg.baseandstatecontrol.databinding.ActivityMainBinding
import com.gg.baseandstatecontrol.fragment.StateFragment
import com.gg.baseandstatecontrol.utils.setNavigationBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.measureTime

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun initView() {
        setNavigationBar {
            setText(R.id.left_text, "返回")
            setText(R.id.middle_text, "设置")
            setClick(R.id.left_view) {
                startActivity(Intent(this@MainActivity, NewActivity::class.java))
            }
        }
        val fragment = StateFragment()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment, fragment)
            .commit()
        val time = measureTime {
            CoroutineScope(Dispatchers.Default).launch {

            }
        }
        LogUtils.w(time)
    }

    override fun initData() {
    }
}