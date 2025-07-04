package com.example.helloworld

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Активация кнопки "Назад" в ActionBar - шапка
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_login)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(0, systemBars.bottom, 0, 0)
//            insets
//        }
        val button = findViewById<Button>(R.id.resetButton)
        button.setOnClickListener {
            val intent = Intent(this, Activity_Emptystate::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean { //функция вызывается при нажатии на кнопку Назад в шапке
        finish() // Закрываем Activity
        return true //зачем то возвращаем?????
    }
}