package com.example.mytasks.model

import kotlinx.coroutines.flow.Flow

class TarefaRepository(private val dao: TarefaDao) : ITarefaRepository {
    override val tarefas: Flow<List<Tarefa>> = dao.getAll()
    override fun getTarefaById(id: Int): Flow<Tarefa?> = dao.getById(id)
    override suspend fun inserir(tarefa: Tarefa) = dao.insert(tarefa)
    override suspend fun atualizar(tarefa: Tarefa) = dao.update(tarefa)
    override suspend fun deletar(tarefa: Tarefa) = dao.delete(tarefa)
}
