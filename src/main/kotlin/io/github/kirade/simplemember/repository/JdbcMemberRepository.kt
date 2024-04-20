package io.github.kirade.simplemember.repository

import io.github.kirade.simplemember.domain.Member
import java.sql.SQLException
import java.sql.Statement
import javax.sql.DataSource

class JdbcMemberRepository (private val dataSource: DataSource): MemberRepository {

    // DataSourceUtils 를 통한 Connection 을 가져오는 경우, try-catch-finally 구문을통해 명시적으로 자원에 대한 Close 를 해주어야 한다.
    // 현재 코드에서는 use 를 통해 자동으로 Connection 이 닫히게 하기위해 DataSourceUtils 를 활용하지 않았다.
    // 일반적으로는 연결, 트랜잭션, 자원해제, 유연성이 뛰어난 DataSourceUtils 를 활용하여 커넥션 관리를 한다고 한다.
    // val connection = DataSourceUtils.getConnection(dataSource)

    override fun create(member: Member): Member {
        val sql = "INSERT INTO member(name) VALUES(?)"

        dataSource.connection.use { connection ->
            connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { prepareStatement ->
                prepareStatement.setString(1, member.name)
                prepareStatement.executeUpdate()
                prepareStatement.generatedKeys.use { resultSet ->
                    if (resultSet.next()) {
                        member.id = resultSet.getLong(1).toULong()
                    } else {
                        throw SQLException("No generated keys found")
                    }
                    return member
                }
            }
        }
    }

    override fun findById(id: ULong): Member? {
        val sql = "SELECT * FROM member WHERE id = ?"

        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.setLong(1, id.toLong())
                preparedStatement.executeQuery().use { resultSet ->
                    if (resultSet.next()) {
                        return Member(
                            resultSet.getLong("id").toULong(),
                            resultSet.getString("name"),
                        )
                    } else {
                        return null
                    }
                }
            }
        }
    }

    override fun findByName(name: String): Member? {
        val sql = "SELECT * FROM member WHERE name = ?"

        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.setString(1, name)
                preparedStatement.executeQuery().use { resultSet ->
                    if (resultSet.next()) {
                        return Member(
                            resultSet.getLong("id").toULong(),
                            resultSet.getString("name"),
                        )
                    } else {
                        return null
                    }
                }
            }
        }
    }

    override fun findAll(): List<Member> {
        val sql = "SELECT * FROM member"
        val memberList = mutableListOf<Member>()

        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.executeQuery().use { resultSet ->
                    while (resultSet.next()) {
                        val member = Member(
                            resultSet.getLong("id").toULong(),
                            resultSet.getString("name"),
                        )
                        memberList.add(member)
                    }
                    return memberList
                }
            }
        }
    }

    override fun update(member: Member): Member {
        val sql = "UPDATE member SET name = ? WHERE id = ?"
        val memberId = member.id ?: throw IllegalArgumentException("Member id cannot be null")

        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.setString(1, member.name)
                preparedStatement.setLong(2, memberId.toLong())
                preparedStatement.executeUpdate()
                return Member(
                    memberId,
                    member.name,
                )
            }
        }
    }

    override fun delete(id: ULong): Boolean {
        val sql = "DELETE FROM member WHERE id = ?"

        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.setLong(1, id.toLong())
                return preparedStatement.executeUpdate() > 0
            }
        }
    }

    override fun flush() {
        val sql = "DELETE FROM member"

        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.executeQuery()
            }
        }
    }

}