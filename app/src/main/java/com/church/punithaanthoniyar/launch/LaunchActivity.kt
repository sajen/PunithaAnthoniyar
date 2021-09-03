package com.church.punithaanthoniyar.launch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.church.punithaanthoniyar.R
import com.church.punithaanthoniyar.base.BaseActivity
import com.church.punithaanthoniyar.home.HomeActivity
import android.preference.PreferenceManager

import android.content.SharedPreferences
import java.util.*


class LaunchActivity : BaseActivity() {
    override fun initializeDi() {
    }

    override fun setUpLayout() {
    }

    override fun setUpViews() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPrefFile = "languageSharedPref"
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)

        val lang = sharedPreferences.getString("Language","en")

        setLocale(lang.toString())

        setContentView(R.layout.activity_launch)

        Handler(Looper.getMainLooper()).postDelayed({
            val mIntent = Intent(this@LaunchActivity, HomeActivity::class.java)
            startActivity(mIntent)
            finish()
        }, 2000)
    }

    fun setLocale(lang : String){

        Locale(lang)
    }
}