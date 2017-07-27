package com.zbxvip.getlocationpath

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.MapView
import com.amap.api.maps2d.UiSettings
import com.amap.api.maps2d.model.CameraPosition
import com.amap.api.maps2d.model.LatLng
import com.amap.api.maps2d.model.PolylineOptions

class MainActivity : AppCompatActivity() {

    lateinit var mapView:MapView
    lateinit var aMap:AMap
    lateinit var toast: Toast
    var mLocationClient:AMapLocationClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT)

        mapView = findViewById(R.id.mapView) as MapView
        mapView.onCreate(savedInstanceState)

        mapView.post { initMap() }

    }

    var preLatLng:LatLng? = null;
    /**
     * 初始化地图
     */
    fun initMap() {
        aMap = mapView.map
        var uiSettings: UiSettings = aMap.uiSettings
        uiSettings.isZoomControlsEnabled = false
        uiSettings.isZoomGesturesEnabled = true

        //重置记录
        findViewById(R.id.resetButton).setOnClickListener { stopGetLocation(mLocationClient);aMap.clear() }

        //停止记录
        findViewById(R.id.stopButton).setOnClickListener { stopGetLocation(mLocationClient) }

        //开始记录
        findViewById(R.id.startButton).setOnClickListener {

            //初始化线段
            var polylineOptions: PolylineOptions = PolylineOptions().width(10f).color(Color.argb(55, 255, 1, 1))

            mLocationClient = getLocation(this, AMapLocationListener {
                if (it.errorCode == 0){
                    toast.showToast("${it.latitude} - ${it.longitude}")

                    //经纬度信息
                    var latlng: LatLng = LatLng(it.latitude, it.longitude)
//                    var latlng: LatLng = LatLng(33.453453, 119.711761)
//                    var latlng_1: LatLng = LatLng(33.452018, 119.75914)
//                    var latlng: LatLng = LatLng(33.449729, 119.745063)
//                    var latlng: LatLng = LatLng(33.433544, 119.755882)
//                    var latlng: LatLng = LatLng(33.419502, 119.712966)
//                    var latlng: LatLng = LatLng(33.453739, 119.705238)


                    //TODO 每次收到新位置 就全部清空，重新按新路径点画线 待
                    aMap.clear()
                    polylineOptions.add(latlng)
                    aMap.addPolyline(polylineOptions)

                    aMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition(latlng, 17f, 0f, 0f)), 1000, object :AMap.CancelableCallback{
                        override fun onCancel() {

                        }
                        override fun onFinish() {

                        }

                    })
                }
            }) }

    }

    /**
     * 方法必须重写
     */
     override fun onResume() {
        super.onResume();
        mapView.onResume();
    }


    /**
     * 方法必须重写
     */
    override fun onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    override fun onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
