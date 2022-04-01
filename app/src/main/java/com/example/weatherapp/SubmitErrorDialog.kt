package com.example.weatherapp

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class SubmitErrorDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
       AlertDialog.Builder(requireContext())
           .setMessage("Error Fetching data for the zip code")
           .setPositiveButton(R.string.ok, null)
           .create()


    companion object{
        const val TAG = "SubmitErrorDialogFragment"
    }
}