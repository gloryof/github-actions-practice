package jp.glory.actions.practice.todo.usecase

import jp.glory.actions.practice.base.usecase.UseCase
import jp.glory.actions.practice.todo.domain.event.RegisteredTodo
import jp.glory.actions.practice.todo.domain.model.TodoIdGenerator
import jp.glory.actions.practice.todo.domain.repository.TodoRepository
import java.time.LocalDate

@UseCase
class RegisterTodoUseCase(
    private val todoIdGenerator: TodoIdGenerator,
    private val todoRepository: TodoRepository
) {
    fun register(input: Input): Output =
        createEvent(input)
            .also { todoRepository.registerRegisteredEvent(it) }
            .let { Output(it.id) }

    private fun createEvent(
        input: Input
    ): RegisteredTodo = RegisteredTodo(
        id = todoIdGenerator.generate(),
        title = input.title,
        deadLine = input.deadLine
    )

    class Input(
        val title: String,
        val deadLine: LocalDate,
    )

    class Output(
        val id: String
    )
}