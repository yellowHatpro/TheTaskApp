package org.yellowhatpro.thetaskapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import org.yellowhatpro.thetaskapp.data.entities.Task

@Dao
interface TaskDao {
    @Query(value = "SELECT * FROM TASK")
    suspend fun getTasks(): List<Task>

    @Query(value = "SELECT * FROM TASK WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): Task

    @Query(value = "DELETE FROM TASK WHERE id = :taskId")
    suspend fun deleteTaskById(taskId: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Query(value = "DELETE FROM TASK WHERE isCompleted = true")
    suspend fun deleteAllCompletedTasks()
}