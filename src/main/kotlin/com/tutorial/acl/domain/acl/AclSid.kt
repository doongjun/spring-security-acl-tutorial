package com.tutorial.acl.domain.acl

import javax.persistence.*

@Entity(name = "AclSid")
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["sid", "principal"])])
class AclSid (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, length = 100)
    var sid: String? = null,

    @Column(nullable = false)
    var principal: Boolean = false,
)