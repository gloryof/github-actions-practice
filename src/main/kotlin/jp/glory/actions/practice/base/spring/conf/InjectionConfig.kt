package jp.glory.actions.practice.base.spring.conf

import jp.glory.actions.practice.todo.domain.model.TodoIdGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock

@Configuration
class InjectionConfig {
    @Bean
    fun clock(): Clock = Clock.systemDefaultZone()

    @Bean
    fun todoIdGenerator(): TodoIdGenerator = TodoIdGenerator()
}