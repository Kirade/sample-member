package io.github.kirade.simplemember.repository

import io.github.kirade.simplemember.domain.Member
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import java.lang.IllegalStateException
import javax.sql.DataSource

class JdbcTemplateMemberRepository(dataSource: DataSource): MemberRepository {
    val jdbcTemplate = JdbcTemplate(dataSource)

    override fun create(member: Member): Member {
        val jdbcInsert = SimpleJdbcInsert(jdbcTemplate)
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id")

        val parameters: MutableMap<String, Any> = mutableMapOf()
        parameters["name"] = member.name

        val key = jdbcInsert.executeAndReturnKey(MapSqlParameterSource(parameters))
        member.id = key.toLong().toULong()
        return member
    }

    override fun findById(id: ULong): Member? {
        val result: List<Member> = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id.toLong())
        return result.firstOrNull()
    }

    override fun findByName(name: String): Member? {
        val result: List<Member> = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name)
        return result.firstOrNull()
    }

    override fun findAll(): List<Member> {
        return jdbcTemplate.query("select * from member", memberRowMapper())
    }

    override fun update(member: Member): Member {
        val updateCount = jdbcTemplate.update("update member set name = ? where id = ?", member.name, member.id!!.toLong())
        return if (updateCount > 0) member else throw IllegalStateException("Update Failed")
    }

    override fun delete(id: ULong): Boolean {
        val updatedCount = jdbcTemplate.update("delete from member where id = ?", id.toLong())
        return updatedCount > 0
    }

    override fun flush() {
        jdbcTemplate.update("delete from member")
    }

    private fun memberRowMapper(): RowMapper<Member> {
        return RowMapper<Member> { rs, rowNum ->
            Member(
                id=rs.getLong("id").toULong(),
                name=rs.getString("name"),
            )
        }
    }
}