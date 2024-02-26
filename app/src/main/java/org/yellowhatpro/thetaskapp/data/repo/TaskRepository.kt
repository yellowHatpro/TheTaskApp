package org.yellowhatpro.thetaskapp.data.repo

import org.yellowhatpro.thetaskapp.data.dao.TaskDao
import org.yellowhatpro.thetaskapp.data.entities.Task
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {
     suspend fun getTasks(): List<Task> {
        return taskDao.getTasks()
    }

     suspend fun getTaskById(taskId: Int): Task {
        return taskDao.getTaskById(taskId)
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