package com.gg.baseandstatecontrol

import android.app.Application
import com.tencent.mmkv.MMKV
import com.zy.multistatepage.*

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