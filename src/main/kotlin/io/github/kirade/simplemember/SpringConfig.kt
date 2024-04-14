package io.github.kirade.simplemember

import io.github.kirade.simplemember.repository.MemberRepository
import io.github.kirade.simplemember.repository.MemoryMemberRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SpringConfig {

    @Bean
    fun MemberRepository() : MemberRepository = MemoryMemberRepository()
}