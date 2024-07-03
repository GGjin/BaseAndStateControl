package com.gg.base

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.gg.utils.multistatepage.*
import java.lang.reflect.ParameterizedType

/**
 * @description:
 * @author: Jinyu.Guo
 * @createDate: 2022/8/29 029 16:28
 * @updateUser:
 * @updateDate: 2022/8/29 029 16:28
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    @Suppress("UNCHECKED_CAST")
    val mBinding: VB by lazy {
        //使用反射得到viewbinding的class
        val type = javaClass.genericSuperclass as ParameterizedType
        val aClass = type.actualTypeArguments[0] as Class<*>
        val method = aClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        method.invoke(null, layoutInflater) as VB
    }

    val stateView: MultiStateContainer by createMultiState()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initData()
    }

    abstract fun initView()

    abstract fun initData()

}