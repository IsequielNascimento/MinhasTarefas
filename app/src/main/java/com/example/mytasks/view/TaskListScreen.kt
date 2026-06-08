package com.example.mytasks.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mytasks.model.Tarefa
import com.example.mytasks.view.components.AddTaskSheet
import com.example.mytasks.view.components.EmptyTasksState
import com.example.mytasks.view.components.FilterSheet
import com.example.mytasks.view.components.TarefaItem
import com.example.mytasks.view.components.TaskSwipeToDismiss
import com.example.mytasks.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    viewModel: TaskViewModel,
    onNavigateToDetail: (Int) -> Unit
) {
    val tarefas by viewModel.tarefas.collectAsState()
    val filtroAtual by viewModel.filtroAtual.collectAsState()
    var showAddSheet by remember { mutableStateOf(false) }
    var showFilterSheet by remember { mutableStateOf(false) }
    var pendingDeleteTarefa by remember { mutableStateOf<Tarefa?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val addSheetState = rememberModalBottomSheetState()
    val filterSheetState = rememberModalBottomSheetState()

    val tarefasVisiveis = tarefas.filter { it.id != pendingDeleteTarefa?.id }

    LaunchedEffect(pendingDeleteTarefa) {
        val tarefa = pendingDeleteTarefa ?: return@LaunchedEffect
        val result = snackbarHostState.showSnackbar(
            message = "Tarefa removida",
            actionLabel = "Desfazer",
            duration = SnackbarDuration.Long
        )
        when (result) {
            SnackbarResult.ActionPerformed -> pendingDeleteTarefa = null
            SnackbarResult.Dismissed -> {
                viewModel.deletarTarefa(tarefa)
                pendingDeleteTarefa = null
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Minhas Tarefas") },
                actions = {
                    TextButton(onClick = { showFilterSheet = true }) {
                        Text("Filtrar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddSheet = true }) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar tarefa")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        if (tarefasVisiveis.isEmpty() && pendingDeleteTarefa == null) {
            EmptyTasksState(filtroAtual = filtroAtual, modifier = Modifier.padding(innerPadding))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentPadding = PaddingValues(bottom = 88.dp)
            ) {
                items(tarefasVisiveis, key = { it.id }) { tarefa ->
                    TaskSwipeToDismiss(
                        onDismiss = {
                            pendingDeleteTarefa?.let { viewModel.deletarTarefa(it) }
                            pendingDeleteTarefa = tarefa
                        }
                    ) {
                        Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
                            TarefaItem(
                                tarefa = tarefa,
                                onToggleConcluida = {
                                    viewModel.atualizarTarefa(tarefa.copy(concluida = !tarefa.concluida))
                                },
                                onNavigateToDetail = { onNavigateToDetail(tarefa.id) }
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }

    if (showAddSheet) {
        AddTaskSheet(
            sheetState = addSheetState,
            onDismiss = { showAddSheet = false },
            onConfirm = { titulo -> viewModel.adicionarTarefa(titulo) }
        )
    }

    if (showFilterSheet) {
        FilterSheet(
            sheetState = filterSheetState,
            filtroAtual = filtroAtual,
            onFiltroSelecionado = { viewModel.setFiltro(it) },
            onDismiss = { showFilterSheet = false }
        )
    }
}
