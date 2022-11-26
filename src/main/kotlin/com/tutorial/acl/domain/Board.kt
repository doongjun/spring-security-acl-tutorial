package com.tutorial.acl.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Board(
    @Id
    @Column
    val id: Int? = null,
    @Column
    var name: String? = null
)