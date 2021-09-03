package com.church.punithaanthoniyar.bible

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.church.punithaanthoniyar.R
import com.church.punithaanthoniyar.base.BaseFragment
import com.church.punithaanthoniyar.databinding.BibleFragmentBinding

class BibleFragment: BaseFragment() {

    private lateinit var binding: BibleFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = BibleFragmentBinding.inflate(inflater,container,false)

        setScreenTitle(getString(R.string.bible))

        return binding.root
    }

    override fun init(view: View?) {
    }

    override fun initializeDi() {
    }

    override fun getMessageFromAliens() {
    }

    override fun setUpViews() {
    }
}