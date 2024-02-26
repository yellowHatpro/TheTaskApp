package org.yellowhatpro.thetaskapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.yellowhatpro.thetaskapp.data.entities.Task
import org.yellowhatpro.thetaskapp.ui.MainViewModel
import org.yellowhatpro.thetaskapp.utils.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: MainViewModel,
               onCreateTaskButton: () -> Unit,
               onClickTask: (taskId: Int)-> Unit) {
    val scope = rememberCoroutineScope()
    val allTasksData by viewModel.tasks.collectAsState()

    LaunchedEffect(key1 = allTasksData) {
        scope.launch {
            viewModel.fetchAllTasks()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "The Task App")
            })
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                       Text(text = "Add Task")
                },
                icon = { Icon(Icons.Filled.Add, "Add Task") },
                onClick = {
                    onCreateTaskButton()
                })
        }
    ) {
        Column(Modifier.padding(it).padding(4.dp)) {
            when (allTasksData.status) {
                Response.Status.SUCCESS -> {
                    allTasksData.data?.let { allTasks ->
                        LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2)) {
                            items(allTasks) { task ->
                                TaskCard(task = task, onClickTask)
                            }
                        }
                    }
                }

                else -> {
                    ErrorScreen()
                }
            }
        }
    }
}

@Composable
fun TaskCard(task: Task, onClickTask: (taskId: Int) -> Unit) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .border(BorderStroke(2.dp, color = Color.Gray))
            .padding(4.dp)
            .clickable {
                onClickTask(task.id)
            }
    ) {
        Text(text = task.name)
        Text(text = task.description)
        Text(text = task.dueDate.toString())
    }
}