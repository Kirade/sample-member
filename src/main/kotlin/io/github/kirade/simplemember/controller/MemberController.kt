package io.github.kirade.simplemember.controller

import io.github.kirade.simplemember.domain.Member
import io.github.kirade.simplemember.service.MemberService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/members")
class MemberController (private val service: MemberService) {

    @PostMapping
    fun addMember(@RequestBody member: Member): Member {
        return service.addMember(member)
    }

    @GetMapping("/{memberId}")
    fun getMember(@PathVariable("memberId") memberId: ULong): Member? {
        return service.getMember(memberId)
    }

    @GetMapping
    fun getMembers(): List<Member> {
        return service.getMembers()
    }

    @PatchMapping("/{memberId}")
    fun updateMember(@PathVariable("memberId") memberId: ULong, @RequestBody member: Member): Member {
        return service.updateMember(memberId, member)
    }

    @DeleteMapping("/{memberId}")
    fun deleteMember(@PathVariable("memberId") memberId: ULong): Boolean {
        return service.deleteMember(memberId)
    }
}
