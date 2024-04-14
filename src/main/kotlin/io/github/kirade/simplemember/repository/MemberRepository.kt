package io.github.kirade.simplemember.repository

import io.github.kirade.simplemember.domain.Member

interface MemberRepository {
    fun create(member: Member): Member
    fun findById(id: ULong): Member?
    fun findByName(name: String): Member?
    fun findAll(): List<Member>
    fun update(member: Member): Member
    fun delete(id: ULong): Boolean
}