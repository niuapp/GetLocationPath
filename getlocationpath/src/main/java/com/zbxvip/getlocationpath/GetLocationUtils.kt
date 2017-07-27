package com.zbxvip.getlocationpath

import android.content.Context
import android.widget.Toast
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener

/**
 * Created by niuapp on 2017/7/26 19:10.
 * Project : MyApplication.
 * Email : *******
 *   -->
 */

fun Toast.showToast(str: String) {
    this.setText(str)
    this.show()
}


//获取一个地址，需要一个监听回调
fun getLocation(context: Context, listener: AMapLocationListener): AMapLocationClient {
    //
    var mLocationClient: AMapLocationClient = AMapLocationClient(context)
    mLocationClient.setLocationListener(listener)

    var mLocationOption: AMapLocationClientOption = AMapLocationClientOption()
    mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy)
    mLocationOption.setInterval(3000)
    mLocationOption.isLocationCacheEnable = false

    mLocationClient.setLocationOption(mLocationOption)

    mLocationClient.startLocation()
    return mLocationClient
}

fun stopGetLocation(mLocationClient: AMapLocationClient?){
        mLocationClient?.stopLocation()
}
