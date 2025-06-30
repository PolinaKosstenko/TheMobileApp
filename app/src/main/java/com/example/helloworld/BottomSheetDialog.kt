package com.example.helloworld

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.graphics.toColorInt
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helloworld.data.model.Activity
import com.example.helloworld.data.model.ActivityType
import com.example.helloworld.data.model.Coordinate
import com.example.helloworld.data.model.activityTypeToString
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.properties.Delegates
import kotlin.random.Random

data class AddActivityListItem(
    val type: ActivityType,
    val imageId: Int
)

class AddActivityListItemAdapter(
    private val dataSet: Array<AddActivityListItem>,
) : RecyclerView.Adapter<AddActivityListItemAdapter.ViewHolder>() {
    private var spacingInPixels by Delegates.notNull<Int>()
    var selected: Int = 0;

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: CardView = view.findViewById(R.id.card)
        val type: TextView = view.findViewById(R.id.list_type)
        val image: ImageView = view.findViewById(R.id.list_image)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.activity_add_list_item, viewGroup, false)
        spacingInPixels = view.resources.getDimensionPixelSize(R.dimen.list_margin)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.type.text = activityTypeToString[dataSet[position].type]
        viewHolder.image.setImageResource(dataSet[position].imageId)
        viewHolder.card.setOnClickListener { it ->
            selected = position
            this.notifyDataSetChanged()
        }
        viewHolder.card.elevation = if (selected == position) 1f else 5f
        viewHolder.card.setCardBackgroundColor(
            if (selected == position) "#F6F5F5FF".toColorInt()
            else "#FFFFFFFF".toColorInt()
        )
    }

    override fun getItemCount() = dataSet.size
}


class BottomSheetDialog : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        dialog?.window?.addNavigationHeightAsBottomPadding()
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog);
        dialog?.setCanceledOnTouchOutside(true)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            transparentBackgroundForBottomSheet()
            setStyle(STYLE_NORMAL, R.style.BottomSheetDialog);
//            behavior.setPeekHeight(resources.displayMetrics.heightPixels / 2)

        }
    }

    fun Dialog.transparentBackgroundForBottomSheet() {
        setOnShowListener {
            val bottomSheet =
                findViewById<View>(R.layout.bottom_sheet_layout)
            bottomSheet?.setBackgroundResource(android.R.color.white)
        }
        window?.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    fun Window.addNavigationHeightAsBottomPadding(view: View = decorView) {
        view.post {
            val bottomInset =
                ViewCompat.getRootWindowInsets(view)?.getInsets(WindowInsetsCompat.Type.navigationBars())?.bottom ?: 0
            view.updatePadding(bottom = bottomInset)
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(
            R.layout.bottom_sheet_layout,
            container, false
        )

        view.post {
            val parent = view.parent as View
            val behavior = BottomSheetBehavior.from(parent)
//            behavior.setHideableInternal(false)
        }
//            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
//                val dismissOffset: Float = -0.2f // when less value then wider should be swipe gesture to dismiss
//
//                private var currOffset: Float = 1f // from 1 to -1
//
//                private var dismissAllowed: Boolean = true
//
//                override fun onStateChanged(
//                    bottomSheet: View, @BottomSheetBehavior.State newState: Int
//                ) {
//
//                    if (newState == BottomSheetBehavior.STATE_SETTLING) {
//                        if (currOffset > dismissOffset) {
//                            dismissAllowed = false
//                            behavior.state = BottomSheetBehavior.STATE_EXPANDED
//                        } else {
//                            behavior.state = BottomSheetBehavior.STATE_HIDDEN
//                        }
//                    } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
//                        dismissAllowed = true
//                    } else if (newState == BottomSheetBehavior.STATE_HIDDEN) {
//                        if (dismissAllowed) {
//                            dialog?.cancel()
//                        }
//                    }
//                }
//
//                override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                    currOffset = slideOffset
//                }
//            })
//            val layoutParams = parent.layoutParams
////            layoutParams.height = 100
//            parent.layoutParams = layoutParams
//            behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
//        }

        val dataSet = arrayOf(
            AddActivityListItem(
                ActivityType.BIKE,
                R.drawable.welcome_screen_image
            ),
            AddActivityListItem(
                ActivityType.RUN,
                R.drawable.welcome_screen_image
            ),
            AddActivityListItem(
                ActivityType.WALK,
                R.drawable.welcome_screen_image
            ),
        )

        val customAdapter = AddActivityListItemAdapter(dataSet)

        val recyclerView: RecyclerView = view.findViewById(R.id.activityAddRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)

        // Установка отступа между элементами (16dp)
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.fab_margin)
        recyclerView.addItemDecoration(HistoryItemSpacingDecoration(spacingInPixels))

        recyclerView.adapter = customAdapter

        val start_button = view.findViewById<Button>(R.id.start)

        start_button.setOnClickListener {
            val pointsCount = Random.nextInt(2, 10);
            val coordinates = Array<Coordinate>(pointsCount) { Coordinate(0f, 0f) }
            for (i in 0..pointsCount - 1) {
                coordinates[i] = Coordinate(
                    -90 + Random.nextFloat() * 180,
                    -180 + Random.nextFloat() * 360
                )
            }

            val subMinutes = Random.nextLong(0, 7200)
            val start = LocalDateTime.now().minus(subMinutes, ChronoUnit.MINUTES)
            val end = LocalDateTime.now().minus(Random.nextLong(0, subMinutes), ChronoUnit.MINUTES)

            (activity as AddActivity).viewModel.insert(
                Activity(
                    type = dataSet[customAdapter.selected].type,
                    startTime = start,
                    endTime = end,
                    coorinates = coordinates
                ))

            dismiss()
            val i = Intent(activity, Activity_Emptystate::class.java)
            startActivity(i)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setCanceledOnTouchOutside(false)
    }
}