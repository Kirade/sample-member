package io.github.kirade.simplemember

import io.github.kirade.simplemember.repository.JpaMemberRepository
import io.github.kirade.simplemember.repository.MemberRepository
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SpringConfig {

    @Autowired
    private lateinit var em: EntityManager

    @Bean
    fun MemberRepository() : MemberRepository = JpaMemberRepository(em)
}