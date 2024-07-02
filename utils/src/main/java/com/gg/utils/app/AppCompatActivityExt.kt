@file:JvmName("AndroidUtils")
package com.gg.utils.app

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Build
import android.telephony.TelephonyManager
import android.text.InputFilter
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.util.regex.Pattern


/**
 *  Creator : GG
 */

fun Activity.setTransparent() {
    if (Build.VERSION.SDK_INT >= 21) {
        val decorView = window.decorView
        val option = (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        decorView.systemUiVisibility = option
        window.navigationBarColor = Color.TRANSPARENT
        window.statusBarColor = Color.TRANSPARENT
    }
}

/**
 * Runs a FragmentTransaction, then calls commit().
 */
private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}

/**
 * 获取屏幕宽度
 */
fun Context.screenWidth(): Int {
    val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val outMetrics = DisplayMetrics()
    wm.defaultDisplay.getMetrics(outMetrics)
    return outMetrics.widthPixels
}

/**
 * 获取屏幕高度
 */
fun Context.screenHeight(): Int {
    val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val outMetrics = DisplayMetrics()
    wm.defaultDisplay.getMetrics(outMetrics)
    return outMetrics.heightPixels
}

/**
 * 获取当前app的状态
 */
fun Context.isApkInDebug(): Boolean {
    return try {
        val info = this.applicationInfo
        info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
    } catch (e: Exception) {
        false
    }
}

/**
 * 获取当前线程名字
 */
fun Context.curProcessName(): String? {
    val pid = android.os.Process.myPid()
    val activityManager = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    for (appProcess in activityManager.runningAppProcesses) {
        if (appProcess.pid == pid) {
            return appProcess.processName
        }
    }
    return null
}

fun dp2px(dpValue: Number): Float {
    return (0.5f + dpValue.toFloat() * Resources.getSystem().displayMetrics.density)
}

fun Float.px(): Float {
    return (0.5f + this * Resources.getSystem().displayMetrics.density)
}

fun Int.px(): Int {
    return (0.5f + this * Resources.getSystem().displayMetrics.density).toInt()
}

fun dp2px2int(dpValue: Number): Int {
    return (0.5f + dpValue.toFloat() * Resources.getSystem().displayMetrics.density).toInt()
}

/**
 * dp转px
 */
fun Context.dp2px(dp: Number): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(), this.resources.displayMetrics
    ).toInt()
}

/**
 * dp转px(float)
 */
fun Context.dp2fpx(dp: Number): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(), this.resources.displayMetrics
    )
}

/**
 * px转dp
 */
fun Context.px2dp(px: Number): Int {
    val scale = this.resources.displayMetrics.density
    return (px.toFloat() / scale + 0.5f).toInt()
}

/**
 * dp转px
 */
fun Fragment.dp2px(dp: Number): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(), this.resources.displayMetrics
    ).toInt()
}

/**
 * px转dp
 */
fun Fragment.px2dp(px: Number): Int {
    val scale = this.resources.displayMetrics.density
    return (px.toFloat() / scale + 0.5f).toInt()
}

/**
 * 设置添加屏幕的背景透明度
 */
fun Activity.backgroundAlpha(alpha: Float) {
    val lp = this.window.attributes
    lp.alpha = alpha
    this.window.attributes = lp
}

/**
 * 获取版本名
 *
 * @return 当前应用的版本名
 */
fun Context.versionName(): String =
    try {
        val manager = this.packageManager
        val info = manager.getPackageInfo(this.packageName, 0)
        info.versionName
    } catch (e: Exception) {
        e.printStackTrace()
//        this.getString(R.string.can_not_find_version_name)
        ""
    }

/**
 * 获取版本号
 *
 * @return 当前应用的版本号
 */
fun Context.versionCode(): Int =
    try {
        val manager = this.packageManager
        val info = manager.getPackageInfo(this.packageName, 0)
        info.versionCode
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }


/**
 * 通过反射的方式获取状态栏高度
 *
 * @return
 */
