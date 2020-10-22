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
    companion object {

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
    }
}