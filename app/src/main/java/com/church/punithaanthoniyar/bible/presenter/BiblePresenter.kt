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

    override fun getChapterList(context : Context) {

        val chapters : MutableList<BibleChapter> = mutableListOf()

        try {
            val db = DBUtil(context, ApplicationConfigs.DB_NAME)
            db.createDataBase()
            db.openDataBase()

            val query = "SELECT bn,tn_f from t_bookkey"

            val c = db.selectSQL(query)
            if (c.count > 0) {
                while (c.moveToNext()) {
                    val bibleChapter = BibleChapter()
                    bibleChapter.chapterId = c.getInt(0)
                    bibleChapter.chapterName = c.getString(1).trim()

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