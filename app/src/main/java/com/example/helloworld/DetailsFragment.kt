package com.example.helloworld

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.helloworld.data.model.activityTypeToString
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsFragment(private var data: HistoryListItemData) : Fragment() {

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.activity_menu, menu)

    }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar = (requireActivity() as Activity_Emptystate).getSupportActionBar();
        actionBar?.setDisplayHomeAsUpEnabled(true);
        actionBar?.setDisplayShowHomeEnabled(true);
        actionBar?.title = data.type;
        actionBar?.show();
        actionBar?.setShowHideAnimationEnabled(false)

        setHasOptionsMenu(true);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_details, container, false)


        val distance = view.findViewById<TextView>(R.id.distance)
        val date = view.findViewById<TextView>(R.id.list_date)
        val duration = view.findViewById<TextView>(R.id.list_duration)
        val startTime = view.findViewById<TextView>(R.id.startTime)
        val endTime = view.findViewById<TextView>(R.id.endTime)

        distance.text = data.distance.toString();
        date.text = data.date.toString();
        duration.text = data.duration.toString();

        val time = LocalTime.parse(data.date, DateTimeFormatter.ofPattern("HH:mm:ss"));
        val startTimeDate = time.toString()
        val durationMinutes = data.duration.dropLast(6).toInt()

        startTime.text = startTimeDate
        endTime.text =  time.plusMinutes(durationMinutes.toLong()).toString()

        return view
    }

}