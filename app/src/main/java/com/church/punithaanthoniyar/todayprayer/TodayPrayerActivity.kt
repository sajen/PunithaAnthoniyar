package com.church.punithaanthoniyar.todayprayer

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.church.punithaanthoniyar.R
import com.church.punithaanthoniyar.base.BaseActivity
import com.church.punithaanthoniyar.databinding.ActivityTodayPrayerBinding
import com.church.punithaanthoniyar.databinding.HomeFragmentBinding
import com.church.punithaanthoniyar.utils.DateTimeUtils
import java.util.*

class TodayPrayerActivity : BaseActivity() {


    private lateinit var binding: ActivityTodayPrayerBinding

    override fun initializeDi() {

    }

    override fun setUpLayout() {
        binding = ActivityTodayPrayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun setUpViews() {

        setUpToolbar()

//        binding.prayerDate.text = DateTimeUtils.now(DateTimeUtils.DATE_GLOBAL_HYPHEN)

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
            toolbar.elevation = 0f

            setScreenTitle(getString(R.string.today_prayer))
        }
    }

    private fun openCalendar(){

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            // Display Selected date in textbox
//            binding.prayerDate.text = "$dayOfMonth $monthOfYear, $year"

        }, year, month, day)

        dpd.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.common_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.action_calendar)
            openCalendar()

        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }
}