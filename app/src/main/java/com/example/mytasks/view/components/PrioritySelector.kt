package com.example.mytasks.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mytasks.model.Prioridade

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioritySelector(
    prioridadeSelecionada: Prioridade,
    onPrioridadeSelecionada: (Prioridade) -> Unit,
    modifier: Modifier = Modifier
) {
    SingleChoiceSegmentedButtonRow(modifier = modifier.fillMaxWidth()) {
        Prioridade.entries.forEachIndexed { index, p ->
            SegmentedButton(
                selected = prioridadeSelecionada == p,
                onClick = { onPrioridadeSelecionada(p) },
                shape = SegmentedButtonDefaults.itemShape(index, Prioridade.entries.size),
                label = {
                    Text(
                        when (p) {
                            Prioridade.ALTA  -> "Alta"
                            Prioridade.MEDIA -> "Média"
                            Prioridade.BAIXA -> "Baixa"
                        }
                    )
                }
            )
        }
    }
}
