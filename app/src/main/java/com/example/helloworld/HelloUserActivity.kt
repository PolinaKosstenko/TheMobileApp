package com.example.helloworld

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class HelloUserActivity : AppCompatActivity(R.layout.activity_hello_user) {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var editText = findViewById<EditText>(R.id.etUserName)
        val button = findViewById<Button>(R.id.bthButton)

        button.setOnClickListener {
            val intent = Intent(this, HelloWorld::class.java)
            startActivity(intent)
        }
    }
}