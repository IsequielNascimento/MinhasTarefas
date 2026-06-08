package com.example.mytasks.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytasks.model.Tarefa
import com.example.mytasks.utils.formatarDataCurta
import com.example.mytasks.utils.prioridadeCor
import com.example.mytasks.utils.startOfTodayUtcMs

@Composable
fun TarefaItem(
    tarefa: Tarefa,
    onToggleConcluida: () -> Unit,
    onNavigateToDetail: () -> Unit
) {
    val isDark = isSystemInDarkTheme()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .background(prioridadeCor(tarefa.prioridade, isDark))
        )
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = tarefa.concluida, onCheckedChange = { onToggleConcluida() })
            Column(modifier = Modifier.weight(1f).clickable { onNavigateToDetail() }) {
                Text(
                    text = tarefa.titulo,
                    textDecoration = if (tarefa.concluida) TextDecoration.LineThrough else null
                )
                tarefa.dataVencimento?.let { ms ->
                    val todayUtc = startOfTodayUtcMs()
                    val (texto, cor) = when {
                        tarefa.concluida ->
                            "Vencia em ${formatarDataCurta(ms)}" to
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                        ms < todayUtc ->
                            "Venceu em ${formatarDataCurta(ms)}" to
                            if (isDark) Color(0xFFEF9A9A) else Color(0xFFC62828)
                        ms < todayUtc + 86_400_000L ->
                            "Vence hoje" to
                            if (isDark) Color(0xFFFFCC80) else Color(0xFFE65100)
                        else ->
                            "Vence em ${formatarDataCurta(ms)}" to
                            MaterialTheme.colorScheme.onSurfaceVariant
                    }
                    Text(texto, fontSize = 11.sp, color = cor)
                }
            }
        }
    }
}
