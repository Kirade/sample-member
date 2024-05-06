package io.github.kirade.simplemember.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Column

@Entity
class Member(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column(name="name")
    var name: String,
)