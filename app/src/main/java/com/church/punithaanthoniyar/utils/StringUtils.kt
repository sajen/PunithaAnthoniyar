package com.church.punithaanthoniyar.utils

import android.text.TextUtils
import android.util.Patterns
import android.webkit.URLUtil
import java.util.regex.Pattern

object StringUtils {
    fun validRegex(pattern: String, str: String?): Boolean {
        if (pattern == "") {
            return true
        }
        val mPattern = Pattern.compile(pattern)
        val matcher = mPattern.matcher(str)

        // Entered text does not match the pattern
        return if (!matcher.matches()) {
            // It does not match partially too
            matcher.hitEnd()
        } else true
    }

    fun isEmptyString(text: String?): Boolean {
        return text == null || text.trim { it <= ' ' } == "null" || text.trim { it <= ' ' }
            .length <= 0
    }

    fun getStringQueryParam(data: String?): String? {
        return if (data != null) "'$data'" else null
    }

    fun getStringQueryParamQuotes(data: String?): String? {
        return if (data != null) "\"" + data + "\"" else null
    }

    fun isNullOrEmpty(text: String?): Boolean {
        return text == null || text.trim { it <= ' ' } == "null" || text.trim { it <= ' ' }
            .length <= 0
    }

    fun isValidEmail(target: CharSequence): Boolean {
        return !isNullOrEmpty(target.toString()) && Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        ).matcher(target).matches()
    }

    fun isValidURL(targetUrl: CharSequence): Boolean {
        return (!isNullOrEmpty(targetUrl.toString())
                && URLUtil.isValidUrl(targetUrl.toString())
                && Patterns.WEB_URL.matcher(targetUrl).matches())
    }

    fun isValidRegx(target: CharSequence?, regx: String): Boolean {
        if (regx == "") {
            return true
        }
        val value = regx.replace("\\<.*?\\>".toRegex(), "")
        return !TextUtils.isEmpty(target) && Pattern.compile(value).matcher(target)
            .matches()
    }

    /**
     * Removing single quotes from input string
     *
     * @param str - input string
     * @return formatted string
     */
    fun removeQuotes(str: String): String {
        return str.replace("'".toRegex(), "")
    }

    fun getImageName(imageName: String): String {
        val splitPath = imageName.split("/".toRegex()).toTypedArray()
        return splitPath[splitPath.size - 1]
    }
}