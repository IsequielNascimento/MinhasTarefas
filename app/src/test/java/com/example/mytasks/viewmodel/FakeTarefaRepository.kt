package com.example.mytasks.viewmodel

import com.example.mytasks.model.ITarefaRepository
import com.example.mytasks.model.Tarefa
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeTarefaRepository : ITarefaRepository {
    private val _tarefas = MutableStateFlow<List<Tarefa>>(emptyList())
    override val tarefas: Flow<List<Tarefa>> = _tarefas

    override fun getTarefaById(id: Int): Flow<Tarefa?> =
        _tarefas.map { list -> list.find { it.id == id } }

    override suspend fun inserir(tarefa: Tarefa) {
        val nextId = (_tarefas.value.maxOfOrNull { it.id } ?: 0) + 1
        _tarefas.update { it + tarefa.copy(id = nextId) }
    }

    override suspend fun atualizar(tarefa: Tarefa) {
        _tarefas.update { list -> list.map { if (it.id == tarefa.id) tarefa else it } }
    }

    override suspend fun deletar(tarefa: Tarefa) {
        _tarefas.update { list -> list.filter { it.id != tarefa.id } }
    }
}
