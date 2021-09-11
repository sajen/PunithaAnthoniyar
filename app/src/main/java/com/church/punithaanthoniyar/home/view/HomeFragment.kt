package com.church.punithaanthoniyar.home.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.church.punithaanthoniyar.R
import com.church.punithaanthoniyar.about.AboutActivity
import com.church.punithaanthoniyar.base.BaseFragment
import com.church.punithaanthoniyar.databinding.HomeFragmentBinding
import com.church.punithaanthoniyar.home.IHomeContract
import com.church.punithaanthoniyar.home.presenter.HomeFragmentPresenter
import com.church.punithaanthoniyar.masstiming.MassTimingActivity
import com.church.punithaanthoniyar.todayprayer.TodayPrayerActivity
import com.google.firebase.storage.StorageReference
import com.opensooq.pluto.base.PlutoAdapter
import com.opensooq.pluto.listeners.OnItemClickListener
import com.opensooq.pluto.listeners.OnSlideChangeListener

class HomeFragment : BaseFragment(),IHomeContract.IHomeViewContract {

    private lateinit var binding:HomeFragmentBinding

    private var imageList = arrayListOf<String>()

    private lateinit var presenter : HomeFragmentPresenter

    private lateinit var adapter : SliderAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = HomeFragmentBinding.inflate(inflater,container,false)

        setScreenTitle(getString(R.string.home))

        presenter = HomeFragmentPresenter()

        presenter.setScreenView(this)

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
        adapter = SliderAdapter(imageList, object : OnItemClickListener<String> {
            override fun onItemClicked(item: String?, position: Int) {
            }
        })

        pluto.create(adapter, lifecycle = lifecycle)

        pluto.setCustomIndicator(binding.customIndicator)

        binding.prayerLayout.setOnClickListener {
            val mIntent = Intent(activity, TodayPrayerActivity::class.java)
            startActivity(mIntent)
        }

        binding.aboutLayout.setOnClickListener {
            val mIntent = Intent(activity, AboutActivity::class.java)
            startActivity(mIntent)
        }

        binding.massLayout.setOnClickListener {
            val mIntent = Intent(activity, MassTimingActivity::class.java)
            startActivity(mIntent)
        }

        presenter.getImageListFromFirestore()
    }

    private fun getImages(): MutableList<Int> {
        val items = mutableListOf<Int>()
        items.add(R.drawable.img_a)
        items.add(R.drawable.img_b)
        items.add(R.drawable.img_c)
        return items
    }

    override fun updateImage(imageList: ArrayList<String>) {
        this.imageList.addAll(imageList)

        adapter.notifyDataSetChanged()
    }
}