package com.example.helloworld

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import android.widget.TextView

class WelcomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)

        // Находим кнопку регистрации и устанавливаем обработчик клика
        val registerButton = findViewById<MaterialButton>(R.id.registerButton)
        registerButton.setOnClickListener {
            // Переход на экран регистрации
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }

        // Находим TextView с текстом "Уже есть аккаунт? Войти"
        val loginPromptTextView = findViewById<TextView>(R.id.loginPromptTextView)

        // Создаем SpannableString для кликабельного текста
        val fullText = "Уже есть аккаунт? Войти"
        val spannableString = SpannableString(fullText)

        // Определяем позиции слова "Войти" в тексте
        val startIndex = fullText.indexOf("Войти")
        val endIndex = startIndex + "Войти".length

        // Создаем ClickableSpan для обработки клика
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Переход на экран входа
                val intent = Intent(this@WelcomeScreen, Login::class.java)
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                // Устанавливаем цвет текста
                ds.color = ContextCompat.getColor(this@WelcomeScreen, R.color.purple_500)
                // Убираем подчеркивание
                ds.isUnderlineText = false
            }
        }

        // Применяем ClickableSpan к слову "Войти"
        spannableString.setSpan(
            clickableSpan,
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Устанавливаем текст в TextView
        loginPromptTextView.text = spannableString
        // Включаем возможность клика по тексту
        loginPromptTextView.movementMethod = LinkMovementMethod.getInstance()
        // Убираем подсветку при клике
        loginPromptTextView.highlightColor = Color.TRANSPARENT
    }
}