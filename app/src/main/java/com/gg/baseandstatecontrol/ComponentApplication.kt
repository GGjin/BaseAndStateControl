package com.gg.baseandstatecontrol

import android.app.Application
import com.gg.utils.app.versionName
import com.tencent.mmkv.MMKV
import com.zy.multistatepage.*
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.converter.GsonConverter
import rxhttp.wrapper.converter.MoshiConverter
import rxhttp.wrapper.cookie.CookieStore
import rxhttp.wrapper.param.*
import rxhttp.wrapper.ssl.HttpsUtils
import java.io.File
import java.util.TimeZone
import java.util.concurrent.TimeUnit

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
        initHttp()
    }


    private fun initHttp() {
        val file: File = File(getExternalCacheDir(), "RxHttpCookie")
        val sslParams: HttpsUtils.SSLParams = HttpsUtils.getSslSocketFactory()
        val client = OkHttpClient.Builder()
            .cookieJar(CookieStore(file))
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
//            .addInterceptor(HttpCodeInterceptor())
//            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager) //添加信任证书
//            .hostnameVerifier { hostname: String?, session: SSLSession? -> true } //忽略host验证
            //            .followRedirects(false)  //禁制OkHttp的重定向操作，我们自己处理重定向
            //            .addInterceptor(new RedirectInterceptor())
            //            .addInterceptor(new TokenInterceptor())
            .build()
        RxHttpPlugins.init(client)      //自定义OkHttpClient对象
//            .setDebug(BuildConfig.DEBUG, true, 4)      //是否开启调试模式、是否分段打印、打印json数据缩进空格数量
            .setDebug(BuildConfig.DEBUG)      //是否开启调试模式、是否分段打印、打印json数据缩进空格数量
//            .setCache(File, long, CacheMode)  //配置缓存目录，最大size及缓存模式
//            .setExcludeCacheKeys(String...)   //设置一些key，不参与cacheKey的组拼
//        .setResultDecoder(Function)       //设置数据解密/解码器，非必须
//            .setConverter(GsonConverter.create(GsonFactory.getSingletonGson()))         //设置全局的转换器，非必须
            .setConverter(MoshiConverter.create())
            .setOnParamAssembly { p: Param<*> ->                  //设置公共参数，非必须
                //1、可根据不同请求添加不同参数，每次发送请求前都会被回调
                //2、如果希望部分请求不回调这里，发请求前调用RxHttp#setAssemblyEnabled(false)即可
                val reqAuthorization = p.getHeader("Authorization")
                val ts = System.currentTimeMillis().toString()
                p.addHeader("versionName", versionName()) //添加公共参数
//                    .addHeader("Authorization", UserStorageUtil.getToken()) //添加公共请求头
                    .addHeader("CLIENT-TOC", "Y")
                    .addHeader("isShowToast", true.toString())
                    .addHeader("ts", ts)
                    .apply {
                        val method: Method = p.method
                        if (method.isGet) {
                            addHeader("method", "get")
                        } else if (method.isPost) { //Post请求
                            addHeader("method", "post")
                        }
                    }
//                    .addHeader("trade", UserStorageUtil.tradeToken) // 语言
//                    .addHeader("Accept-Language", AppUtil.getInterceptorLanHeaderStr())
                    .addHeader("zone", TimeZone.getDefault().id)
//                    .addHeader("sign", (UserStorageUtil.getToken() + ts).toMd5U32())
                    .addHeader("User-Agent", System.getProperty("http.agent"))
            }    //设置公共参数/请求头回调
    }

}