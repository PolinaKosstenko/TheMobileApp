package com.example.helloworld

import android.app.Application
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helloworld.data.model.ActivityViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import kotlin.collections.get
import kotlin.compareTo
import com.example.helloworld.data.model.Coordinate
import java.time.LocalDate
import kotlin.math.*
import com.example.helloworld.HistoryFragment
import com.example.helloworld.data.model.ActivityType
import com.example.helloworld.data.model.activityTypeToString
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Collections

class HistoryItemSpacingDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            bottom = spaceHeight
//            if (parent.getChildAdapterPosition(view) == 0) {
//                top = spaceHeight
//            }
        }
    }
}

class HistoryFragment(val type: String) : Fragment() {
    private lateinit var viewModel: ActivityViewModel
    private lateinit var dataSet: MutableMap<String, ArrayList<HistoryListData>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        val onClick = { data: Bundle ->
        requireActivity().supportFragmentManager.setFragmentResult(
            "showDetails",
            data
        )}

        dataSet = mutableMapOf(
            "Пользователей" to arrayListOf(
                HistoryListData(
                    "Позавчера",
                    arrayListOf(
                        HistoryListItemData(
                            "14.32 км",
                            "@van_darkholme",
                            "02-46",
                            "Серфинг",
                            "14",
                            onClick
                        ),
                    )
                ),
                HistoryListData(
                    "Вчера",
                    arrayListOf(
                        HistoryListItemData(
                            "14.32 км",
                            "@van_darkholme",
                            "02-46",
                            "Серфинг",
                            "14",
                            onClick
                        ),
                        HistoryListItemData(
                            "228 м",
                            "@techniquepasha",
                            "14-48",
                            "Качели",
                            "14",
                            onClick
                        ),
                    )
                ),
                HistoryListData(
                    "Сегодня",
                    arrayListOf(
                        HistoryListItemData(
                            "228 м",
                            "@techniquepasha",
                            "14-48",
                            "Качели",
                            "14",
                            onClick
                        ),
                        HistoryListItemData(
                            "10 км",
                            "@morgen_shtern",
                            "01-10",
                            "Езда на кадилак",
                            "14",
                            onClick
                        )
                    )
                ),
            ),
            "Мои" to arrayListOf()
        )

        val customAdapter = HistoryListDataAdapter(dataSet[type] ?: arrayListOf())
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        // Установка отступа между элементами (16dp)
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.fab_margin)
        recyclerView.addItemDecoration(HistoryItemSpacingDecoration(spacingInPixels))

        recyclerView.adapter = customAdapter

        if (type == "Мои") {
            viewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(activity?.application ?: Application())
            )[ActivityViewModel::class.java]

            viewModel.allActivities.observe(viewLifecycleOwner) { list ->
                val distBetween = {
                        point1: Coordinate, point2: Coordinate ->
                    acos(
                        sin(point1.latitude) * sin(point2.latitude) +
                                cos(point1.latitude) * cos(point2.latitude) * cos(
                            point2.longitude - point1.longitude
                        )) * 6371
                }

                if (list.size > 0) {
                    var myDataSet: ArrayList<HistoryListData> = arrayListOf()
                    val time = LocalDateTime.now()

                    list?.let {
                        for (v in list) {
                            var dist = 0f
                            for (i in 0 until v.coorinates.size - 1)
                                dist += distBetween(v.coorinates[i], v.coorinates[i + 1])


                            val item = HistoryListItemData(
                                distance = "${dist} км",
                                username = "",
                                duration = "${v.startTime?.until(v.endTime, ChronoUnit.MINUTES)} минут",
                                type = activityTypeToString[v.type].toString(),
                                date = DateTimeFormatter.ofPattern("HH:mm:ss").format(v.startTime),
                                onClick
                            )

                            var t = v.endTime?.toLocalDate().toString()
                            val until = v.endTime?.until(time, ChronoUnit.DAYS) ?: 3
                            if (until.toInt() == 0)
                                t = "Сегодня"
                            if (until.toInt() == 1)
                                t = "Вчера"

                            val i = myDataSet.find({ i -> i.time == t })
                            if (i != null) i.childrenData.add(item)
                            else myDataSet.add(HistoryListData(
                                t,
                                arrayListOf(item)
                            ))
                        }

                        for (l in myDataSet) {
                            l.childrenData.sortWith(Comparator {
                                i1: HistoryListItemData, i2: HistoryListItemData ->
                                    i1.date.compareTo(i2.date)
                            })
                        }

                        customAdapter.update(myDataSet)
                    }
                }
            }
        }

        val addActivityButton: FloatingActionButton = view.findViewById(R.id.floatingActionButton)
        addActivityButton.setOnClickListener {
            val intent = Intent(activity, AddActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}