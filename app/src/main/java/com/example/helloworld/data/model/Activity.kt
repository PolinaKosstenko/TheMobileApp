package com.example.helloworld.data.model
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter
import com.google.gson.Gson
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale

enum class ActivityType {
    BIKE, RUN, WALK;
}

val activityTypeToString: Map<ActivityType, String> = mapOf(
    ActivityType.BIKE to "Велосипед",
    ActivityType.RUN to "Бег",
    ActivityType.WALK to "Шаг",
)

val stringToActivityType = mapOf(
    "Велосипед" to ActivityType.BIKE,
    "Бег" to ActivityType.RUN,
    "Шаг" to ActivityType.WALK,
)

class Converters {
    @TypeConverter
    fun fromActivityTypeToString(type: ActivityType): String {
        return activityTypeToString[type].toString();
    }

    @TypeConverter
    fun fromStringToActivityType(type: String): ActivityType {
        return stringToActivityType[type] as ActivityType;
    }

    @TypeConverter
    fun fromDateToString(date: LocalDateTime): String {
        return date.toString()
    }

    @TypeConverter
    fun fromStringToDate(date: String): LocalDateTime {
        return LocalDateTime.parse(date)
    }

    @TypeConverter
    fun fromArrayPairToString(coordinates: Array<Coordinate>): String {
        var gson = Gson()
        var jsonString = gson.toJson(coordinates)
        return jsonString
    }

    @TypeConverter
    fun fromStringToArrayPair(coordinates: String): Array<Coordinate> {
        var gson = Gson()
        var array = gson.fromJson(coordinates, Array<Coordinate>::class.java)
        return array
    }
}


@Serializable
data class Coordinate (
    val latitude: Float,
    val longitude: Float
)

@Entity
data class Activity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "type") val type: ActivityType,
    @ColumnInfo(name = "startTime") val startTime: LocalDateTime?,
    @ColumnInfo(name = "endTime") val endTime: LocalDateTime?,
    @ColumnInfo(name = "coordinates") val coorinates: Array<Coordinate>
)