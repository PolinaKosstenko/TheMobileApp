import androidx.lifecycle.LiveData
import com.example.helloworld.data.model.Activity
import com.example.helloworld.data.model.ActivityDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivityRepository(private val activityDao: ActivityDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val activityList: LiveData<List<Activity>>? = activityDao.getAll()

    fun addActivity(activity: Activity) {
        coroutineScope.launch(Dispatchers.IO) {
            activityDao.insert(activity)
        }
    }

    fun deleteActivity(activity: Activity) {
        coroutineScope.launch(Dispatchers.IO) {
            activityDao.delete(activity)
        }
    }
}