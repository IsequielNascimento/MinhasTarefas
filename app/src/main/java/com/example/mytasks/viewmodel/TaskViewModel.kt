package com.example.mytasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mytasks.model.FiltroStatus
import com.example.mytasks.model.ITarefaRepository
import com.example.mytasks.model.Tarefa
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: ITarefaRepository) : ViewModel() {

    private val _filtro = MutableStateFlow(FiltroStatus.TODAS)
    val filtroAtual: StateFlow<FiltroStatus> = _filtro.asStateFlow()

    val tarefas: StateFlow<List<Tarefa>> = combine(
        repository.tarefas,
        _filtro
    ) { lista, filtro ->
        when (filtro) {
            FiltroStatus.TODAS      -> lista
            FiltroStatus.PENDENTES  -> lista.filter { !it.concluida }
            FiltroStatus.CONCLUIDAS -> lista.filter { it.concluida }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setFiltro(filtro: FiltroStatus) { _filtro.value = filtro }

    fun adicionarTarefa(titulo: String) {
        if (titulo.isBlank()) return
        viewModelScope.launch { repository.inserir(Tarefa(titulo = titulo.trim())) }
    }

    fun atualizarTarefa(tarefa: Tarefa) {
        viewModelScope.launch { repository.atualizar(tarefa) }
    }

    fun deletarTarefa(tarefa: Tarefa) {
        viewModelScope.launch { repository.deletar(tarefa) }
    }

    fun getTarefaById(id: Int): Flow<Tarefa?> = repository.getTarefaById(id)

    class Factory(private val repository: ITarefaRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            TaskViewModel(repository) as T
    }
}
