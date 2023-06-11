package jp.glory.actions.practice.todo.usecase

import jp.glory.actions.practice.base.usecase.UseCase
import jp.glory.actions.practice.todo.domain.repository.TodoRepository
import java.time.Clock

@UseCase
class ListTodoUseCase(
    private val todoRepository: TodoRepository,
    private val clock: Clock
) {
    fun findAll(): Output =
        todoRepository.findAll()
            .let { details -> details.map { TodoDetail(it, clock) } }
            .let { Output(it) }

    class Input(
        val id: String
    )

    class Output(
        val details: List<TodoDetail>
    )
}