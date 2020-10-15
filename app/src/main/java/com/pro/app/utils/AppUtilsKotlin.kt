package com.pro.app.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.pro.app.R
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class AppUtilsKotlin {
    public companion object {

        private val VALID_EMAIL_ADDRESS_REGEX: Pattern =
            Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+", Pattern.CASE_INSENSITIVE)

        fun checkEmailForValidity(email: String?): Boolean {
            if (email == null) {
                return false
            }
            val matcher: Matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email)
            return matcher.find()
        }

        fun passwordValidation(password: String?): Boolean {
            return !password.isNullOrEmpty()
        }

        fun getDate(addedOn: String): String {

            val format = SimpleDateFormat(Constants.DATEFORMAT_1, Locale.getDefault())
            format.timeZone = TimeZone.getTimeZone("UTC")
            val date = format.parse(addedOn)
            var modifiedDate =
                SimpleDateFormat(Constants.DATE_FORMAT_2, Locale.getDefault()).format(date)
            return modifiedDate
        }

        fun isJSONValid(test: String): Boolean {
            try {
                JSONObject(test)
            } catch (ex: JSONException) {
                try {
                    JSONArray(test)
                } catch (ex1: JSONException) {
                    return false
                }

            }
            return true
        }

        fun formatString(text: String): String {

            val json = StringBuilder()
            var indentString = ""

            for (i in 0 until text.length) {
                val letter = text[i]
                when (letter) {
                    '{', '[' -> {
                        json.append("\n" + indentString + letter + "\n")
                        indentString += "\t"
                        json.append(indentString)
                    }
                    '}', ']' -> {
                        indentString = indentString.replaceFirst("\t".toRegex(), "")
                        json.append("\n" + indentString + letter)
                    }
                    ',' -> json.append(letter + "\n" + indentString)

                    else -> json.append(letter)
                }
            }

            return json.toString()
        }

        fun showMessage(context: Context, msg: String) {
            val alertDialog = AlertDialog.Builder(context).create()
            alertDialog.setTitle(context.getString(R.string.message))
            alertDialog.setMessage(msg)
            alertDialog.setButton(
                AlertDialog.BUTTON_POSITIVE,
                context.getString(R.string.ok)
            ) { dialog, which ->
                dialog.dismiss()

            }
            alertDialog.show()
        }

        fun getInitials(data: String): String {

            val dataArray = data.trim().split(" ", ignoreCase = true)
            val defaultString = ""
            return if (dataArray.isNotEmpty()) {
                if (dataArray.size > 1) {
                    try {
                        "${dataArray[0].trim().substring(0, 1)}${dataArray[1].trim()
                            .substring(0, 1)}".toUpperCase(
                            Locale.getDefault()
                        )
                    } catch (e: StringIndexOutOfBoundsException) {
                        defaultString
                    }
                } else {
                    return if (dataArray[0].length > 1) {
                        data.trim().substring(0, 2).toUpperCase(Locale.getDefault())
                    } else {
                        data.trim().substring(0, 1).toUpperCase(Locale.getDefault())
                    }
                }
            } else {
                defaultString
            }
        }

        fun changeLang(
            context: Context,
            lang_code: String
        ): Context? {
            var context = context
            val sysLocale: Locale
            val rs: Resources = context.resources
            val config: Configuration = rs.configuration
            sysLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                config.locales.get(0)
            } else {
                config.locale
            }
            if (lang_code != "") { //&& sysLocale.language != lang_code
                val locale = Locale(lang_code)
                Locale.setDefault(locale)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    config.setLocale(locale)
                } else {
                    config.locale = locale
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    context = context.createConfigurationContext(config)
                } else {
                    context.resources
                        .updateConfiguration(config, context.resources.displayMetrics)
                }
            }
            //return ContextWrapper(context)
            return context
        }

        fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}