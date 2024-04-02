package org.yellowhatpro.thetaskapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.yellowhatpro.thetaskapp.data.entities.Task
import org.yellowhatpro.thetaskapp.ui.MainViewModel
import org.yellowhatpro.thetaskapp.utils.DoOnResult

@Composable
fun CreateTaskScreen(
    viewModel: MainViewModel,
    navigateBack: () -> Unit
) {
    CreateUpdateTaskCommonScreen("Create",
        navigateBack = navigateBack,
        viewModel = viewModel)
}

@Composable
fun UpdateTaskScreen(
    viewModel: MainViewModel,
    navigateBack: () -> Unit
) {
    val currentTask by viewModel.currentTask.collectAsState()
    currentTask.DoOnResult(onFailure = {
        ErrorScreen(error = it.message.toString())
    }, onLoading = {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(100.dp)
                    .padding(40.dp)
            )
        }
    }) {
        CreateUpdateTaskCommonScreen("Update", it, navigateBack, viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUpdateTaskCommonScreen(method: String,
                                 task: Task? = null,
                                 navigateBack: () -> Unit,
                                 viewModel: MainViewModel) {
    var taskName by rememberSaveable {
        mutableStateOf(task?.name ?: "")
    }
    var taskDescription by rememberSaveable {
        mutableStateOf(task?.description ?: "")
    }
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "$method Task") }, navigationIcon = {
            if (method == "Update") {
                IconButton(onClick = {
                    navigateBack()
                }) {
                    Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Go Back")
                }
            }
        })
    }, floatingActionButton = {
        ExtendedFloatingActionButton(
            text = { Text(text = method) },
            icon = { Icon(imageVector = Icons.Rounded.Check, contentDescription = "Done") },
            onClick = {
                when (method) {
                    "Update" -> {
                        task?.let {task->
                            task.description = taskDescription
                            task.name = taskName
                            viewModel.updateTask(task)
                        }
                    }

                    "Create" -> {
                        val newTask = Task(
                            name = taskName,
                            description = taskDescription
                        )
                        viewModel.createNewTask(newTask)
                    }
                }
                navigateBack()
            })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(4.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = taskName,
                onValueChange = { taskName = it },
                placeholder = {
                    when (method) {
                        "Create" -> Text(text = "Title of the Task")
                    }
                },
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp),
                value = taskDescription,
                onValueChange = { taskDescription = it },
                placeholder = {
                    when (method) {
                        "Create" -> Text(text = "Description")
                    }
                },
            )
        }
    }
}