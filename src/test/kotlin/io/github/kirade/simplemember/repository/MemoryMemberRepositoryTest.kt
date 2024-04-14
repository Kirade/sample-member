package io.github.kirade.simplemember.repository

import io.github.kirade.simplemember.domain.Member
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class MemoryMemberRepositoryTest {

    private val repository = MemoryMemberRepository()

    @Test
    fun testCreate() {
        val member = Member(id=null, name="홍길동")

        val createdMember = repository.create(member)

        assertEquals(member, createdMember)
        assertEquals(1UL, createdMember.id)
        assertEquals("홍길동", createdMember.name)
    }

    @Test
    fun findById() {
    }

    @Test
    fun findByName() {
    }

    @Test
    fun findAll() {
    }

    @Test
    fun update() {
    }

    @Test
    fun delete() {
    }
}