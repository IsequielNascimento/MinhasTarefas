package com.example.mytasks.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mytasks.model.FiltroStatus
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSheet(
    sheetState: SheetState,
    filtroAtual: FiltroStatus,
    onFiltroSelecionado: (FiltroStatus) -> Unit,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Text("Filtrar por status", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(16.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                FiltroStatus.entries.forEach { filtro ->
                    FilterChip(
                        selected = filtroAtual == filtro,
                        onClick = {
                            onFiltroSelecionado(filtro)
                            scope.launch { sheetState.hide(); onDismiss() }
                        },
                        label = {
                            Text(
                                when (filtro) {
                                    FiltroStatus.TODAS      -> "Todas"
                                    FiltroStatus.PENDENTES  -> "Pendentes"
                                    FiltroStatus.CONCLUIDAS -> "Concluídas"
                                }
                            )
                        }
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}
