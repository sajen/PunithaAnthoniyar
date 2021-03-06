package com.church.punithaanthoniyar.bible.presenter

import android.content.Context
import com.church.punithaanthoniyar.base.ApplicationConfigs
import com.church.punithaanthoniyar.bible.IBibleContract
import com.church.punithaanthoniyar.bible.model.BibleChapter
import com.church.punithaanthoniyar.home.IHomeContract
import com.church.punithaanthoniyar.home.model.Home
import com.church.punithaanthoniyar.utils.Commons
import com.church.punithaanthoniyar.utils.DBUtil
import com.church.punithaanthoniyar.utils.FirebaseUtil
import com.google.firebase.firestore.DocumentSnapshot
import tbm.church.AppConstants.Companion.FIRESTORE_BASE_PATH
import tbm.church.AppConstants.Companion.FIRESTORE_HOME
import com.google.firebase.storage.StorageReference
import tbm.church.AppConstants.Companion.FIREBASE_HOME_URL
import java.lang.Exception


class BiblePresenter : IBibleContract.IBiblePresenterContract {

    lateinit var view : IBibleContract.IBibleViewContract

    override fun setScreenView (view : IBibleContract.IBibleViewContract){
        this.view = view
    }

    override fun getChapterList(context : Context,pos : Int) {

        val chapters : MutableList<BibleChapter> = mutableListOf()

        val testamentVer = if(pos == 0) "OLD" else "NEW"

        try {
            val db = DBUtil(context, ApplicationConfigs.DB_NAME)
            db.createDataBase()
            db.openDataBase()

            val query = "SELECT bn,tn_f,chapters_count from t_bookkey where testament_version = '$testamentVer'"

            val c = db.selectSQL(query)
            if (c.count > 0) {
                while (c.moveToNext()) {
                    val bibleChapter = BibleChapter()
                    bibleChapter.chapterId = c.getLong(0)
                    bibleChapter.chapterName = c.getString(1).trim()
                    bibleChapter.count = c.getInt(2)

                    chapters.add(bibleChapter)
                }
                c.close()
            }

            db.closeDB()

        } catch (e: Exception) {
            Commons.printException(e)
        }

        view.updateList(chapters)

    }

    override fun getChapterVerseList(context : Context,bookId : String,chapterId : String) {

        val chapters : MutableList<BibleChapter> = mutableListOf()

        var chapterNumberId = "001"

        chapterNumberId = when {
            chapterId.toInt() < 9 -> "00"
            chapterId.toInt() in 10..99 -> "0"
            else -> ""
        }

        val chapterActualId = ("$chapterNumberId$chapterId")

        val chapterVerseId : String = "$bookId$chapterActualId"+"001"
        val chapterToVerseId : String = "$bookId$chapterNumberId"+(chapterId.toInt()+1).toString()+"001"
        try {
            val db = DBUtil(context, ApplicationConfigs.DB_NAME)
            db.createDataBase()
            db.openDataBase()

            val query = "SELECT verse,id from t_verses where id >= $chapterVerseId and id < $chapterToVerseId"

            val c = db.selectSQL(query)
            if (c.count > 0) {
                while (c.moveToNext()) {
                    val bibleChapter = BibleChapter()
                    bibleChapter.chapterVerses = c.getString(0)
                    bibleChapter.chapterId = c.getLong(1)

                    chapters.add(bibleChapter)
                }
                c.close()
            }

            db.closeDB()

        } catch (e: Exception) {
            Commons.printException(e)
        }

        view.updateList(chapters)

    }


}