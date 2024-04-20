package io.github.kirade.simplemember.service

import io.github.kirade.simplemember.domain.Member
import io.github.kirade.simplemember.repository.MemberRepository
import io.github.kirade.simplemember.repository.MemoryMemberRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MemberServiceTest @Autowired constructor(val repository: MemberRepository) {
    val service: MemberService = MemberService(repository)

    @BeforeEach
    fun prepareTestData() {
        repository.flush()
    }

    @Test
    fun addMember() {
        val member = Member(null, "Jay")

        val createdMember = service.addMember(member)

        assertEquals(createdMember.name, "Jay")
    }

    @Test
    fun getMember() {
        val member = Member(null, "Jay")
        val memberId = service.addMember(member).id ?: throw java.lang.IllegalStateException("Member not created")

        val foundMember = service.getMember(memberId) ?: throw IllegalStateException("Member not found")

        assertEquals(foundMember.id, memberId)
        assertEquals(foundMember.name, "Jay")
    }

    @Test
    fun getMembers() {
        service.addMember(Member(null, "Jay"))
        service.addMember(Member(null, "Kirade"))

        val members = service.getMembers()

        assertEquals(members.size, 2)
    }

    @Test
    fun updateMember() {
        val memberId= service.addMember(Member(null, "Jay")).id ?: throw java.lang.IllegalStateException("Member not created")

        val updatedMember = service.updateMember(memberId, Member(memberId, "Kirade"))

        assertEquals(updatedMember.id, memberId)
        assertEquals(updatedMember.name, "Kirade")
    }

    @Test
    fun deleteMember() {
        val memberId= service.addMember(Member(null, "Jay")).id ?: throw java.lang.IllegalStateException("Member not created")

        val result = service.deleteMember(memberId)

        assertTrue(result)
    }
}