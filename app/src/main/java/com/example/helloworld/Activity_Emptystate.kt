package com.example.helloworld

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView


import androidx.fragment.app.Fragment


class Activity_Emptystate : AppCompatActivity(R.layout.activity_emptystate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.bottom, 0, 0)
            insets
        }

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        // Set the default fragment that should be shown when the app starts
        setCurrentFragment(ActivityFragment())

        // Set a listener to handle item selection on the bottom navigation bar
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            // Check which menu item was clicked
            when (menuItem.itemId) {
                // If the Algorithm tab is selected, show the AlgorithmFragment
                R.id.activ -> setCurrentFragment(ActivityFragment())
                // If the Course tab is selected, show the CourseFragment
                R.id.profile -> setCurrentFragment(ProfileFragment())

            }
            // Return true to indicate that we handled the item click
            true


        }


    }
    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            // Replace the fragment inside the container with the new fragment
            replace(R.id.fragment_container, fragment)
            // Commit the transaction to actually perform the change
            commit()
        }

}


