package com.epro.mall.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.SystemClock
import android.text.TextUtils
import android.view.animation.BounceInterpolator
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationQualityReport
import com.amap.api.maps.AMapUtils
import com.amap.api.maps.Projection
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.LatLngBounds
import com.amap.api.maps.model.Marker
import com.epro.mall.R
import com.mike.baselib.utils.AppUtils
import com.mike.baselib.utils.Constants
import com.mike.baselib.utils.ToastUtil
import java.text.SimpleDateFormat
import java.util.*

class MallMapUtils {
    companion object {
        /**
         * 根据定位结果返回定位信息的字符串
         * @param location
         * @return
         */
        @Synchronized
        fun getLocationStr(location: AMapLocation?): String? {
            if (null == location) {
                return null
            }
            val sb = StringBuffer()
            //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
            if (location.errorCode == 0) {
                sb.append("定位成功" + "\n")
                sb.append("定位类型: " + location.locationType + "\n")
                sb.append("经    度    : " + location.longitude + "\n")
                sb.append("纬    度    : " + location.latitude + "\n")
                sb.append("精    度    : " + location.accuracy + "米" + "\n")
                sb.append("提供者    : " + location.provider + "\n")

                sb.append("速    度    : " + location.speed + "米/秒" + "\n")
                sb.append("角    度    : " + location.bearing + "\n")
                // 获取当前提供定位服务的卫星个数
                sb.append("星    数    : " + location.satellites + "\n")
                sb.append("国    家    : " + location.country + "\n")
                sb.append("省            : " + location.province + "\n")
                sb.append("市            : " + location.city + "\n")
                sb.append("城市编码 : " + location.cityCode + "\n")
                sb.append("区            : " + location.district + "\n")
                sb.append("区域 码   : " + location.adCode + "\n")
                sb.append("地    址    : " + location.address + "\n")
                sb.append("兴趣点    : " + location.poiName + "\n")
                //定位完成的时间
                sb.append("定位时间: " + formatUTC(location.time, "yyyy-MM-dd HH:mm:ss") + "\n")
            } else {
                //定位失败
                sb.append("定位失败" + "\n")
                sb.append("错误码:" + location.errorCode + "\n")
                sb.append("错误信息:" + location.errorInfo + "\n")
                sb.append("错误描述:" + location.locationDetail + "\n")
            }
            sb.append("***定位质量报告***").append("\n")
            sb.append("* WIFI开关：").append(if (location.locationQualityReport.isWifiAble) "开启" else "关闭").append("\n")
            sb.append("* GPS状态：").append(getGPSStatusString(location.locationQualityReport.gpsStatus)).append("\n")
            sb.append("* GPS星数：").append(location.locationQualityReport.gpsSatellites).append("\n")
            sb.append("* 网络类型：" + location.locationQualityReport.networkType).append("\n")
            sb.append("* 网络耗时：" + location.locationQualityReport.netUseTime).append("\n")
            sb.append("****************").append("\n")
            //定位之后的回调时间
            sb.append("回调时间: " + formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n")
            return sb.toString()
        }

        private var sdf: SimpleDateFormat? = null

        fun formatUTC(l: Long, strPattern: String): String {
            var strPattern = strPattern
            if (TextUtils.isEmpty(strPattern)) {
                strPattern = "yyyy-MM-dd HH:mm:ss"
            }
            if (sdf == null) {
                try {
                    sdf = SimpleDateFormat(strPattern, Locale.CHINA)
                } catch (e: Throwable) {
                }

            } else {
                sdf!!.applyPattern(strPattern)
            }
            return if (sdf == null) "NULL" else sdf!!.format(l)
        }

        /**
         * 获取app的名称
         * @param context
         * @return
         */
        fun getAppName(context: Context): String {
            var appName = ""
            try {
                val packageManager = context.packageManager
                val packageInfo = packageManager.getPackageInfo(
                        context.packageName, 0)
                val labelRes = packageInfo.applicationInfo.labelRes
                appName = context.resources.getString(labelRes)
            } catch (e: Throwable) {
                e.printStackTrace()
            }

            return appName
        }

        /**
         * 获取GPS状态的字符串
         * @param statusCode GPS状态码
         * @return
         */
        private fun getGPSStatusString(statusCode: Int): String {
            var str = ""
            when (statusCode) {
                AMapLocationQualityReport.GPS_STATUS_OK -> str = "GPS状态正常"
                AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER -> str = "手机中没有GPS Provider，无法进行GPS定位"
                AMapLocationQualityReport.GPS_STATUS_OFF -> str = "GPS关闭，建议开启GPS，提高定位质量"
                AMapLocationQualityReport.GPS_STATUS_MODE_SAVING -> str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量"
                AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION -> str = "没有GPS定位权限，建议开启gps定位权限"
            }
            return str
        }

        fun test() {
            val b = ByteArray(1000000000)
        }


        /**
         * 打开高德地图
         *
         * @author jack
         * created at 2017/8/2 15:01
         */
        fun openGaoDeMap(context: Context, dlat: Double, dlon: Double, dname: String) {
            if (!AppUtils.isAvilible(context, Constants.MAP_GAODE)) {
                ToastUtil.showToast("未安装高德地图")
                return
            }
            try {
                // APP_NAME  自己应用的名字
                val uri = getGdMapUri(context.getString(R.string.app_name), "", "", "",
                        dlat.toString(),
                        dlon.toString(),
                        dname)
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setPackage(Constants.MAP_GAODE)
                intent.data = Uri.parse(uri)
                context.startActivity(intent) //启动调用
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        /**
         * 获取打开高德地图应用uri
         * style
         * 导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5
         * 不走高速且避免收费；6 不走高速且躲避拥堵；
         * 7 躲避收费和拥堵；8 不走高速躲避收费和拥堵)
         */
        private fun getGdMapUri(appName: String, slat: String, slon: String, sname: String, dlat: String, dlon: String, dname: String): String {
            val newUri = "androidamap://navi?sourceApplication=%1\$s&poiname=%2\$s&lat=%3\$s&lon=%4\$s&dev=1&style=2"
            return String.format(newUri, appName, dname, dlat, dlon)
        }


        /**
         * 打开百度地图
         *
         * @param slat  开始地点 维度
         * @param slon  开始地点 经度
         * @param sname 开始地点 名字
         * @param dlat  终点地点 维度
         * @param dlon  终点地点 经度
         * @param dname 终点名字
         * @param city  所在城市- 动态获取 （例如：北京市）
         * @author jack
         * created at 2017/8/2 15:01
         */
        fun openBaiduMap(context: Context,
                         dlat: Double, dlon: Double, dname: String) {
            if (!AppUtils.isAvilible(context, Constants.MAP_BAIDU)) {
                ToastUtil.showToast("未安装百度地图")
                return
            }
            try {
                val uri = getBaiduMapUri("", "", "",
                        dlat.toString(), dlon.toString(), dname, "", "")
                val intent = Intent.parseUri(uri, 0)
                context.startActivity(intent) //启动调用
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        fun calculateLineDistance(la1:Double,lo1:Double,la2:Double,lo2:Double): Float {
            return AMapUtils.calculateLineDistance(LatLng(la1,lo1),LatLng(la2,lo2))
        }

        private fun getBaiduMapUri(originLat: String, originLon: String, originName: String, desLat: String, desLon: String, destination: String, region: String, src: String): String {
            //        String uri = "intent://map/direction?origin=latlng:%1$s,%2$s|name:%3$s" +
            //                "&destination=latlng:%4$s,%5$s|name:%6$s&mode=driving&region=%7$s&src=%8$s#Intent;" +
            //                "scheme=bdapp;package=com.baidu.BaiduMap;end";
            val uri = "intent://map/direction?destination=latlng:%1\$s,%2\$s|name:%3\$s&mode=driving&region=%4\$s&src=%5\$s#Intent;" + "scheme=bdapp;package=com.baidu.BaiduMap;end"
            return String.format(uri, desLat, desLon, destination, region, src)
        }

        /**
         * marker点击时跳动一下
         */
        fun jumpPoint(marker: Marker, projection: Projection) {
            val handler = Handler()
            val start = SystemClock.uptimeMillis()
            val proj = projection
            val markerLatlng = marker.position
            val markerPoint = proj.toScreenLocation(markerLatlng)
            markerPoint.offset(0, -100)
            val startLatLng = proj.fromScreenLocation(markerPoint)
            val duration: Long = 1500

            val interpolator = BounceInterpolator()
            handler.post(object : Runnable {
                override fun run() {
                    val elapsed = SystemClock.uptimeMillis() - start
                    val t = interpolator.getInterpolation(elapsed.toFloat() / duration)
                    val lng = t * markerLatlng.longitude + (1 - t) * startLatLng.longitude
                    val lat = t * markerLatlng.latitude + (1 - t) * startLatLng.latitude
                    marker.position = LatLng(lat, lng)
                    if (t < 1.0) {
                        handler.postDelayed(this, 16)
                    }
                }
            })
        }

        /**
         * 根据中心点和自定义内容获取缩放bounds
         */
        fun getLatLngBounds(centerpoint: LatLng?, pointList: List<LatLng>): LatLngBounds {
            val b = LatLngBounds.builder()
            if (centerpoint != null) {
                for (i in pointList.indices) {
                    val p = pointList[i]
                    val p1 = LatLng(centerpoint.latitude * 2 - p.latitude, centerpoint.longitude * 2 - p.longitude)
                    b.include(p)
                    b.include(p1)
                }
            }
            return b.build()
        }


        /**
         * 根据自定义内容获取缩放bounds
         */
        fun getLatLngBounds(pointList: List<LatLng>): LatLngBounds {
            val b = LatLngBounds.builder()
            for (i in pointList.indices) {
                val p = pointList[i]
                b.include(p)
            }
            return b.build()
        }
    }
}