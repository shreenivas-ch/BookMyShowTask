package com.pro.app.extensions

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.pro.app.R
import com.pro.app.utils.Constants.TAG
import java.text.SimpleDateFormat
import java.util.*

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatImageView.loadUrl(url: String) {
    Glide.with(this)
        .load(url)
        .error(R.drawable.placeholder)
        .into(this)
}

fun String.showLog() {
    Log.d(TAG, this)
}

fun String.dateInFormat(from: String, to: String) {
    val currentDateFormat = SimpleDateFormat(from, Locale.getDefault())
    val dateToFormat = currentDateFormat.parse(this)
    val newDateFormat = SimpleDateFormat(to, Locale.getDefault())
    newDateFormat.format(dateToFormat)
}

fun AppCompatActivity.showMessage(msg: String) {
    val alertDialog = AlertDialog.Builder(this).create()
    alertDialog.setTitle(getString(R.string.message))
    alertDialog.setMessage(msg)
    alertDialog.setButton(
        AlertDialog.BUTTON_POSITIVE,
        getString(R.string.ok)
    ) { dialog, which ->
        dialog.dismiss()

    }
    alertDialog.show()
}
