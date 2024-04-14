package io.github.kirade.simplemember.repository

import io.github.kirade.simplemember.domain.Member
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class MemoryMemberRepositoryTest {

    private val repository = MemoryMemberRepository()

    @BeforeEach
    fun prepareTestData() {
        // Flush
        MemoryMemberRepository.store.clear()

        // Default Test Data
        MemoryMemberRepository.store[1UL] = Member(1UL, "홍길동")
        MemoryMemberRepository.store[2UL] = Member(2UL, "홍길은")
        MemoryMemberRepository.sequence = 2UL
    }

    @Test
    fun testCreate() {
        val member = Member(id=null, name="홍길금")

        val createdMember = repository.create(member)

        assertEquals(member, createdMember)
        assertEquals(3UL, createdMember.id)
        assertEquals("홍길금", createdMember.name)
    }

    @Test
    fun testCreate_동명이인() {
        val member = Member(id=null, name="홍길동")

        val createdMember = repository.create(member)

        assertEquals(member, createdMember)
        assertEquals(3UL, createdMember.id)
        assertEquals("홍길동", createdMember.name)
    }

    @Test
    fun testFindById() {
        val foundMember1 = repository.findById(1UL)
        val foundMember2 = repository.findById(2UL)

        assertNotNull(foundMember1)
        if (foundMember1 != null) {
            assertEquals(1UL, foundMember1.id)
            assertEquals("홍길동", foundMember1.name)
        }

        assertNotNull(foundMember2)
        if (foundMember2 != null) {
            assertEquals(2UL, foundMember2.id)
            assertEquals("홍길은", foundMember2.name)
        }
    }

    @Test
    fun findByName() {
        val foundMember1 = repository.findByName("홍길동")
        val foundMember2 = repository.findByName("홍길은")

        assertNotNull(foundMember1)
        if (foundMember1 != null) {
            assertEquals(1UL, foundMember1.id)
            assertEquals("홍길동", foundMember1.name)
        }
        assertNotNull(foundMember2)
        if (foundMember2 != null) {
            assertEquals(2UL, foundMember2.id)
            assertEquals("홍길은", foundMember2.name)
        }
    }

    @Test
    fun findAll() {
        val members = repository.findAll()

        assertEquals(2, members.size)
        assertEquals(members[0].id, 1UL)
        assertEquals(members[0].name, "홍길동")
        assertEquals(members[1].id, 2UL)
        assertEquals(members[1].name, "홍길은")
    }

    @Test
    fun update() {
    }

    @Test
    fun delete() {
    }
}