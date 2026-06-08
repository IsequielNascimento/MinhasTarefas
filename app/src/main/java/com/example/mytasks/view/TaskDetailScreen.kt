package com.example.mytasks.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mytasks.model.Prioridade
import com.example.mytasks.view.components.DateField
import com.example.mytasks.view.components.PrioritySelector
import com.example.mytasks.view.components.TaskDatePickerDialog
import com.example.mytasks.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    tarefaId: Int,
    viewModel: TaskViewModel,
    onNavigateBack: () -> Unit
) {
    val tarefa by viewModel.getTarefaById(tarefaId).collectAsState(initial = null)

    var tituloEditado by remember(tarefa?.id) { mutableStateOf(tarefa?.titulo ?: "") }
    var concluidaEditada by remember(tarefa?.id) { mutableStateOf(tarefa?.concluida ?: false) }
    var prioridadeEditada by remember(tarefa?.id) { mutableStateOf(tarefa?.prioridade ?: Prioridade.MEDIA) }
    var dataVencimentoEditada by remember(tarefa?.id) { mutableStateOf(tarefa?.dataVencimento) }
    var showDatePicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes da Tarefa") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (tarefa == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = tituloEditado,
                    onValueChange = { tituloEditado = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Prioridade",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                PrioritySelector(
                    prioridadeSelecionada = prioridadeEditada,
                    onPrioridadeSelecionada = { prioridadeEditada = it }
                )

                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Vencimento",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                DateField(
                    dataMs = dataVencimentoEditada,
                    onClickSelecionar = { showDatePicker = true },
                    onLimpar = { dataVencimentoEditada = null }
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = concluidaEditada,
                        onCheckedChange = { concluidaEditada = it }
                    )
                    Text("Concluída")
                }

                Button(
                    onClick = {
                        tarefa?.let {
                            viewModel.atualizarTarefa(
                                it.copy(
                                    titulo = tituloEditado.trim(),
                                    concluida = concluidaEditada,
                                    prioridade = prioridadeEditada,
                                    dataVencimento = dataVencimentoEditada
                                )
                            )
                        }
                        onNavigateBack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = tituloEditado.isNotBlank()
                ) {
                    Text("Salvar")
                }
            }

            if (showDatePicker) {
                TaskDatePickerDialog(
                    initialSelectedDateMillis = dataVencimentoEditada,
                    onDismissRequest = { showDatePicker = false },
                    onConfirm = { selectedDate ->
                        dataVencimentoEditada = selectedDate
                        showDatePicker = false
                    }
                )
            }
        }
    }
}
