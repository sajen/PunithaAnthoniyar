package com.church.punithaanthoniyar.saintofday

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.church.punithaanthoniyar.R
import com.church.punithaanthoniyar.base.BaseActivity
import com.church.punithaanthoniyar.databinding.ActivitySaintOfDayBinding
import com.church.punithaanthoniyar.databinding.ActivityVerseOfDayBinding
import com.church.punithaanthoniyar.saintofday.model.Saint
import java.util.*
import kotlin.collections.ArrayList

class SaintOfDayActivity : BaseActivity() {

    private lateinit var binding: ActivitySaintOfDayBinding

    override fun initializeDi() {

    }

    override fun setUpLayout() {
        binding = ActivitySaintOfDayBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun setUpViews() {
        setUpToolbar()

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        recyclerview.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<Saint>()

        for (i in 1..20) {
            data.add(Saint("Saint $i","Mid 2nd Century","235 AD",getString(R.string.saint_about_dummy)))
        }

        val adapter = SaintOfDayAdapter(this,data)
        recyclerview.adapter = adapter
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

            setScreenTitle(getString(R.string.saint_of_day))
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