package com.bangkitacademy.medicare.customview

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.bangkitacademy.medicare.R

class CustomEditText : AppCompatEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Nothing
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    if (inputType == (InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS + 1)) {
                        checkValidEmail(s.toString())
                    } else if (inputType == (InputType.TYPE_TEXT_VARIATION_PASSWORD + 1)) {
                        checkValidPassword(s.toString())
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Nothing
            }
        })
    }

    private fun checkValidEmail(text: String) {
        error = if (!android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            context.getString(R.string.invalid_email)
        } else {
            null
        }
    }

    private fun checkValidPassword(text: String) {
        error = if (text.length < 8) {
            context.getString(R.string.invalid_password)
        } else {
            null
        }
    }
}