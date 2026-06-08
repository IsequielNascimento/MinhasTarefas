package com.example.mytasks.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tarefas")
data class Tarefa(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val concluida: Boolean = false,
    @ColumnInfo(defaultValue = "MEDIA")
    val prioridade: Prioridade = Prioridade.MEDIA,
    val dataVencimento: Long? = null
)
