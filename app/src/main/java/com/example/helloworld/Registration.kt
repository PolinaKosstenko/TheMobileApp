package com.example.helloworld

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class Registration : AppCompatActivity(R.layout.activity_registration) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val editText = findViewById<TextInputLayout>(R.id.loginInputLayout)
        val button = findViewById<Button>(R.id.bthButton)

        button.setOnClickListener {
            val intent = Intent(this, WelcomeScreen::class.java)
            startActivity(intent)
        }

        setupPrivacyPolicyText()
    }

    private fun setupPrivacyPolicyText() {
        val privacyTextView = findViewById<TextView>(R.id.termsTextView)
        val fullText = "Нажимая на кнопку, вы соглашаетесь с политикой конфиденциальности и обработки персональных данных, а также принимаете пользовательское соглашение"

        val spannableString = SpannableString(fullText)


        val privacyText = "политикой конфиденциальности"
        val privacyStart = fullText.indexOf(privacyText)
        val privacyEnd = privacyStart + privacyText.length

        spannableString.setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {
                    Toast.makeText(
                        this@Registration,
                        "Политика конфиденциальности (заглушка)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                    ds.color = Color.BLUE
                }
            },
            privacyStart,
            privacyEnd,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val termsText = "пользовательское соглашение"
        val termsStart = fullText.indexOf(termsText)
        val termsEnd = termsStart + termsText.length

        spannableString.setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {
                    Toast.makeText(
                        this@Registration,
                        "Пользовательское соглашение (заглушка)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                    ds.color = Color.BLUE
                }
            },
            termsStart,
            termsEnd,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        privacyTextView.text = spannableString
        privacyTextView.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}