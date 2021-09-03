package com.church.punithaanthoniyar.language

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.church.punithaanthoniyar.R

class LanguageDialogFragment(val iLanguageListener : ILanguageListener) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            val alertBuilder = AlertDialog.Builder(it)

            alertBuilder.setTitle("Select an Language")
            alertBuilder.setItems(R.array.lang_array, DialogInterface.OnClickListener{ dialog, index ->
                iLanguageListener.setLanguage(if (index == 0)"en" else "ta")
            })
            alertBuilder.create()
        } ?: throw IllegalStateException("Exception !! Activity is null !!")
    }

    interface ILanguageListener{
        fun setLanguage(lang : String)
    }
}