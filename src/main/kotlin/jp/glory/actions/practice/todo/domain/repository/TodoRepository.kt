package jp.glory.actions.practice.todo.domain.repository

import jp.glory.actions.practice.todo.domain.model.Todo
import jp.glory.actions.practice.todo.domain.event.*

interface TodoRepository {
    fun findById(id: String): Todo?
    fun findAll(): List<Todo>

    fun registerRegisteredEvent(registeredTodo: RegisteredTodo)
    fun registerChangedEvent(changedTodo: ChangedTodo)
    fun registerDeletedEvent(deletedTodo: DeletedTodo)
    fun registerStatedEvent(startedTodo: StartedTodo)
    fun registerFinishedEvent(finishedTodo: FinishedTodo)
}