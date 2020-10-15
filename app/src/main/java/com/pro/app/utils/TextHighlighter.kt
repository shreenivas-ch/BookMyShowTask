package com.pro.app.utils

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView
import java.util.*

class TextHighlighter {
    private var fgColor: ForegroundColorSpan? = null
    private var bgColor: BackgroundColorSpan? = null
    private var bold = false
    private var italic = false
    private var textViews = ArrayList<TextView>()
    private var highlightedText: String? = null

    /**
     * Its method will be called to find position of highlighting text.
     */
    interface Matcher {
        /**
         * Implement algorithm to find matching indices.
         *
         *
         *  getMatchIndices("Hello world! We are the world". "world") should return [7,14]
         *
         * @param origin whole text
         * @param keyword word you want to highlight
         * @return matching indies
         */
        fun getMatchIndices(
            origin: String,
            keyword: String
        ): ArrayList<Int>

        /**
         * Implement algorithm to detecting any matching word exist.
         *
         * @param origin whole text
         * @param keyword word you want to highlight
         * @return true for if any text matches, otherwise false
         */
        fun isHighlightable(origin: String, keyword: String): Boolean
    }

    /**
     * Set foreground color of highlighted text.
     *
     * @param color color of foreground.
     * @return itself
     */
    fun setForegroundColor(color: Int): TextHighlighter {
        fgColor = ForegroundColorSpan(color)
        return this
    }

    /**
     * Reset foreground color of highlighted text.
     *
     * @return itself
     */
    fun resetForegroundColor(): TextHighlighter {
        fgColor = null
        return this
    }

    /**
     * Set background color of highlighted text.
     *
     * @param color color of background
     * @return itself
     */
    fun setBackgroundColor(color: Int): TextHighlighter {
        bgColor = BackgroundColorSpan(color)
        return this
    }

    /**
     * Reset background color of highlighted text.
     *
     * @return itself
     */
    fun resetBackgroundColor(): TextHighlighter {
        bgColor = null
        return this
    }

    /**
     * Set bold typeface to highlighted text.
     *
     * @param bold bold or not
     * @return itself
     */
    fun setBold(bold: Boolean): TextHighlighter {
        this.bold = bold
        return this
    }

    /**
     * Set italic typeface to highlighted text.
     *
     * @param italic bold or not
     * @return itself
     */
    fun setItalic(italic: Boolean): TextHighlighter {
        this.italic = italic
        return this
    }

    /**
     * Add single TextView as target of current TextHighlighter.
     *
     *
     *  It sets target view only if view is instance of TextView.
     *
     * @param view target View
     * @return itself
     */
    fun addTarget(view: View?): TextHighlighter {
        if (view is TextView && !textViews.contains(view)) {
            textViews.add(view)
        }
        return this
    }

    /**
     * Remove all targeted TextView.
     */
    fun resetTargets() {
        textViews = ArrayList()
    }

    /**
     * Perform highlight action for targeted views.
     *
     * @param keyword word you want to highlight
     * @param matcher [Matcher] for finding position of highlighted text
     */
    fun highlight(
        keyword: String?,
        matcher: Matcher
    ) {
        highlightedText = keyword
        if (keyword == null || keyword.isEmpty()) {
            reset()
            return
        }
        for (textView in textViews) {
            highlightTextView(textView, keyword, matcher)
        }
    }

    /**
     * Re-Highlight words in given targets.
     *
     * @param matcher [Matcher] for finding position of highlighted text
     */
    fun invalidate(matcher: Matcher) {
        highlight(highlightedText, matcher)
    }

    private fun getHighlightedText(
        origin: String, keyword: String,
        indices: ArrayList<Int>
    ): Spannable {
        return getHighlightedText(SpannableString(origin), keyword, indices)
    }

    private fun getHighlightedText(
        origin: Spannable, keyword: String,
        indices: ArrayList<Int>
    ): Spannable {
        val spannable: Spannable = SpannableString(origin.toString())
        val noop = (origin.toString().isEmpty()
                || fgColor == null && bgColor == null && !bold && !italic)
        if (noop) {
            return spannable
        }
        for (index in 0 until origin.toString().length) {
            for (style in origin.getSpans(
                index,
                index + 1,
                CharacterStyle::class.java
            )) {
                if (style !is StyleSpan) {
                    spannable.setSpan(CharacterStyle.wrap(style), index, index + 1, 0)
                }
            }
        }
        for (index in indices) {
            if (fgColor != null) {
                spannable.setSpan(CharacterStyle.wrap(fgColor), index, index + keyword.length, 0)
            }
            if (bgColor != null) {
                spannable.setSpan(CharacterStyle.wrap(bgColor), index, index + keyword.length, 0)
            }
            if (!bold && !italic) {
                spannable.setSpan(StyleSpan(Typeface.NORMAL), index, index + keyword.length, 0)
            } else if (bold && !italic) {
                spannable.setSpan(StyleSpan(Typeface.BOLD), index, index + keyword.length, 0)
            } else if (!bold && italic) {
                spannable.setSpan(StyleSpan(Typeface.ITALIC), index, index + keyword.length, 0)
            } else {
                spannable.setSpan(StyleSpan(Typeface.BOLD_ITALIC), index, index + keyword.length, 0)
            }
        }
        return spannable
    }

    private fun highlightTextView(
        textView: TextView?,
        keyword: String,
        matcher: Matcher
    ) {
        if (textView == null) {
            return
        }
        val indices =
            matcher.getMatchIndices(textView.text.toString(), keyword)
        textView.setText(
            getHighlightedText(textView.text.toString(), keyword, indices),
            TextView.BufferType.SPANNABLE
        )
    }

    fun highlightString(
        completeString: String,
        keyword: String,
        matcher: Matcher
    ): Spannable {
        val indices = matcher.getMatchIndices(completeString, keyword)
        return getHighlightedText(completeString, keyword, indices)
    }

    private fun resetTextView(textView: TextView?) {
        if (textView != null) {
            textView.text = textView.text.toString()
        }
    }

    private fun reset() {
        for (textView in textViews) {
            resetTextView(textView)
        }
    }

    companion object {
        /**
         * Simple Matcher which finds exactly same word.
         */
        val BASE_MATCHER: Matcher =
            object : Matcher {
                override fun getMatchIndices(
                    origin: String,
                    keyword: String
                ): ArrayList<Int> {
                    val indices = ArrayList<Int>()
                    var index = origin.toLowerCase().indexOf(keyword.toLowerCase(), 0)
                    while (index != -1) {
                        indices.add(index)
                        index = origin.indexOf(keyword.toLowerCase(), index + 1)
                    }
                    return indices
                }

                override fun isHighlightable(
                    origin: String,
                    keyword: String
                ): Boolean {
                    return getMatchIndices(origin, keyword).size > 0
                }
            }

        /**
         * Case insensitive Matcher which finds exactly same word but ignore case.
         */
        val CASE_INSENSITIVE_MATCHER: Matcher =
            object : Matcher {
                override fun getMatchIndices(
                    origin: String,
                    keyword: String
                ): ArrayList<Int> {
                    var origin = origin
                    var keyword = keyword
                    val indices = ArrayList<Int>()
                    origin = origin.toLowerCase()
                    keyword = keyword.toLowerCase()
                    var index = origin.indexOf(keyword, 0)
                    while (index != -1) {
                        indices.add(index)
                        index = origin.indexOf(keyword, index + 1)
                    }
                    return indices
                }

                override fun isHighlightable(
                    origin: String,
                    keyword: String
                ): Boolean {
                    return getMatchIndices(origin, keyword).size > 0
                }
            }
    }
}