package jp.glory.actions.practice.todo.usecase

import jp.glory.actions.practice.base.usecase.UseCase
import jp.glory.actions.practice.todo.domain.repository.TodoRepository

@UseCase
class StartTodoUseCase(
    private val todoRepository: TodoRepository
) {
    fun start(input: Input) =
        todoRepository.findById(input.id)
            ?.let { it.start() }
            ?.also { todoRepository.registerStatedEvent(it) }
            ?: throw IllegalStateException("Todo not found")

    class Input(
        val id: String,
    )
}