package io.github.kirade.simplemember

import io.github.kirade.simplemember.repository.MemberRepository
import io.github.kirade.simplemember.service.MemberService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringConfig(private val memberRepository: MemberRepository) {

    @Bean
    fun memberService(): MemberService {
        return MemberService(memberRepository)
    }
}