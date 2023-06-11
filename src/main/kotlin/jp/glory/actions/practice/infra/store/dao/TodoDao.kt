package jp.glory.actions.practice.infra.store.dao

import org.springframework.stereotype.Component

@Component
class TodoDao {
    private val records = mutableMapOf<String, TodoRecord>()
    fun findById(id: String): TodoRecord? = records[id]

    fun findAll(): List<TodoRecord> = records.values.toList()

    fun register(todoRecord: TodoRecord) {
        records[todoRecord.id] = todoRecord
    }

    fun update(todoRecord: TodoRecord) {
        records[todoRecord.id] = todoRecord
    }

    fun delete(id: String) {
        records.remove(id)
    }
}

data class TodoRecord(
    val id: String,
    val title: String,
    val deadLine: String,
    val started: Boolean,
    val finished: Boolean
)