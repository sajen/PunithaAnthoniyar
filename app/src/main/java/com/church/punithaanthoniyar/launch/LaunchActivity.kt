package com.church.punithaanthoniyar.launch

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.church.punithaanthoniyar.R
import com.church.punithaanthoniyar.base.BaseActivity
import com.church.punithaanthoniyar.home.view.HomeActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
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

        val sharedPrefToken = "NotificationToken"
        val sharedPreferencesToken: SharedPreferences = this.getSharedPreferences(sharedPrefToken,Context.MODE_PRIVATE)

        val isTokenAvail = sharedPreferences.getBoolean("IsToken",false)

        if (!isTokenAvail){
            FirebaseMessaging.getInstance().token
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w("LaunchActivity", "Fetching FCM registration token failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new FCM registration token
                    val token: String = task.result.toString()


                    val editor:SharedPreferences.Editor =  sharedPreferencesToken.edit()
                    editor.putBoolean("IsToken",true)
                    editor.putString("Token",token)
                    editor.apply()

                    // Log and toast
                    Log.d("LaunchActivity", token)
                })
        }

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