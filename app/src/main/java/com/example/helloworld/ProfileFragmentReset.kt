package com.example.helloworld

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragmentReset.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragmentReset : Fragment() {

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = (requireActivity() as Activity_Emptystate).getSupportActionBar();

        actionBar?.setDisplayHomeAsUpEnabled(true);
        actionBar?.setDisplayShowHomeEnabled(true);
        actionBar?.title = "Сбросить пароль"
        actionBar?.show();
        actionBar?.setShowHideAnimationEnabled(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile_reset, container, false)

        view.findViewById<TextView>(R.id.resetButton).setOnClickListener {
            val intent = Intent(activity, Activity_Emptystate::class.java)
            startActivity(intent)
        }

        return view
    }
}