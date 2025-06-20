package com.example.helloworld

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date

data class HistoryListData(
    val distance: String,
    val username: String,
    val duration: Date,
    val type: String,
    val date: Date
)


class HistoryListAdapter(private val dataSet: Array<HistoryListData>) :
    RecyclerView.Adapter<HistoryListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Define click listener for the ViewHolder's View
        val distance: TextView = view.findViewById(R.id.distance)
        val username: TextView = view.findViewById(R.id.username)
        val duration: TextView = view.findViewById(R.id.duration)
        val type: TextView = view.findViewById(R.id.type)
        val date: TextView = view.findViewById(R.id.date)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.activity_list_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.distance.text = dataSet[position].distance
        viewHolder.username.text = dataSet[position].username
        viewHolder.duration.text = dataSet[position].duration.toString()
        viewHolder.type.text = dataSet[position].type
        viewHolder.date.text = dataSet[position].date.toString()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}

class HistoryFragment(val type: String) : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        val dataset = mapOf(
            "Мои" to arrayOf(
                HistoryListData(
                    "14.32 км",
                    "",
                    SimpleDateFormat("hh-mm").parse("02-46"),
                    "Серфинг",
                    SimpleDateFormat("hh").parse("14")),
                HistoryListData(
                    "1000 м",
                    "",
                    SimpleDateFormat("mm").parse("60"),
                    "Велосипед",
                    SimpleDateFormat("dd.mm.yyyy").parse("29.05.2022")),
            ),
            "Пользователей" to arrayOf(
                HistoryListData(
                    "14.32 км",
                    "@van_darkholme",
                    SimpleDateFormat("hh-mm").parse("02-46"),
                    "Серфинг",
                    SimpleDateFormat("hh").parse("14")),
                HistoryListData(
                    "228 м",
                    "@techniquepasha",
                    SimpleDateFormat("hh-mm").parse("14-48"),
                    "Качели",
                    SimpleDateFormat("hh").parse("14")),
                HistoryListData(
                    "10 км",
                    "@morgen_shtern",
                    SimpleDateFormat("hh-mm").parse("01-10"),
                    "Езда на кадилак",
                    SimpleDateFormat("hh").parse("14"))
            )
        )

        val customAdapter = HistoryListAdapter(dataset[type] ?: arrayOf())

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = customAdapter

        return view
    }
}