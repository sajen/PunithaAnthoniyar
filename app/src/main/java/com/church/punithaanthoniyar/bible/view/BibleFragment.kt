package com.church.punithaanthoniyar.bible.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.church.punithaanthoniyar.R
import com.church.punithaanthoniyar.base.BaseFragment
import com.church.punithaanthoniyar.bible.IBibleContract
import com.church.punithaanthoniyar.bible.model.BibleChapter
import com.church.punithaanthoniyar.bible.presenter.BiblePresenter
import com.church.punithaanthoniyar.databinding.BibleFragmentBinding

class BibleFragment: BaseFragment(),IBibleContract.IBibleViewContract {

    private lateinit var binding: BibleFragmentBinding

    private lateinit var presenter : BiblePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = BibleFragmentBinding.inflate(inflater,container,false)

        setScreenTitle(getString(R.string.bible))

        presenter = BiblePresenter()

        presenter.setScreenView(this)

        return binding.root
    }

    override fun init(view: View?) {
    }

    override fun initializeDi() {
    }

    override fun getMessageFromAliens() {
    }

    lateinit var adapter : BibleListAdapter
    val data : MutableList<BibleChapter> = mutableListOf()

    override fun setUpViews() {

        val recyclerview = binding.recyclerView

        recyclerview.layoutManager = LinearLayoutManager(activity)

        adapter = BibleListAdapter(data)
        recyclerview.adapter = adapter

        presenter.getChapterList(requireActivity())

    }

    override fun updateList(chapterList: MutableList<BibleChapter>) {

        data.addAll(chapterList)
        adapter.notifyDataSetChanged()
    }
}