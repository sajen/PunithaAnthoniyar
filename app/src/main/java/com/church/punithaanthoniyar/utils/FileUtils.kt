package com.church.punithaanthoniyar.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.StatFs
import androidx.core.content.FileProvider
import com.church.punithaanthoniyar.BuildConfig
import com.church.punithaanthoniyar.utils.Commons.printException
import java.io.*

object FileUtils {
    var photoFolderPath: String? = null
    var TEXT_FILE_EXT = ".txt"
    var HTML_FILE_EXT = ".html"

    /**
     * @return `true`` if external storage available else `false``
    `` */
    fun isExternalStorageAvailable(mb: Int): Boolean {
        val stat = StatFs(
            Environment.getExternalStorageDirectory()
                .path
        )
        val sdAvailSize = (stat.availableBlocks.toDouble()
                * stat.blockSize.toDouble())
        // One binary gigabyte equals 1,073,741,824 bytes.
        val mbAvailable = sdAvailSize / 1048576
        val state = Environment.getExternalStorageState()
        var mExternalStorageAvailable = false
        var mExternalStorageWriteable = false
        if (Environment.MEDIA_MOUNTED == state) {
            // We can read and write the media
            mExternalStorageWriteable = true
            mExternalStorageAvailable = mExternalStorageWriteable
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY == state) {
            // We can only read the media
            mExternalStorageAvailable = true
        } else {
            // Something else is wrong. It may be one of many other states, but
            // all we need
            // to know is we can neither read nor write
            mExternalStorageWriteable = false
            mExternalStorageAvailable = mExternalStorageWriteable
        }
        return (mExternalStorageAvailable
                && mExternalStorageWriteable && mbAvailable > mb)
    }

    /**
     * To check file availability
     *
     * @param path File path
     * @return Availability
     */
    fun isFileExisting(path: String?): Boolean {
        val f = File(path)
        return f.exists()
    }

    /**
     * Getting file URI
     *
     * @param path File path
     * @return URI
     */
    fun getUriFromFile(mContext: Context?, path: String?): Uri {
        val f = File(path)
        return if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(
                mContext!!,
                BuildConfig.APPLICATION_ID.toString() + ".provider",
                f
            )
        } else {
            Uri.fromFile(f)
        }
    }

    fun checkFileExist(
        imageName: String,
        retailerID: String,
        isLatLongImage: Boolean
    ) {
        try {
            var fName = if (!isLatLongImage) "PRO_" else "LATLONG_"
            fName += retailerID
            val sourceDir = File(photoFolderPath)
            val files = sourceDir.listFiles()
            for (file in files) {
                if (file.name.startsWith(fName) &&
                    file.name != imageName
                ) file.delete()
            }
        } catch (e: Exception) {
            printException(e)
        }
    }

    fun deleteFiles(folderPath: String?, fnamesStarts: String?) {
        val folder = File(folderPath)
        val files = folder.listFiles()
        if (files != null && files.size >= 1) {
            for (tempFile in files) {
                if (tempFile != null) {
                    if (tempFile.name.startsWith(fnamesStarts!!)) tempFile.delete()
                }
            }
        }
    }

    /*
     * It returns true if the folder contains the n or more than n files
     * which starts name fnameStarts otherwiese returns false;
     */
    fun checkForNFilesInFolder(
        folderPath: String?, n: Int,
        fNameStarts: String?
    ): Boolean {
        if (fNameStarts == null) return false
        if (n < 1) return true
        val folder = File(folderPath)
        if (!folder.exists()) {
            return false
        } else {
            val fnames = folder.list()
            if (fnames == null || fnames.size < n) {
                return false
            } else {
                var count = 0
                for (str in fnames) {
                    if (str != null && fNameStarts != "" && str.length > 0) {
                        if (str.startsWith(fNameStarts)) {
                            count++
                        }
                    }
                    if (count == n) {
                        return true
                    }
                }
            }
        }
        return false
    }

    /**
     * @param uri
     * The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri
            .authority
    }

    /**
     * @param uri
     * The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri
            .authority
    }

    /**
     * @param uri
     * The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri
            .authority
    }

    /**
     * @param uri
     * The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri
            .authority
    }

    fun readFile(
        context: Context,
        fileName: String,
        folder: String,
        filePath: String
    ): String {
        val path: String
        path = if (filePath == "") context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            .toString() + "/" + folder + "/" else "$filePath/$folder/"
        val file = File(path + fileName)
        val sb = StringBuilder()
        var br: BufferedReader? = null
        try {
            br = BufferedReader(FileReader(file))
            var st: String?
            while (br.readLine().also { st = it } != null) {
                sb.append(st)
                sb.append("\n")
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return sb.toString()
    }

    fun getFile(
        context: Context,
        fileName: String,
        folder: String,
        filePath: String
    ): File {
        val path: String
        path = if (filePath == "") context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            .toString() + "/" + folder + "/" else "$filePath/$folder/"
        return File(path + fileName)
    }

    fun getStorageDir(folderName: String?): File {
        val docsFolder =
            File(Environment.getExternalStorageDirectory(), folderName)
        if (!docsFolder.exists()) {
            docsFolder.mkdir()
        }
        return docsFolder
    }
}