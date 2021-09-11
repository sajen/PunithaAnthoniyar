package com.church.punithaanthoniyar.masstiming

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.church.punithaanthoniyar.R
import com.church.punithaanthoniyar.base.BaseActivity
import com.church.punithaanthoniyar.databinding.ActivityMassTimingBinding
import com.church.punithaanthoniyar.databinding.ActivityTodayPrayerBinding

class MassTimingActivity : BaseActivity() {

    private lateinit var binding : ActivityMassTimingBinding
    override fun initializeDi() {
    }

    override fun setUpLayout() {
        binding = ActivityMassTimingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun setUpViews() {

        setUpToolbar()
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.toolbar.toolbar as Toolbar)
        val actionBar = supportActionBar
        actionBar?.let {toolbar ->
            toolbar.setDisplayHomeAsUpEnabled(true)
            toolbar.setDisplayShowHomeEnabled(true)
            toolbar.title = null
            toolbar.setIcon(null)
            toolbar.elevation = 0f

            setScreenTitle(getString(R.string.mass_timing))
        }
    }
}