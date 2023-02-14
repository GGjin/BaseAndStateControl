package com.gg.base.utils

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * @description:
 * @author: Jinyu.Guo
 * @createDate: 2022/8/25 025 16:43
 * @updateUser:
 * @updateDate: 2022/8/25 025 16:43
 */


inline fun <reified VB : ViewBinding> Fragment.binding() =
    FragmentInflateBindingProperty(VB::class.java)

class FragmentInflateBindingProperty<VB : ViewBinding>(private val clazz: Class<VB>) : ReadOnlyProperty<Fragment, VB> {
    private var binding: VB? = null
    private val handler by lazy { Handler(Looper.getMainLooper()) }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        if (binding == null) {
            try {
                @Suppress("UNCHECKED_CAST")
                binding = (clazz.getMethod("inflate", LayoutInflater::class.java).invoke(null, thisRef.layoutInflater) as VB)
            } catch (e: IllegalStateException) {
                throw IllegalStateException("The property of ${property.name} has been destroyed.")
            }
            thisRef.viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    handler.post { binding = null }
                }
            })
        }
        return binding!!
    }
}