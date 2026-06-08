package com.example.mytasks.viewmodel

import com.example.mytasks.model.FiltroStatus
import com.example.mytasks.model.Prioridade
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TaskViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var repository: FakeTarefaRepository
    private lateinit var viewModel: TaskViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = FakeTarefaRepository()
        viewModel = TaskViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `adicionarTarefa adiciona tarefa com titulo correto`() = runTest {
        viewModel.adicionarTarefa("Comprar pão")

        val tarefas = repository.tarefas.first()
        assertEquals(1, tarefas.size)
        assertEquals("Comprar pão", tarefas[0].titulo)
        assertEquals(false, tarefas[0].concluida)
    }

    @Test
    fun `adicionarTarefa ignora titulo em branco`() = runTest {
        viewModel.adicionarTarefa("   ")

        val tarefas = repository.tarefas.first()
        assertTrue(tarefas.isEmpty())
    }

    @Test
    fun `atualizarTarefa altera titulo e status`() = runTest {
        viewModel.adicionarTarefa("Tarefa original")
        val tarefaAdicionada = repository.tarefas.first()[0]

        viewModel.atualizarTarefa(tarefaAdicionada.copy(titulo = "Tarefa editada", concluida = true))

        val tarefas = repository.tarefas.first()
        assertEquals("Tarefa editada", tarefas[0].titulo)
        assertEquals(true, tarefas[0].concluida)
    }

    @Test
    fun `deletarTarefa remove a tarefa da lista`() = runTest {
        viewModel.adicionarTarefa("Tarefa para deletar")
        val tarefa = repository.tarefas.first()[0]

        viewModel.deletarTarefa(tarefa)

        val tarefas = repository.tarefas.first()
        assertTrue(tarefas.isEmpty())
    }

    @Test
    fun `setFiltro PENDENTES exibe apenas tarefas nao concluidas`() = runTest {
        viewModel.adicionarTarefa("Pendente")
        viewModel.adicionarTarefa("Concluida")
        val concluida = repository.tarefas.first()[1]
        viewModel.atualizarTarefa(concluida.copy(concluida = true))

        viewModel.setFiltro(FiltroStatus.PENDENTES)

        val tarefas = viewModel.tarefas.first()
        assertEquals(1, tarefas.size)
        assertEquals("Pendente", tarefas[0].titulo)
    }

    @Test
    fun `setFiltro CONCLUIDAS exibe apenas tarefas concluidas`() = runTest {
        viewModel.adicionarTarefa("Pendente")
        viewModel.adicionarTarefa("Concluida")
        val concluida = repository.tarefas.first()[1]
        viewModel.atualizarTarefa(concluida.copy(concluida = true))

        viewModel.setFiltro(FiltroStatus.CONCLUIDAS)

        val tarefas = viewModel.tarefas.first()
        assertEquals(1, tarefas.size)
        assertEquals("Concluida", tarefas[0].titulo)
    }

    @Test
    fun `setFiltro TODAS apos filtro ativo exibe todas as tarefas`() = runTest {
        viewModel.adicionarTarefa("Pendente")
        viewModel.adicionarTarefa("Concluida")
        val concluida = repository.tarefas.first()[1]
        viewModel.atualizarTarefa(concluida.copy(concluida = true))
        viewModel.setFiltro(FiltroStatus.PENDENTES)

        viewModel.setFiltro(FiltroStatus.TODAS)

        val tarefas = viewModel.tarefas.first()
        assertEquals(2, tarefas.size)
    }

    @Test
    fun `adicionarTarefa cria tarefa com prioridade MEDIA por padrao`() = runTest {
        viewModel.adicionarTarefa("Nova tarefa")
        val tarefa = repository.tarefas.first().first()
        assertEquals(Prioridade.MEDIA, tarefa.prioridade)
    }

    @Test
    fun `atualizarTarefa persiste prioridade ALTA`() = runTest {
        viewModel.adicionarTarefa("Tarefa")
        val tarefa = repository.tarefas.first().first()
        viewModel.atualizarTarefa(tarefa.copy(prioridade = Prioridade.ALTA))
        val atualizada = repository.tarefas.first().first()
        assertEquals(Prioridade.ALTA, atualizada.prioridade)
    }

    @Test
    fun `atualizarTarefa persiste dataVencimento`() = runTest {
        viewModel.adicionarTarefa("Tarefa")
        val tarefa = repository.tarefas.first().first()
        val ts = 1_750_000_000_000L
        viewModel.atualizarTarefa(tarefa.copy(dataVencimento = ts))
        val atualizada = repository.tarefas.first().first()
        assertEquals(ts, atualizada.dataVencimento)
    }
}
