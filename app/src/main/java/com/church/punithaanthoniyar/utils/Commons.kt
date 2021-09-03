package com.church.punithaanthoniyar.utils

import android.util.Log
import com.church.punithaanthoniyar.BuildConfig
import tbm.church.AppConstants

object Commons {
    const val DATE_TIME = 5
    const val DATE = 6
    fun print(message: String?) {}
    fun printException(message: String?, e: Throwable?) {
        Log.e(AppConstants.LOG, message, e)
    }

    fun printException(e: Throwable?) {
        Log.e(AppConstants.LOG, "~", e)
    }

    @JvmStatic
    fun printException(message: String?) {
        Log.e(AppConstants.LOG, message.toString())
    }

    fun printInformation(message: String?) {
        if (BuildConfig.DEBUG) Log.i(AppConstants.LOG, message.toString())
    }
}