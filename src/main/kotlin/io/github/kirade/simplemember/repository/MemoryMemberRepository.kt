package io.github.kirade.simplemember.repository

import io.github.kirade.simplemember.domain.Member

class MemoryMemberRepository : MemberRepository {
    companion object Memory {
        val store: MutableMap<ULong, Member> = mutableMapOf()
        var sequence: ULong = ULong.MIN_VALUE
    }

    override fun create(member: Member): Member {
        store[++sequence] = member
        return member
    }

    override fun findById(id: ULong): Member? {
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
        if (store.containsKey(member.id)) {
            store[member.id] = member
        }
        else {
            throw IllegalArgumentException("Member with id ${member.id} not found")
        }
        return member
    }

    override fun delete(id: ULong): Boolean {
        return store.remove(id) != null
    }
}
