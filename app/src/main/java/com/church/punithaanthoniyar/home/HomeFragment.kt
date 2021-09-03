package com.church.punithaanthoniyar.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.church.punithaanthoniyar.R
import com.church.punithaanthoniyar.base.BaseFragment
import com.church.punithaanthoniyar.databinding.HomeFragmentBinding
import com.opensooq.pluto.base.PlutoAdapter
import com.opensooq.pluto.listeners.OnItemClickListener
import com.opensooq.pluto.listeners.OnSlideChangeListener

class HomeFragment : BaseFragment() {

    private lateinit var binding:HomeFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = HomeFragmentBinding.inflate(inflater,container,false)

        setScreenTitle(getString(R.string.home))

        return binding.root
    }

    override fun init(view: View?) {
    }

    override fun initializeDi() {
    }

    override fun getMessageFromAliens() {

    }

    override fun setUpViews() {
        val pluto = binding.sliderView
        val adapter = SliderAdapter(getImages(), object : OnItemClickListener<Int> {
            override fun onItemClicked(item: Int?, position: Int) {
            }
        })

        pluto.create(adapter, lifecycle = lifecycle)
        pluto.setCustomIndicator(binding.customIndicator)
        pluto.setOnSlideChangeListener(object : OnSlideChangeListener {
            override fun onSlideChange(adapter: PlutoAdapter<*, *>, position: Int) {

            }
        })
    }

    private fun getImages(): MutableList<Int> {
        val items = mutableListOf<Int>()
        items.add(R.drawable.img_a)
        items.add(R.drawable.img_b)
        items.add(R.drawable.img_c)
        return items
    }
}