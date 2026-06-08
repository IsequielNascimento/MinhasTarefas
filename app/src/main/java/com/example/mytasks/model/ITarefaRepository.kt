package com.example.mytasks.model

import kotlinx.coroutines.flow.Flow

interface ITarefaRepository {
    val tarefas: Flow<List<Tarefa>>
    fun getTarefaById(id: Int): Flow<Tarefa?>
    suspend fun inserir(tarefa: Tarefa)
    suspend fun atualizar(tarefa: Tarefa)
    suspend fun deletar(tarefa: Tarefa)
}
