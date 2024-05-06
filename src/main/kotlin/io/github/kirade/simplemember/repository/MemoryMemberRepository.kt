package io.github.kirade.simplemember.repository

import io.github.kirade.simplemember.domain.Member

class MemoryMemberRepository : MemberRepository {
    companion object Memory {
        val store: MutableMap<Long, Member> = mutableMapOf()
        var sequence: Long = Long.MIN_VALUE
    }

    override fun create(member: Member): Member {
        member.id = ++sequence
        store[sequence] = member
        return member
    }

    override fun findById(id: Long): Member? {
        return store[id]
    }

    override fun findByName(name: String): Member? {
        val result: Member? = null
        for (member in store.values) {
            if (member.name == name) {
                return member
            }
        }
        return result
    }

    override fun findAll(): List<Member> {
        return store.values.toList()
    }

    override fun update(member: Member): Member {
        val memberId = member.id ?: throw IllegalStateException("Member without ID cannot be updated")
        if (store.containsKey(member.id)) {
            store[memberId] = member
        }
        else {
            throw IllegalArgumentException("Member with id ${member.id} not found")
        }
        return member
    }

    override fun delete(id: Long): Boolean {
        return store.remove(id) != null
    }

    override fun flush() {
        store.clear()
    }
}
