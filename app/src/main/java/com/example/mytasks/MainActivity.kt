package com.example.mytasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mytasks.model.TarefaDatabase
import com.example.mytasks.model.TarefaRepository
import com.example.mytasks.ui.theme.MyTasksTheme
import com.example.mytasks.view.TaskDetailScreen
import com.example.mytasks.view.TaskListScreen
import com.example.mytasks.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = TarefaDatabase.getInstance(applicationContext)
        val repository = TarefaRepository(db.tarefaDao())
        val viewModel: TaskViewModel by viewModels { TaskViewModel.Factory(repository) }

        enableEdgeToEdge()
        setContent {
            MyTasksTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "lista") {
                    composable("lista") {
                        TaskListScreen(
                            viewModel = viewModel,
                            onNavigateToDetail = { tarefaId ->
                                navController.navigate("detalhes/$tarefaId")
                            }
                        )
                    }
                    composable("detalhes/{tarefaId}") { backStackEntry ->
                        val tarefaId = backStackEntry.arguments
                            ?.getString("tarefaId")
                            ?.toIntOrNull()
                            ?: return@composable
                        TaskDetailScreen(
                            tarefaId = tarefaId,
                            viewModel = viewModel,
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
