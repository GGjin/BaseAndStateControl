package com.gg.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.gg.utils.multistatepage.MultiStateContainer
import com.gg.utils.multistatepage.createMultiState
import java.lang.reflect.ParameterizedType

/**
 * @description:
 * @author: Jinyu.Guo
 * @createDate: 2022/8/29 029 16:28
 * @updateUser:
 * @updateDate: 2022/8/29 029 16:28
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {


    val binding: VB by lazy {
        //使用反射得到viewbinding的class
        val type = javaClass.genericSuperclass as ParameterizedType
        val aClass = type.actualTypeArguments[0] as Class<*>
        val method = aClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        method.invoke(null, layoutInflater) as VB
    }

    val stateView: MultiStateContainer by createMultiState()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initData()
    }

    abstract fun initView()

    abstract fun initData()

}