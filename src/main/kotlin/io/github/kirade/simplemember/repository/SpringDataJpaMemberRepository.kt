package io.github.kirade.simplemember.repository

import io.github.kirade.simplemember.domain.Member
import jakarta.persistence.EntityManager
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component

interface SpringDataJpaMemberRepository: JpaRepository<Member, Long>, MemberRepository {
}

@Component
class SpringDataJpaMemberRepositoryImpl(private val em: EntityManager) {
    private lateinit var repository: SpringDataJpaMemberRepository

    fun create(member: Member): Member {
        em.persist(member)
        return member
    }

    fun update(member: Member): Member {
        val foundMember = em.find(Member::class.java, member.id)
        foundMember.name = member.name
        em.merge(foundMember)
        return member
    }

    fun delete(id: Long): Boolean {
        val foundMember = em.find(Member::class.java, id)
        em.remove(foundMember)
        return foundMember.id == id
    }

    fun flush() {
        em.createQuery("DELETE FROM Member").executeUpdate()
    }
}