package org.yellowhatpro.thetaskapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.yellowhatpro.thetaskapp.data.entities.Task
import org.yellowhatpro.thetaskapp.data.repo.TaskRepository
import org.yellowhatpro.thetaskapp.utils.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    private val _tasks = MutableStateFlow<Response<List<Task>>>(Response.loading())
    val tasks: StateFlow<Response<List<Task>>> = _tasks

    private val _currentTask = MutableStateFlow<Response<Task>>(Response.loading())
    val currentTask: StateFlow<Response<Task>> = _currentTask

    fun fetchAllTasks(){
       viewModelScope.launch {
           try {
               val response = taskRepository.getTasks()
               _tasks.value = Response.success(response)
           } catch (e: Exception){
               _tasks.value = Response.failure()
           }
       }
    }

    fun fetchCurrentTask(taskId: Int) {
        viewModelScope.launch {
            _currentTask.value = Response.loading()
            try {
                val response = taskRepository.getTaskById(taskId)
                response.let {
                    _currentTask.value = Response.success(response)
                }
            } catch (e: Exception) {
                _currentTask.value = Response.failure()
            }
        }
    }

    fun deleteTask(taskId: Int){
        viewModelScope.launch {
            taskRepository.deleteTaskById(taskId)
        }
    }

    fun deleteCompletedTask(){
        viewModelScope.launch {
            taskRepository.deleteAllCompletedTasks()
        }
    }

     fun createNewTask(task: Task) {
         viewModelScope.launch {
             taskRepository.createTask(task)
         }
     }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            Log.d("IDDDD",task.id.toString())
            taskRepository.updateTask(task)
        }
    }

}