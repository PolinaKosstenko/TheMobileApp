package com.example.helloworld

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton


class WelcomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)

        // Находим кнопку регистрации по айдишке и устанавливаем обработчик клика
        val registerButton = findViewById<MaterialButton>(R.id.registerButton)
        registerButton.setOnClickListener {
            //  на экран регистрации
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }


        val loginPromptButton = findViewById<MaterialButton>(R.id.loginPromptButton)
        loginPromptButton.setOnClickListener {
            // переход на экран входа
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }


    }
}