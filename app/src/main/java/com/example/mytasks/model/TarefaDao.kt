package com.example.mytasks.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TarefaDao {
    @Query("SELECT * FROM tarefas ORDER BY id ASC")
    fun getAll(): Flow<List<Tarefa>>

    @Query("SELECT * FROM tarefas WHERE id = :id")
    fun getById(id: Int): Flow<Tarefa?>

    @Insert
    suspend fun insert(tarefa: Tarefa)

    @Update
    suspend fun update(tarefa: Tarefa)

    @Delete
    suspend fun delete(tarefa: Tarefa)
}
