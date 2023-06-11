package jp.glory.actions.practice.todo.infra.repository

import jp.glory.actions.practice.infra.store.dao.*
import jp.glory.actions.practice.todo.domain.event.*
import jp.glory.actions.practice.todo.domain.model.Todo
import jp.glory.actions.practice.todo.domain.model.ProgressStatus
import jp.glory.actions.practice.todo.domain.repository.TodoRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class TodoRepositoryImpl(
    private val todoDao: TodoDao
) : TodoRepository {
    override fun findById(id: String): Todo? =
        todoDao.findById(id)
            ?.let { toDomainModel(it) }

    override fun findAll(): List<Todo> =
        todoDao.findAll()
            .map { toDomainModel(it) }

    override fun registerRegisteredEvent(registeredTodo: RegisteredTodo) {
        toTodoRecord(registeredTodo)
            .let { todoDao.register(it) }
    }

    override fun registerChangedEvent(changedTodo: ChangedTodo) {
        val before = todoDao.findById(changedTodo.id) ?: throw IllegalStateException("Not found")
        val after = before.copy(
            title = changedTodo.newTitle,
            deadLine = changedTodo.newDeadLine.toString()
        )
        todoDao.update(after)
    }

    override fun registerDeletedEvent(deletedTodo: DeletedTodo) {
        val todo = todoDao.findById(deletedTodo.id) ?: throw IllegalStateException("Not found")
        todoDao.delete(todo.id)
    }

    override fun registerStatedEvent(startedTodo: StartedTodo) {
        val before = todoDao.findById(startedTodo.id) ?: throw IllegalStateException("Not found")
        val progressFlags = createTodoFlags(ProgressStatus.Started)
        val after = before.copy(
            started = progressFlags.started,
            finished = progressFlags.finished
        )
        todoDao.update(after)
    }

    override fun registerFinishedEvent(finishedTodo: FinishedTodo) {
        val before = todoDao.findById(finishedTodo.id) ?: throw IllegalStateException("Not found")
        val progressFlags = createTodoFlags(ProgressStatus.Finished)
        val after = before.copy(
            started = progressFlags.started,
            finished = progressFlags.finished
        )
        todoDao.update(after)
    }

    private fun toDomainModel(todoRecord: TodoRecord): Todo =
        Todo(
            id = todoRecord.id,
            title = todoRecord.title,
            deadLine = LocalDate.parse(todoRecord.deadLine),
            started = todoRecord.started,
            finished = todoRecord.finished
        )
    private fun toTodoRecord(
        registeredTodo: RegisteredTodo
    ): TodoRecord =
        TodoRecord(
            id = registeredTodo.id,
            title = registeredTodo.title,
            deadLine = registeredTodo.deadLine.toString(),
            started = false,
            finished = false
        )

    private fun createTodoFlags(progressStatus: ProgressStatus) =
        when(progressStatus) {
            ProgressStatus.UnStarted -> TodoFlags(
                started = false,
                finished = false
            )
            ProgressStatus.Started -> TodoFlags(
                started = true,
                finished = false
            )
            ProgressStatus.Finished -> TodoFlags(
                started = true,
                finished = true
            )
        }

    private class TodoFlags(
        val started: Boolean,
        val finished: Boolean
    )
}