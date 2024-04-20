package io.github.kirade.simplemember.service

import io.github.kirade.simplemember.domain.Member
import io.github.kirade.simplemember.repository.MemberRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class MemberService (private val repository: MemberRepository) {
    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    fun addMember(member: Member): Member {
        logger.info("Add member named `${member.name}`")

        return repository.create(Member(id=null, name=member.name))
    }

    fun getMember(memberId: ULong): Member? {
        logger.info("Get member $memberId")

        return repository.findById(memberId)
    }

    fun getMembers(): List<Member> {
        logger.info("Get members")

        return repository.findAll()
    }

    fun updateMember(memberId: ULong, member: Member): Member {
        val foundMember = repository.findById(memberId) ?: throw IllegalStateException("Member with id $memberId not found")
        logger.info("Update member `${foundMember.name}` -> ${member.name}")

        member.id = foundMember.id
        val updatedMember = repository.update(member)

        return updatedMember
    }

    fun deleteMember(memberId: ULong): Boolean {
        logger.info("Delete member `${memberId}`")

        return repository.delete(memberId)
    }

}
