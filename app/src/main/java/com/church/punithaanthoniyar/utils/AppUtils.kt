package com.church.punithaanthoniyar.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object AppUtils {
    fun getApplicationVersionName(context: Context): String {
        var versionName = ""
        try {
            val pinfo = context.packageManager.getPackageInfo(
                context.packageName, 0
            )
            versionName = pinfo.versionName
        } catch (e: Exception) {
            Commons.printException("" + e)
        }
        return versionName
    }

    // *****************************************************
    fun getApplicationVersionNumber(context: Context): String {
        var versionNumber = 0
        try {
            val pinfo = context.packageManager.getPackageInfo(
                context.packageName, 0
            )
            versionNumber = pinfo.versionCode
        } catch (e: Exception) {
            Commons.printException("" + e)
        }
        return versionNumber.toString() + ""
    }

    /**
     * Hide kye board
     *
     * @param focusedView
     * @param context
     */
    fun hideKeyboard(
        focusedView: View?,
        context: Context
    ) {
        if (focusedView != null) {
            val imm =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(focusedView.windowToken, 0)
        }
    }
}