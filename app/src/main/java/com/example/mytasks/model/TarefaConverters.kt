package com.example.mytasks.model

import androidx.room.TypeConverter

class TarefaConverters {
    @TypeConverter
    fun fromPrioridade(p: Prioridade): String = p.name

    @TypeConverter
    fun toPrioridade(s: String): Prioridade =
        Prioridade.entries.firstOrNull { it.name == s } ?: Prioridade.MEDIA
}