fun Context.statusBarHeight(): Int {
    try {
        val c = Class.forName("com.android.internal.R\$dimen")
        val obj = c.newInstance()
        val field = c.getField("status_bar_height")
        val x = Integer.parseInt(field.get(obj).toString())
        return this.resources.getDimensionPixelSize(x)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return 0
}

/**
 * 获取底部导航栏高度
 *
 * @return
 */
fun Context.getNavigationBarHeight(): Int {
    val resources = this.resources
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    //获取NavigationBar的高度
    return resources.getDimensionPixelSize(resourceId)
}

/**
 * 获取是否存在NavigationBar
 */
fun Context.checkDeviceHasNavigationBar(): Boolean {
    var hasNavigationBar = false
    val rs = this.resources
    val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
    if (id > 0) {
        hasNavigationBar = rs.getBoolean(id)
    }
    try {
        val systemPropertiesClass = Class.forName("android.os.SystemProperties")
        val m = systemPropertiesClass.getMethod("get", String::class.java)
        val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
        if ("1" == navBarOverride) {
            hasNavigationBar = false
        } else if ("0" == navBarOverride) {
            hasNavigationBar = true
        }
    } catch (e: Exception) {

    }

    return hasNavigationBar

}

/**
 * 以兼容的方式获取颜色值
 */
fun Context.getCompatColor(@ColorRes id: Int): Int =
    ContextCompat.getColor(this, id)

/**
 * 以兼容的方式获取drawable
 */
fun Context.getCompatDrawable(@DrawableRes id: Int): Drawable? =
    ContextCompat.getDrawable(this, id)

/**
 *
 * 通过包名判断程序是否安装
 *
 */
fun Context?.checkAppInstalled(name: String): Boolean {

    if (name.isEmpty()) {
        return false
    }
    var found = true
    try {
        this?.packageManager?.getPackageInfo(name, 0)
    } catch (e: PackageManager.NameNotFoundException) {
        found = false
    }

    return found
}

fun AppCompatActivity.context(): Context = this


fun Fragment.toast(
    int: Int,
    length: Int = Toast.LENGTH_SHORT,
    gravity: Int = Gravity.CENTER,
    xOffset: Int = 0,
    yOffset: Int = 0
) {
    val toast = Toast.makeText(context?.applicationContext, int, length)
    toast.setGravity(gravity, xOffset, yOffset)
    toast.show()
}

fun String.isPhoneNum(): Boolean {
    val p = Pattern.compile("^((13[0-9])|(15[^4])|(18[0-9])|(17[0-9])|(147))\\d{8}$")
    val m = p.matcher(this)
    return m.matches()
}


/**
 * 判定输入汉字
 *
 * @param c
 * @return
 */
fun Char.isChinese(): Boolean {
    val ub = Character.UnicodeBlock.of(this)
    return ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub === Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub === Character.UnicodeBlock.GENERAL_PUNCTUATION || ub === Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub === Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS

}

fun EditText.onlyChinese() {
    val filter = InputFilter { source, start, end, dest, dstart, dend ->
        for (i in start until end) {
            if (!source[i].isChinese()) {
                return@InputFilter ""
            }
        }
        null
    }
    this.filters = arrayOf(filter)
}

fun Int?.isNullOrZero(): Boolean {
    return this == null || this == 0
}

fun Double?.isNullOrZero(): Boolean {
    return this == null || this == 0.0
}

fun Long?.isNullOrZero(): Boolean {
    return this == null || this == 0L

}

//fun View.showSnackbar(snackbarText: String, timeLength: Int) {
//    Snackbar.make(this, snackbarText, timeLength).show()
//}

/**
 * 去除字符串中的空格、回车、换行符、制表符等
 * @param str
 * @return
 */
fun String.replaceSpecialStr(): String {
    val p = Pattern.compile("\\s*|\t|\r|\n")
    val m = p.matcher(this)
    return m.replaceAll("")
}

/**
 * 获取横竖屏
 */
fun Fragment.getOrientation(): Int {
    //获取设置的配置信息
    //获取屏幕方向
    return resources.configuration.orientation
}

fun Activity.getOrientation(): Int {
    //获取设置的配置信息
    //获取屏幕方向
    return resources.configuration.orientation
}

/**
 * 获取联网状态
 */
fun Context.isNetWork(): Boolean {
    //连接服务 CONNECTIVITY_SERVICE
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    //网络信息 NetworkInfo
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

/**
 * 获取当前的网络状态 ：没有网络-0：WIFI网络1：4G网络-4：3G网络-3：2G网络-2
 * 自定义
 *
 * @param context
 * @return
 */
fun Context.getAPNType(): Int {
    //结果返回值
    var netType = 0
    //获取手机所有连接管理对象
    val manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    //获取NetworkInfo对象
    val networkInfo = manager.activeNetworkInfo ?: return netType
    //NetworkInfo对象为空 则代表没有网络
    //否则 NetworkInfo对象不为空 则获取该networkInfo的类型
    val nType = networkInfo.type
    if (nType == ConnectivityManager.TYPE_WIFI) {
        //WIFI
        netType = 1
    } else if (nType == ConnectivityManager.TYPE_MOBILE) {
        val nSubType = networkInfo.subtype
        val telephonyManager = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        //3G   联通的3G为UMTS或HSDPA 电信的3G为EVDO
        netType = if (nSubType == TelephonyManager.NETWORK_TYPE_LTE
            && !telephonyManager.isNetworkRoaming
        ) {
            4
        } else if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS || nSubType == TelephonyManager.NETWORK_TYPE_HSDPA || (nSubType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    && !telephonyManager.isNetworkRoaming)
        ) {
            3
            //2G 移动和联通的2G为GPRS或EGDE，电信的2G为CDMA
        } else if (nSubType == TelephonyManager.NETWORK_TYPE_GPRS || nSubType == TelephonyManager.NETWORK_TYPE_EDGE || (nSubType == TelephonyManager.NETWORK_TYPE_CDMA
                    && !telephonyManager.isNetworkRoaming)
        ) {
            2
        } else {
            2
        }
    }
    return netType
}

