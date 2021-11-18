package com.church.punithaanthoniyar.bible.view

import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.church.punithaanthoniyar.R
import com.church.punithaanthoniyar.base.BaseActivity
import com.church.punithaanthoniyar.bible.IBibleContract
import com.church.punithaanthoniyar.bible.model.BibleChapter
import com.church.punithaanthoniyar.bible.presenter.BiblePresenter
import com.church.punithaanthoniyar.databinding.ActivityBibleDetailBinding
import com.church.punithaanthoniyar.utils.Commons

class BibleDetailActivity : BaseActivity(), IBibleContract.IBibleViewContract {

    private lateinit var binding: ActivityBibleDetailBinding

    lateinit var adapter : ChapterListAdapter
    val data : MutableList<BibleChapter> = mutableListOf()

    private lateinit var presenter : BiblePresenter

    private var bookId = ""
    private var bookName = ""
    private var chapterCount = 0
    private var chapterId = "1";

    private var isPopupShown = false

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

        val chapterList = mutableListOf<String>()

        for (i in 1..chapterCount){
            chapterList.add("அதிகாரம் $i")
        }

        val chapterAdapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, chapterList)
        chapterAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        binding.chapterSpinner.setAdapter(chapterAdapter)

        if (chapterList.isNotEmpty())
            binding.chapterSpinner.setText(chapterList[0],false)

        /*binding.chapterSpinner.setOnTouchListener { _, event ->

            if (binding.chapterSpinner.isShown)
                binding.chapterSpinner.dismissDropDown()
            else {
                when (event.action) {
                    MotionEvent.ACTION_UP ->
                        binding.chapterSpinner.showDropDown()
                }
            }
            true
        }*/

        binding.chapterSpinner.setOnClickListener {
            isPopupShown = if (isPopupShown) {
                binding.chapterSpinner.dismissDropDown()
                false
            }else {
                binding.chapterSpinner.showDropDown()
                true
            }
        }

        binding.chapterSpinner.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->

            chapterId = (i+1).toString()
            presenter.getChapterVerseList(this,bookId,chapterId )
        }

        val recyclerview = binding.recyclerView

        recyclerview.layoutManager = LinearLayoutManager(this)

        adapter = ChapterListAdapter(this,data)
        recyclerview.adapter = adapter


        presenter.getChapterVerseList(this,bookId,chapterId )

        binding.previous.setOnClickListener{
            if (chapterId.toInt() == 1){

            }else {
                chapterId = (chapterId.toInt() - 1).toString()

                binding.chapterSpinner.setText(chapterList[chapterId.toInt() - 1],false)
                presenter.getChapterVerseList(this, bookId, chapterId)
            }
        }

        binding.next.setOnClickListener{

            if (chapterCount == chapterId.toInt()){

            }else {
                chapterId = (chapterId.toInt() + 1).toString()

                binding.chapterSpinner.setText(chapterList[chapterId.toInt() - 1],false)
                presenter.getChapterVerseList(this, bookId, chapterId)
            }
        }

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