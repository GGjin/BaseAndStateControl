package com.gg.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.gg.base.utils.createMultiState
import com.zy.multistatepage.MultiStateContainer
import java.lang.reflect.ParameterizedType

/**
 * @description:
 * @author: Jinyu.Guo
 * @createDate: 2022/8/25 025 16:40
 * @updateUser:
 * @updateDate: 2022/8/25 025 16:40
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    @Suppress("UNCHECKED_CAST")
    val mBinding: VB by lazy {
        //使用反射得到viewbinding的class
        val type = javaClass.genericSuperclass as ParameterizedType
        val aClass = type.actualTypeArguments[0] as Class<*>
        val method = aClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        method.invoke(null, layoutInflater) as VB
    }

    val stateView: MultiStateContainer by createMultiState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(mBinding.root)

        initView()
        initData()
    }

    abstract fun initView()

    abstract fun initData()

}