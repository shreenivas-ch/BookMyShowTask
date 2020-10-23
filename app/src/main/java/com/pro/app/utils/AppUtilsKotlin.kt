package com.pro.app.utils

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class AppUtilsKotlin {
    companion object {

        fun getReleaseYear(release_date: String): String {
            val format = SimpleDateFormat(Constants.DATEFORMAT_1, Locale.getDefault())
            format.timeZone = TimeZone.getTimeZone("UTC")
            val date = format.parse(release_date)
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
    }
}