package com.example.helloworld.data.model

import ActivityRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class ActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ActivityRepository

    val allActivities: LiveData<List<Activity>>

    init {
        val activityDao = ActivityDatabase.getInstance(application, viewModelScope).activityDao()
        repository = ActivityRepository(activityDao)
        allActivities = repository.activityList!!
    }

    fun insert(activity: Activity) = viewModelScope.launch {
        repository.addActivity(activity)
    }
}