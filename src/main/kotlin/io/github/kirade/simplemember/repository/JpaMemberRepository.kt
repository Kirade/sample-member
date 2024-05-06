package io.github.kirade.simplemember.repository

import io.github.kirade.simplemember.domain.Member
import jakarta.persistence.EntityManager
import java.lang.IllegalStateException

class JpaMemberRepository(private val em:EntityManager): MemberRepository {

    override fun create(member: Member): Member {
        em.persist(member)
        return member
    }

    override fun findById(id: Long): Member? {
        val member = em.find(Member::class.java, id)
        return member
    }

    override fun findByName(name: String): Member? {
        val result = em.createQuery("select m from Member m where m.name = :name", Member::class.java).setParameter("name", name).resultList
        return result.firstOrNull()
    }

    override fun findAll(): List<Member> {
        return em.createQuery("select m from Member as m ", Member::class.java).resultList
    }

    override fun update(member: Member): Member {
        val foundMember = findById(member.id!!) ?: throw IllegalStateException("Member Id cannot be null")
        foundMember.name = member.name
        em.merge(foundMember)
        return member
    }

    override fun delete(id: Long): Boolean {
        val foundMember = findById(id) ?: return false
        em.remove(foundMember)
        return foundMember.id == id
    }

    override fun flush() {
        findAll().forEach { em.remove(it) }
    }
}