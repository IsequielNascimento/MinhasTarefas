package com.example.mytasks.model

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class TarefaDao_Impl(
  __db: RoomDatabase,
) : TarefaDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfTarefa: EntityInsertAdapter<Tarefa>

  private val __tarefaConverters: TarefaConverters = TarefaConverters()

  private val __deleteAdapterOfTarefa: EntityDeleteOrUpdateAdapter<Tarefa>

  private val __updateAdapterOfTarefa: EntityDeleteOrUpdateAdapter<Tarefa>
  init {
    this.__db = __db
    this.__insertAdapterOfTarefa = object : EntityInsertAdapter<Tarefa>() {
      protected override fun createQuery(): String =
          "INSERT OR ABORT INTO `tarefas` (`id`,`titulo`,`concluida`,`prioridade`,`dataVencimento`) VALUES (nullif(?, 0),?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: Tarefa) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindText(2, entity.titulo)
        val _tmp: Int = if (entity.concluida) 1 else 0
        statement.bindLong(3, _tmp.toLong())
        val _tmp_1: String = __tarefaConverters.fromPrioridade(entity.prioridade)
        statement.bindText(4, _tmp_1)
        val _tmpDataVencimento: Long? = entity.dataVencimento
        if (_tmpDataVencimento == null) {
          statement.bindNull(5)
        } else {
          statement.bindLong(5, _tmpDataVencimento)
        }
      }
    }
    this.__deleteAdapterOfTarefa = object : EntityDeleteOrUpdateAdapter<Tarefa>() {
      protected override fun createQuery(): String = "DELETE FROM `tarefas` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Tarefa) {
        statement.bindLong(1, entity.id.toLong())
      }
    }
    this.__updateAdapterOfTarefa = object : EntityDeleteOrUpdateAdapter<Tarefa>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `tarefas` SET `id` = ?,`titulo` = ?,`concluida` = ?,`prioridade` = ?,`dataVencimento` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Tarefa) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindText(2, entity.titulo)
        val _tmp: Int = if (entity.concluida) 1 else 0
        statement.bindLong(3, _tmp.toLong())
        val _tmp_1: String = __tarefaConverters.fromPrioridade(entity.prioridade)
        statement.bindText(4, _tmp_1)
        val _tmpDataVencimento: Long? = entity.dataVencimento
        if (_tmpDataVencimento == null) {
          statement.bindNull(5)
        } else {
          statement.bindLong(5, _tmpDataVencimento)
        }
        statement.bindLong(6, entity.id.toLong())
      }
    }
  }

  public override suspend fun insert(tarefa: Tarefa): Unit = performSuspending(__db, false, true) {
      _connection ->
    __insertAdapterOfTarefa.insert(_connection, tarefa)
  }

  public override suspend fun delete(tarefa: Tarefa): Unit = performSuspending(__db, false, true) {
      _connection ->
    __deleteAdapterOfTarefa.handle(_connection, tarefa)
  }

  public override suspend fun update(tarefa: Tarefa): Unit = performSuspending(__db, false, true) {
      _connection ->
    __updateAdapterOfTarefa.handle(_connection, tarefa)
  }

  public override fun getAll(): Flow<List<Tarefa>> {
    val _sql: String = "SELECT * FROM tarefas ORDER BY id ASC"
    return createFlow(__db, false, arrayOf("tarefas")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitulo: Int = getColumnIndexOrThrow(_stmt, "titulo")
        val _columnIndexOfConcluida: Int = getColumnIndexOrThrow(_stmt, "concluida")
        val _columnIndexOfPrioridade: Int = getColumnIndexOrThrow(_stmt, "prioridade")
        val _columnIndexOfDataVencimento: Int = getColumnIndexOrThrow(_stmt, "dataVencimento")
        val _result: MutableList<Tarefa> = mutableListOf()
        while (_stmt.step()) {
          val _item: Tarefa
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpTitulo: String
          _tmpTitulo = _stmt.getText(_columnIndexOfTitulo)
          val _tmpConcluida: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfConcluida).toInt()
          _tmpConcluida = _tmp != 0
          val _tmpPrioridade: Prioridade
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfPrioridade)
          _tmpPrioridade = __tarefaConverters.toPrioridade(_tmp_1)
          val _tmpDataVencimento: Long?
          if (_stmt.isNull(_columnIndexOfDataVencimento)) {
            _tmpDataVencimento = null
          } else {
            _tmpDataVencimento = _stmt.getLong(_columnIndexOfDataVencimento)
          }
          _item = Tarefa(_tmpId,_tmpTitulo,_tmpConcluida,_tmpPrioridade,_tmpDataVencimento)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getById(id: Int): Flow<Tarefa?> {
    val _sql: String = "SELECT * FROM tarefas WHERE id = ?"
    return createFlow(__db, false, arrayOf("tarefas")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitulo: Int = getColumnIndexOrThrow(_stmt, "titulo")
        val _columnIndexOfConcluida: Int = getColumnIndexOrThrow(_stmt, "concluida")
        val _columnIndexOfPrioridade: Int = getColumnIndexOrThrow(_stmt, "prioridade")
        val _columnIndexOfDataVencimento: Int = getColumnIndexOrThrow(_stmt, "dataVencimento")
        val _result: Tarefa?
        if (_stmt.step()) {
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpTitulo: String
          _tmpTitulo = _stmt.getText(_columnIndexOfTitulo)
          val _tmpConcluida: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfConcluida).toInt()
          _tmpConcluida = _tmp != 0
          val _tmpPrioridade: Prioridade
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfPrioridade)
          _tmpPrioridade = __tarefaConverters.toPrioridade(_tmp_1)
          val _tmpDataVencimento: Long?
          if (_stmt.isNull(_columnIndexOfDataVencimento)) {
            _tmpDataVencimento = null
          } else {
            _tmpDataVencimento = _stmt.getLong(_columnIndexOfDataVencimento)
          }
          _result = Tarefa(_tmpId,_tmpTitulo,_tmpConcluida,_tmpPrioridade,_tmpDataVencimento)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
