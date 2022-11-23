package com.tutorial.acl.domain.acl

import javax.persistence.*

@Entity(name = "AclClass")
class AclClass (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, length = 100, unique = true, name = "class")
    var className: String? = null,
)