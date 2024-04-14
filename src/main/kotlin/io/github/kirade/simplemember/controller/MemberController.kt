package io.github.kirade.simplemember.controller

import io.github.kirade.simplemember.domain.Member
import io.github.kirade.simplemember.repository.MemberRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/members")
class MemberController (@Autowired val repository: MemberRepository) {
    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @PostMapping
    fun addMember(@RequestBody member: Member): Member {
        logger.info("Add member named `${member.name}`")

        return repository.create(Member(id=null, name=member.name))
    }

    @GetMapping("/{memberId}")
    fun getMember(@PathVariable("memberId") memberId: ULong): Member? {
        logger.info("Get member $memberId")

        return repository.findById(memberId)
    }

    @GetMapping
    fun getMembers(): List<Member> {
        logger.info("Get members")

        return repository.findAll()
    }

    @PatchMapping("/{memberId}")
    fun updateMember(@PathVariable("memberId") memberId: ULong, @RequestBody member: Member): Member {
        val foundMember = repository.findById(memberId) ?: throw IllegalStateException("Member with id $memberId not found")
        logger.info("Update member `${foundMember.name}` -> ${member.name}")

        member.id = foundMember.id
        val updatedMember = repository.update(member)

        return updatedMember
    }

    @DeleteMapping("/{memberId}")
    fun deleteMember(@PathVariable("memberId") memberId: ULong): Boolean {
        logger.info("Delete member `${memberId}`")

        return repository.delete(memberId)
    }
}