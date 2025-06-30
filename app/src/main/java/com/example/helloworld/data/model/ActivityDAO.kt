package com.example.helloworld.data.model
import androidx.lifecycle.LiveData
import androidx.room.Query;
import androidx.room.Dao;
import androidx.room.Delete
import androidx.room.Insert


@Dao
interface ActivityDao {
    @Query("SELECT * FROM Activity")
    fun getAll(): LiveData<List<Activity>>

    @Insert
    fun insert(vararg activity: Activity)

    @Delete
    fun delete(activity: Activity)
}