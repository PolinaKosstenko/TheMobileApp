package com.example.helloworld

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helloworld.HistoryListData
import kotlin.properties.Delegates

data class HistoryListData(
    val time: String,
    val childrenData: ArrayList<HistoryListItemData>
)


data class HistoryListItemData(
    val distance: String,
    val username: String,
    val duration: String,
    val type: String,
    val date: String,
    val onClick: ((Bundle) -> Unit)?
)

class HistoryListItemAdapter(
    private var dataSet: ArrayList<HistoryListItemData>,
) : RecyclerView.Adapter<HistoryListItemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val distance: TextView = view.findViewById(R.id.list_distance)
        val username: TextView = view.findViewById(R.id.list_username)
        val duration: TextView = view.findViewById(R.id.list_duration)
        val type: TextView = view.findViewById(R.id.list_type)
        val date: TextView = view.findViewById(R.id.list_date)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.activity_list_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.setOnClickListener {
            dataSet[position].onClick?.let { it1 ->
                it1(bundleOf(
                    "distance" to dataSet[position].distance,
                    "username" to dataSet[position].username,
                    "duration" to dataSet[position].duration,
                    "type" to dataSet[position].type,
                    "date" to dataSet[position].date
                ))
            }
        }
        viewHolder.distance.text = dataSet[position].distance
        viewHolder.username.text = dataSet[position].username
        viewHolder.duration.text = dataSet[position].duration
        viewHolder.type.text = dataSet[position].type
        viewHolder.date.text = dataSet[position].date

    }

    override fun getItemCount() = dataSet.size
}

class HistoryDataSpacingDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            bottom = spaceHeight
        }
    }
}

class HistoryListDataAdapter(
    private var dataSet: ArrayList<HistoryListData>
) : RecyclerView.Adapter<HistoryListDataAdapter.ViewHolder>() {
    private var spacingInPixels by Delegates.notNull<Int>()
    private var listItems = ArrayList<ViewHolder>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val time: TextView = view.findViewById(R.id.date)
        val children: RecyclerView = view.findViewById(R.id.children)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.activity_list, viewGroup, false)
        spacingInPixels = view.resources.getDimensionPixelSize(R.dimen.list_margin)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        listItems.add(viewHolder)

        viewHolder.time.text = dataSet[position].time

        val listItemAdapter = HistoryListItemAdapter(dataSet[position].childrenData)

        val recyclerView: RecyclerView = viewHolder.children
        recyclerView.layoutManager = LinearLayoutManager(null)

        recyclerView.addItemDecoration(HistoryDataSpacingDecoration(spacingInPixels))

        recyclerView.adapter = listItemAdapter
    }

    fun update(dataSet: ArrayList<HistoryListData>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataSet.size
}
