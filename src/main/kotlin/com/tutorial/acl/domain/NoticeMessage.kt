package com.tutorial.acl.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class NoticeMessage(
    @Id @Column
    val id: Int,
    @Column
    var content: String
)
