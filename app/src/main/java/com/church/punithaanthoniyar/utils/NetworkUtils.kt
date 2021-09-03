package com.church.punithaanthoniyar.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.telephony.PhoneStateListener
import android.telephony.SignalStrength
import android.telephony.TelephonyManager
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

/**
 * All Network Level utilities are defined here
 */
class NetworkUtils private constructor() {

    companion object {
        //    private static MyPhoneStateListener MyListener;
        private var signalStrength = ""
        private var level = -1

        /**
         * @param context Application Context
         * @return `true` if Connected, `false` if not connected
         */
        fun isNetworkConnected(context: Context): Boolean {
            try {
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val manager =
                        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    val capabilities =
                        manager.getNetworkCapabilities(manager.activeNetwork) // need ACCESS_NETWORK_STATE permission
                    capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                } else {
                    val cm =
                        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                    val activeNetwork = cm.activeNetworkInfo
                    activeNetwork != null && activeNetwork.isConnectedOrConnecting
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return false
        }

        fun isWifiNetworkConnected(context: Context): Boolean {
            try {
                val cm =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                return cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!.isConnected
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return false
        }

        val isInternetWorking: Boolean
            get() = try {
                val sock = Socket()
                sock.connect(InetSocketAddress("8.8.8.8", 53), 1500)
                sock.close()
                true
            } catch (e: IOException) {
                false
            }


        fun isConnectionFast(type: Int, subType: Int): Boolean {
            return if (type == ConnectivityManager.TYPE_WIFI) {
                true
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                when (subType) {
                    TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_GPRS -> false
                    TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_UMTS -> true
                    TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_HSPAP, TelephonyManager.NETWORK_TYPE_LTE -> true
                    TelephonyManager.NETWORK_TYPE_IDEN -> false
                    TelephonyManager.NETWORK_TYPE_UNKNOWN -> false
                    else -> false
                }
            } else {
                false
            }
        }
    }
}