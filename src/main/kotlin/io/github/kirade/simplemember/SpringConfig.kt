package io.github.kirade.simplemember

import io.github.kirade.simplemember.repository.JdbcTemplateMemberRepository
import io.github.kirade.simplemember.repository.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource


@Configuration
class SpringConfig {

    @Autowired
    private lateinit var dataSource: DataSource

    @Bean
    fun MemberRepository() : MemberRepository = JdbcTemplateMemberRepository(dataSource)
}