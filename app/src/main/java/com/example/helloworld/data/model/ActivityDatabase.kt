package com.example.helloworld.data.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Activity::class], version = 1)
@TypeConverters(Converters::class)
abstract class ActivityDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao

    private class ActivityDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
        }
    }

    companion object {
        private var INSTANCE: ActivityDatabase? = null
        fun getInstance(context: Context, scope: CoroutineScope): ActivityDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                        context.applicationContext,
                                        ActivityDatabase::class.java,
                                        "Activitiy"

                                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
