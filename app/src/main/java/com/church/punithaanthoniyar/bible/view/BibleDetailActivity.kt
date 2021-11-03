package com.church.punithaanthoniyar.bible.view

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.church.punithaanthoniyar.R
import com.church.punithaanthoniyar.base.BaseActivity
import com.church.punithaanthoniyar.bible.IBibleContract
import com.church.punithaanthoniyar.bible.model.BibleChapter
import com.church.punithaanthoniyar.bible.presenter.BiblePresenter
import com.church.punithaanthoniyar.databinding.ActivityBibleDetailBinding
import com.church.punithaanthoniyar.databinding.ActivityVerseOfDayBinding
import com.church.punithaanthoniyar.utils.Commons

class BibleDetailActivity : BaseActivity(), IBibleContract.IBibleViewContract {

    private lateinit var binding: ActivityBibleDetailBinding

    lateinit var adapter : ChapterListAdapter
    val data : MutableList<BibleChapter> = mutableListOf()

    private lateinit var presenter : BiblePresenter

    private var bookId = ""
    private var bookName = ""
    private var chapterCount = 0

    override fun initializeDi() {
    }

    override fun setUpLayout() {
        binding = ActivityBibleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun setUpViews() {

        presenter = BiblePresenter()

        presenter.setScreenView(this)

        try {
            val extras = intent.extras
            if (extras != null) {
                bookId = extras.get("BookId").toString()
                bookName = extras.get("BookName").toString()
                chapterCount = extras.get("ChapterCount") as Int
            }
        }catch (e :Exception){
            Commons.printException(e)
        }

        setUpToolbar()

        val recyclerview = binding.recyclerView

        recyclerview.layoutManager = LinearLayoutManager(this)

        adapter = ChapterListAdapter(this,data)
        recyclerview.adapter = adapter

        presenter.getChapterVerseList(this,bookId,"1" )

    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.toolbar.toolbar as Toolbar)
        val actionBar = supportActionBar
        actionBar?.let {toolbar ->
            toolbar.setDisplayHomeAsUpEnabled(true)
            toolbar.setDisplayShowHomeEnabled(true)
            toolbar.title = null
            toolbar.setIcon(null)
            toolbar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
            toolbar.elevation = 0f

            setScreenTitle(if(bookName.isNotEmpty())bookName else getString(R.string.bible))
        }
    }

    override fun updateList(chapterList: MutableList<BibleChapter>) {

        data.clear()
        data.addAll(chapterList)
        adapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }
}