package com.example.mytasks.utils

import androidx.compose.ui.graphics.Color
import com.example.mytasks.model.Prioridade

fun prioridadeCor(p: Prioridade, isDark: Boolean): Color = when (p) {
    Prioridade.ALTA  -> if (isDark) Color(0xFFEF9A9A) else Color(0xFFC62828)
    Prioridade.MEDIA -> if (isDark) Color(0xFFFFCC80) else Color(0xFFE65100)
    Prioridade.BAIXA -> if (isDark) Color(0xFFA5D6A7) else Color(0xFF2E7D32)
}
