package com.church.punithaanthoniyar.about

import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.church.punithaanthoniyar.R
import com.church.punithaanthoniyar.base.BaseActivity
import com.church.punithaanthoniyar.databinding.ActivityAboutBinding

class AboutActivity : BaseActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun initializeDi() {
    }

    override fun setUpLayout() {
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun setUpViews() {
        setUpToolbar()

        binding.prayerDesc.movementMethod = ScrollingMovementMethod()
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

            setScreenTitle(getString(R.string.about))
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