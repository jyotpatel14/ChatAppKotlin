package com.example.chatappkotlin

import android.content.Context
import android.location.GnssNavigationMessage
import android.widget.Toast

object CallToast {
    private var toast: Toast? = null

    fun show(context: Context, message: String){
        toast?.cancel() // Cancel any existing toast
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast?.show()
    }
}