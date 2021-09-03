package com.church.punithaanthoniyar.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    /**
     * Method to perform all findByView
     * This will not be required if we are using viewBinding or synthetic binding
     * @param view
     */
    abstract fun init(view: View?)

    /**
     * Method to initilise dependance Injection.
     */
    abstract fun initializeDi()

    /**
     * Abstract method which can be used to get the data
     * via intent for other activities
     */
    protected abstract fun getMessageFromAliens()

    /**
     * Set up the views with listeners,other initializations etc
     */
    protected abstract fun setUpViews()
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
        init(view)
        initializeDi()
        getMessageFromAliens()
        setUpViews()
        super.onViewCreated(view, savedInstanceState)
    }

    val isNetworkConnected: Boolean
        get() = (activity as BaseActivity?)!!.isNetworkConnected

    fun startActivity(activity: Class<*>?) {
        (getActivity() as BaseActivity?)!!.startActivity(activity)
    }

    fun startActivityAndFinish(activity: Class<*>?) {
        (getActivity() as BaseActivity?)!!.startActivityAndFinish(activity)
    }

    fun setScreenTitle(title : String){
        (activity as BaseActivity?)!!.setScreenTitle(title)
    }
}