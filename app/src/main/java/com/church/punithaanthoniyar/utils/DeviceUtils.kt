package tbm.church.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.BatteryManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * All Device level utility methods are defined here
 */
object DeviceUtils {
    var SEVEN_INCH_TAB = 0
    var EIGHT_INCH_TAB = 1
    var FIVE_INCH_MOBILE = 2
    var SIX_INCH_MOBILE = 3

    @SuppressLint("MissingPermission")
    fun getIMEINumber(context: Context): String {
        var deviceId = ""
        deviceId = try {
            val telephonyManager = context
                .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            telephonyManager.deviceId
        } catch (e: Exception) {
            return "0"
        }
        return deviceId ?: "0"
    }

    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

    /**
     * Get current battery percentage
     */
    fun getBatteryPercentage(context: Context): Int {
        val iFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = context.registerReceiver(null, iFilter)
        val level = batteryStatus?.getIntExtra(
            BatteryManager.EXTRA_LEVEL,
            -1
        ) ?: -1
        val scale = batteryStatus?.getIntExtra(
            BatteryManager.EXTRA_SCALE,
            -1
        ) ?: -1
        val batteryPct = level / scale.toFloat()
        return (batteryPct * 100).toInt()
    }

    /**
     * Method tells device factor
     *
     * @param activityContext
     * @return
     */
    fun isTabletDevice(activityContext: Context): Boolean {
        val device_large = activityContext.resources
            .configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_LARGE || activityContext
            .resources
            .configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_XLARGE
        if (device_large) {
            val metrics = DisplayMetrics()
            val activity = activityContext as Activity
            activity.windowManager.defaultDisplay.getMetrics(metrics)
            if (metrics.densityDpi == DisplayMetrics.DENSITY_DEFAULT || metrics.densityDpi == DisplayMetrics.DENSITY_HIGH || metrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM || metrics.densityDpi == DisplayMetrics.DENSITY_TV || metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH
            ) {
                return true
            }
        }
        return false
    }

    fun isCatalogDevice(activityContext: Context): Boolean {
        val metrics = DisplayMetrics()
        val activity = activityContext as Activity
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        val is7InchTablet = activityContext.getResources().configuration
            .isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_LARGE)
        return if (is7InchTablet) {
            true
        } else false
    }

    fun dpToPixel(context: Context, dp: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    fun getDeviceWidth(context: Context): Int {
        val displayMetrics = DisplayMetrics()
        val windowmanager =
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
        windowmanager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    fun getDisplayMetrics(context: Context): DisplayMetrics {
        return context.resources.displayMetrics
    }

    val isEmulator: Boolean
        get() = (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator"))

    fun getDisplayDiagonalHeight(context: Context): Int {
        val dm = DisplayMetrics()
        val windowmanager =
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
        windowmanager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val height = dm.heightPixels
        val wi = width.toDouble() / dm.xdpi.toDouble()
        val hi = height.toDouble() / dm.ydpi.toDouble()
        val x = Math.pow(wi, 2.0)
        val y = Math.pow(hi, 2.0)
        val screenInches =
            Math.round(Math.sqrt(x + y)).toDouble()
        if (screenInches == 7.0) return SEVEN_INCH_TAB else if (screenInches == 8.0) return EIGHT_INCH_TAB else if (screenInches == 5.0) return FIVE_INCH_MOBILE else if (screenInches == 6.0) return SIX_INCH_MOBILE
        return SEVEN_INCH_TAB
    }

    fun getDeviceMetrics(context: Context): DisplayMetrics {
        val metrics = DisplayMetrics()
        val wm =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        display.getMetrics(metrics)
        return metrics
    }
}