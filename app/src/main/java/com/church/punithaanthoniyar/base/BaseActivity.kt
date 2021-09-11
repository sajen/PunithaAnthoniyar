package com.church.punithaanthoniyar.base

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.church.punithaanthoniyar.R
import com.church.punithaanthoniyar.utils.NetworkUtils
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity : AppCompatActivity(), LifecycleOwner {
    /**
     * Initilise DI
     */
    abstract fun initializeDi()

    /**
     * set layout to setContentView depends on ViewBinding or Syntethic binding or normal way.
     * Incase of normal way do findViewById in this method.
     */
    protected abstract fun setUpLayout()

    /**
     * Set up the listeners and other initilisation.
     */
    protected abstract fun setUpViews()
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        initializeDi()
        initScreen()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun initScreen() {

        setUpLayout()
        setUpViews()
    }

    private fun showSnackBar(message: String) {
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            message, Snackbar.LENGTH_SHORT
        )
        val sbView = snackbar.view
        val textView = sbView
            .findViewById<View>(R.id.snackbar_text) as TextView
        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        snackbar.show()
    }

    /**
     * To validateData if network is connected
     *
     * @return true if Connected. False if not connected
     */
    val isNetworkConnected: Boolean
        get() = NetworkUtils.isNetworkConnected(this)

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onDestroy() {

        /*if (mUnBinder != null) {
            mUnBinder.unbind();
        }*/
        super.onDestroy()
    }

    fun addFragment(
        containerViewId: Int, fragment: Fragment?, addStack: Boolean,
        isReplace: Boolean, animationType: Int
    ) {
        if (fragment == null) {
            return
        }
        val fragmentManager = supportFragmentManager
        val fragmentTransaction =
            fragmentManager.beginTransaction()
        val tag = fragment.javaClass.toString()
        if (isReplace) {
            for (i in 0 until fragmentManager.backStackEntryCount) {
                fragmentManager.popBackStack()
            }
            fragmentManager.executePendingTransactions()
        }
        if (fragment.isAdded) {
            fragmentTransaction.show(fragment)
        } else {
            if (!isReplace) {
                var currentFragment: Fragment?
                fragmentTransaction.add(containerViewId, fragment, tag)
                if (supportFragmentManager.findFragmentById(containerViewId)
                        .also { currentFragment = it } != null
                ) {
                    fragmentTransaction.hide(currentFragment!!)
                }
            } else {
                fragmentTransaction.replace(containerViewId, fragment, tag)
            }
        }
        if (addStack) {
            fragmentTransaction.addToBackStack(tag)
        } else {
            fragmentManager.popBackStack(
                tag,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
        fragmentTransaction.commit()
        fragmentManager.executePendingTransactions()
    }

    fun setOrientation(isLandscape: Boolean) {
        requestedOrientation =
            if (isLandscape) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    fun startActivity(activity: Class<*>?) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

    fun startActivityAndFinish(activity: Class<*>?) {
        startActivity(activity)
        finish()
    }

    fun setScreenTitle(title: String) {
        val mScreenTitleTV = findViewById<TextView>(R.id.tv_toolbar_title)
        mScreenTitleTV.text = title
    }
}