package com.church.punithaanthoniyar.launch

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.church.punithaanthoniyar.R
import com.church.punithaanthoniyar.base.BaseActivity
import com.church.punithaanthoniyar.home.HomeActivity

class LaunchActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        Handler(Looper.getMainLooper()).postDelayed({
            val mIntent = Intent(this@LaunchActivity, HomeActivity::class.java)
            startActivity(mIntent)
            finish()
        }, 2000)
    }
}