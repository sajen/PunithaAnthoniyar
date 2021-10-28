package com.church.punithaanthoniyar.verseofday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.church.punithaanthoniyar.R
import com.church.punithaanthoniyar.base.BaseActivity
import com.church.punithaanthoniyar.databinding.ActivityAboutBinding
import com.church.punithaanthoniyar.databinding.ActivityVerseOfDayBinding
import com.google.android.material.tabs.TabLayout

class VerseOfDayActivity : BaseActivity() {

    private lateinit var binding: ActivityVerseOfDayBinding

    override fun initializeDi() {

    }

    override fun setUpLayout() {
        binding = ActivityVerseOfDayBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun setUpViews() {
        setUpToolbar()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

    }

    private fun setTabViews(){

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

            setScreenTitle(getString(R.string.verse_of_day))
        }
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