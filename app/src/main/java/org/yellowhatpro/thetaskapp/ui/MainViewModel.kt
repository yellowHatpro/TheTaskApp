package org.yellowhatpro.thetaskapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.yellowhatpro.thetaskapp.data.entities.Task
import org.yellowhatpro.thetaskapp.data.repo.TaskRepository
import org.yellowhatpro.thetaskapp.utils.Result
import org.yellowhatpro.thetaskapp.utils.collectResult
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _tasks = MutableStateFlow<Result<List<Task>>>(Result.loading())
    val tasks: StateFlow<Result<List<Task>>> = _tasks

    private val _currentTask = MutableStateFlow<Result<Task>>(Result.loading())
    val currentTask: StateFlow<Result<Task>> = _currentTask

    fun fetchAllTasks() {
        viewModelScope.launch {
            taskRepository.getTasks().collectResult(
                onSuccess = {
                    _tasks.value = Result.success(it)
                },
                onFailure = {
                    _tasks.value = Result.failed(it.message.toString())
                },
                onLoading = {
                    _tasks.value = Result.loading()
                }
            )
        }
    }

    fun fetchTask(taskId: Int) {
        viewModelScope.launch {
            taskRepository.getTaskById(taskId).collectResult(
                onSuccess = {
                    _currentTask.value = Result.success(it)
                },
                onFailure = {
                    _currentTask.value = Result.failed(it.message.toString())
                },
                onLoading = {
                    _tasks.value = Result.loading()
                }
            )
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
           taskRepository.updateTask(task)
        }
    }

}