package org.yellowhatpro.thetaskapp.data.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.yellowhatpro.thetaskapp.data.dao.TaskDao
import org.yellowhatpro.thetaskapp.data.entities.Task
import org.yellowhatpro.thetaskapp.utils.Result
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {
     suspend fun getTasks(): Flow<Result<List<Task>>> = flow {
         emit(
             Result {
                 taskDao.getTasks()
             }
         )
     }

     suspend fun getTaskById(taskId: Int): Flow<Result<Task>> = flow {
         emit(Result {
             taskDao.getTaskById(taskId)
         })
     }

     suspend fun deleteTaskById(taskId: Int) {
        return taskDao.deleteTaskById(taskId)
    }

     suspend fun createTask(task: Task) {
        return taskDao.createTask(task)
    }

     suspend fun updateTask(task: Task) {
         taskDao.updateTask(task)
    }

     suspend fun deleteAllCompletedTasks() {
        return taskDao.deleteAllCompletedTasks()
    }
}