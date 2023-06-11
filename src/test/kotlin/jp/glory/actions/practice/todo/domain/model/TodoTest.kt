package jp.glory.actions.practice.todo.domain.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate


internal class TodoTest {
    @Nested
    inner class TestGetProgressStatus {
        @Test
        fun unStarted() {
            val sut = createSut(
                started = false,
                finished = false
            )

            assertEquals(ProgressStatus.UnStarted, sut.getProgressStatus())
        }

        @Test
        fun started() {
            val sut = createSut(
                started = true,
                finished = false
            )

            assertEquals(ProgressStatus.Started, sut.getProgressStatus())
        }

        @Test
        fun finished() {
            val sut = createSut(
                started = false,
                finished = true
            )

            assertEquals(ProgressStatus.Finished, sut.getProgressStatus())
        }
    }

    private fun createSut(
        id: String = "test-id",
        title: String = "test-title",
        deadLine: LocalDate = LocalDate.now().plusDays(30),
        started: Boolean = false,
        finished: Boolean = false
    ): Todo =
        Todo(
            id = id,
            title = title,
            deadLine = deadLine,
            started = started,
            finished = finished
        )
}