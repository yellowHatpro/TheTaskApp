package org.yellowhatpro.thetaskapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.yellowhatpro.thetaskapp.data.dao.TaskDao
import org.yellowhatpro.thetaskapp.data.entities.Task
import org.yellowhatpro.thetaskapp.utils.TypeConverter

@Database(
    entities = [
        Task::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao() : TaskDao
}