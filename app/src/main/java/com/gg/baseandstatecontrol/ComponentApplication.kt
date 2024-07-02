package com.gg.baseandstatecontrol

import android.app.Application
import com.gg.utils.multistatepage.MultiStateConfig
import com.gg.utils.multistatepage.MultiStatePage
import com.tencent.mmkv.MMKV


/**
 * @description:
 * @author: rxhttp
 * @createDate: 2022/8/10 010 16:37
 * @updateUser:
 * @updateDate: 2022/8/10 010 16:37
 */
class ComponentApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        MMKV.initialize(this)

        val pageConfig = MultiStateConfig.Builder()
            .alphaDuration(300)
            .errorIcon(R.drawable.state_error)
            .emptyIcon(R.drawable.state_empty)
            .emptyMsg("emptyMsg")
            .loadingMsg("loadingMsg")
            .errorMsg("errorMsg")
            .build()

        MultiStatePage.config(pageConfig)
    }

}