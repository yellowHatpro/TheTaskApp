package org.yellowhatpro.thetaskapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.yellowhatpro.thetaskapp.ui.MainViewModel
import org.yellowhatpro.thetaskapp.ui.screens.CreateTaskScreen
import org.yellowhatpro.thetaskapp.ui.screens.HomeScreen
import org.yellowhatpro.thetaskapp.ui.screens.UpdateTaskScreen
import org.yellowhatpro.thetaskapp.ui.theme.TheTaskAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            val navController = rememberNavController()

            TheTaskAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("home") {
                            HomeScreen(viewModel,
                                onClickTask = { taskId ->
                                    viewModel.fetchTask(taskId)
                                    navController.navigate("task")
                                }, onCreateTaskButton = {
                                    navController.navigate("create")
                                })
                        }
                        composable("create") {
                            CreateTaskScreen(viewModel = viewModel) {
                                navController.navigate("home") {
                                    popUpTo(navController.graph.id) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                        composable("task") {
                            UpdateTaskScreen(viewModel) {
                                navController.navigate("home") {
                                    popUpTo(navController.graph.id) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}