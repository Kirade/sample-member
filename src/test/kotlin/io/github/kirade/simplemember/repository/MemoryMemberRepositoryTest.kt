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
        assertEquals(1UL, foundMember1?.id)
        assertEquals("홍길동", foundMember1?.name)

        assertNotNull(foundMember2)
        assertEquals(2UL, foundMember2?.id)
        assertEquals("홍길은", foundMember2?.name)
    }

    @Test
    fun testFindByName() {
        val foundMember1 = repository.findByName("홍길동")
        val foundMember2 = repository.findByName("홍길은")

        assertNotNull(foundMember1)
        assertEquals(1UL, foundMember1?.id)
        assertEquals("홍길동", foundMember1?.name)

        assertNotNull(foundMember2)
        assertEquals(2UL, foundMember2?.id)
        assertEquals("홍길은", foundMember2?.name)
    }

    @Test
    fun testFindAll() {
        val members = repository.findAll()

        assertEquals(2, members.size)
        assertEquals(members[0].id, 1UL)
        assertEquals(members[0].name, "홍길동")
        assertEquals(members[1].id, 2UL)
        assertEquals(members[1].name, "홍길은")
    }

    @Test
    fun testUpdate() {
        val memberId = 1UL
        val member = repository.findById(memberId)

        if (member == null) {
            throw Exception("멤버가 존재하지 않습니다.")
        } else {
            member.name = "홍길금"
        }

        repository.update(member)

        val updatedMember = repository.findById(memberId)
        assertNotNull(updatedMember)
        assertEquals("홍길금", updatedMember?.name)
    }

    @Test
    fun testUpdate_멤버ID가_null() {
        val member = Member(id=null, name="홍길동")

        assertThrows(IllegalStateException::class.java) { repository.update(member) }
    }

    @Test
    fun testUpdate_존재하지_않는_멤버ID() {
        val member = Member(id=4UL, name="홍길동")

        assertThrows(IllegalArgumentException::class.java) { repository.update(member) }
    }

    @Test
    fun delete() {
        assertTrue(repository.delete(1UL))
        assertFalse(repository.delete(1UL))
        assertTrue(repository.delete(2UL))
        assertFalse(repository.delete(2UL))
        assertFalse(repository.delete(3UL))
    }
}