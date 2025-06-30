package com.example.helloworld

import ActivityRepository
import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView


import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.helloworld.data.model.Activity
import com.example.helloworld.data.model.ActivityDao
import com.example.helloworld.data.model.ActivityDatabase
import com.example.helloworld.data.model.ActivityType
import com.example.helloworld.data.model.activityTypeToString
import com.example.helloworld.data.model.stringToActivityType
import com.google.android.gms.maps.GoogleMap
import java.time.LocalDate


class Activity_Emptystate : AppCompatActivity(R.layout.activity_emptystate) {
    // create an action bar button
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res

        return super.onCreateOptionsMenu(menu);
    }

// handle button activities
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id: Number = item.getItemId();

        if (id == R.id.delete) {
            Log.d("CLICK", "DELETE");
        }

        if (id == R.id.share) {
            Log.d("CLICK", "SHARE");

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayShowHomeEnabled(false);
        supportActionBar?.setDisplayHomeAsUpEnabled(false);
        supportActionBar?.hide();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.bottom, 0, 0)
            insets
        }

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        // Set the default fragment that should be shown when the app starts
        setCurrentFragment(ActivityFragment())

        supportFragmentManager
            .setFragmentResultListener("showDetails", this) { requestKey, bundle ->
                // We use a String here, but any type that can be put in a Bundle is supported.
//                Log.d("Number", (HistoryListItemData)(bundle.get("data")).distance)
                val data: HistoryListItemData = HistoryListItemData(
                    bundle.getString("distance").toString(),
                    bundle.getString("username").toString(),
                    bundle.getString("duration").toString(),
                    bundle.getString("type").toString(),
                    bundle.getString("date").toString(),
                    null
                );

                setCurrentFragment(DetailsFragment(data))
                // Do something with the result.
            }

        supportFragmentManager
            .setFragmentResultListener("resetPassword", this) { requestKey, bundle ->
                setCurrentFragment(ProfileFragmentReset())
            }

        // Set a listener to handle item selection on the bottom navigation bar
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
//            supportActionBar?.setDisplayShowHomeEnabled(false);
//            supportActionBar?.setDisplayHomeAsUpEnabled(false);
//            supportActionBar?.setShowHideAnimationEnabled(false)
//
//            supportActionBar?.hide();
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
    override fun onSupportNavigateUp(): Boolean { //функция вызывается при нажатии на кнопку Назад в шапке
        supportFragmentManager.beginTransaction().apply {
            // Replace the fragment inside the container with the new fragment
            replace(R.id.fragment_container, ActivityFragment())

            supportActionBar?.setDisplayShowHomeEnabled(false);
            supportActionBar?.setDisplayHomeAsUpEnabled(false);
            supportActionBar?.hide();
            // Commit the transaction to actually perform the change
            commit()
        }
        return true
    }
}


