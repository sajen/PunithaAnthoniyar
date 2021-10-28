package com.church.punithaanthoniyar.bible

import android.content.Context
import com.church.punithaanthoniyar.bible.model.BibleChapter

interface IBibleContract {

    interface IBibleViewContract{
        fun updateList(chapterList : MutableList<BibleChapter>)
    }

    interface IBiblePresenterContract{
        fun setScreenView (view : IBibleContract.IBibleViewContract)
        fun getChapterList(context : Context)
    }
}